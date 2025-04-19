package com.example.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

import com.example.app.common.security.ExampleAuthenticationProviderImpl;
import com.example.app.common.security.RestAuthenticationEntryPoint;

/**
 * Security configuration class. This class configures the security settings for
 * the application, including authentication and authorization.
 */
@Configuration
public class SecurityConfig {

	/**
	 * Authentication manager bean.
	 * @param configuration                Authentication configuration
	 * @param customAuthenticationProvider Custom authentication provider
	 * @return Authentication manager
	 */
	@Bean
	public AuthenticationManager authenticationManager(final AuthenticationConfiguration configuration,
			final ExampleAuthenticationProviderImpl customAuthenticationProvider) {
		return new ProviderManager(customAuthenticationProvider);
	}

	/**
	 * Security filter chain bean.
	 * @param http HTTP security filter chain
	 * @return Updated security filter chain
	 * @throws Exception If an error occurs while configuring the security filter
	 *                   chain
	 */
	@Bean
	public SecurityFilterChain filterChain(final HttpSecurity http, final RestAuthenticationEntryPoint entryPoint)
			throws Exception {
		return http.sessionManagement(smc -> smc.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeHttpRequests(auth -> auth.requestMatchers("/public").permitAll().anyRequest().authenticated())
			.httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(entryPoint))
			.build();
	}

}