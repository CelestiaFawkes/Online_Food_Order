package config;

import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import jakarta.servlet.http.HttpServletRequest;

@Configuration
@EnableWebSecurity
public class AppConfig {
	private static final Logger logger = LoggerFactory.getLogger(AppConfig.class);

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		logger.info("Configuring security filter chain...");

		http.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						Authorize -> Authorize.requestMatchers("api/admin/**").hasAnyRole("RESTURANT_OWNER", "OWNER")
								.requestMatchers("api/**").authenticated().anyRequest().permitAll())
				.addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class).csrf(csrf -> csrf.disable())
				.cors(cors -> cors.configurationSource(corsConfigrationSource()));
		return http.build();

	}

	public CorsConfigurationSource corsConfigrationSource() {
		return new CorsConfigurationSource() {

			@Override
			public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {

				CorsConfiguration cfg = new CorsConfiguration();
				cfg.setAllowedOrigins(Arrays.asList("http://localhost:8082"));
				cfg.setAllowedMethods(Collections.singletonList("*"));
				cfg.setAllowCredentials(true);
				cfg.setAllowedHeaders(Collections.singletonList("*"));
				cfg.setExposedHeaders(Arrays.asList("Authorization"));
				cfg.setMaxAge(3600L);
				return cfg;
			}
		};

	}

	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

}
