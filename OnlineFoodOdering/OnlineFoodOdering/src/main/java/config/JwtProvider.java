package config;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.crypto.SecretKey;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtProvider {

	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.secret_key.getBytes());

	public String generateToken(Authentication auth) {
		Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
		String roles = populateAuthorities(authorities);

		logger.info("Generating JWT for user: {}", auth.getName());

		String jwt = Jwts.builder().setIssuedAt(new Date()).setExpiration(new Date(new Date().getTime() + 86400000))
				.claim("email", auth.getName()).claim("authorities", roles).signWith(key).compact();

		logger.debug("JWT generated successfully for user: {}", auth.getName());

		return jwt;
	}

	public String getEmailFromJwtToken(String jwt) {
		if (jwt.startsWith("Bearer ")) {
			jwt = jwt.substring(7); // Trim the "Bearer " prefix
		}

		Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(jwt).getBody();

		String email = claims.get("email", String.class);

		logger.info("Extracted email '{}' from JWT token", email);

		return email;
	}

	private String populateAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Set<String> auths = new HashSet<>();
		for (GrantedAuthority authority : authorities) {
			auths.add(authority.getAuthority());
		}
		return String.join(",", auths);
	}
}
