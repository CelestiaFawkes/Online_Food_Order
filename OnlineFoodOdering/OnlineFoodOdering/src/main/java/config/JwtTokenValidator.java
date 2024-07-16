package config;

import java.io.IOException;
import java.util.List;

import javax.crypto.SecretKey;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

public class JwtTokenValidator extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.Jwt_Header);

        if (jwt != null) {
            jwt = jwt.substring(7); // Reassign the trimmed JWT

            try {
                SecretKey key = Keys.hmacShaKeyFor(JwtConstant.secret_key.getBytes());
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwt)
                        .getBody();

                String email = String.valueOf(claims.get("email"));
                String authorities = String.valueOf(claims.get("authorities"));
                List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication authentication = new UsernamePasswordAuthenticationToken(email, null, auth);
                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e) {
                throw new BadCredentialsException("Invalid tokens...", e);
            }
        }
        filterChain.doFilter(request, response);
    }
}
