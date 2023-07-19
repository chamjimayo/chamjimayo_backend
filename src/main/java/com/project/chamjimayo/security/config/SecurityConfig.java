package com.project.chamjimayo.security.config;

import com.project.chamjimayo.security.ApiKeyAuthenticationFilter;
import com.project.chamjimayo.security.AuthenticationExceptionFilter;
import com.project.chamjimayo.security.CustomUserDetailsService;
import com.project.chamjimayo.security.JwtAuthenticationFilter;
import com.project.chamjimayo.security.RestAuthenticationEntryPoint;
import com.project.chamjimayo.service.AuthTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final CustomUserDetailsService customUserDetailsService;
	private final AuthTokenService authTokenService;
	private final ApiProperties apiProperties;

	public JwtAuthenticationFilter jwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(authTokenService, customUserDetailsService);
	}

	public AuthenticationExceptionFilter authenticationExceptionFilter() {
		return new AuthenticationExceptionFilter();
	}

	public ApiKeyAuthenticationFilter apiKeyAuthenticationFilter() {
		ApiKeyAuthenticationFilter apiKeyAuthenticationFilter = new ApiKeyAuthenticationFilter(
			apiProperties.getHeaderName());

		apiKeyAuthenticationFilter.setAuthenticationManager(authentication -> {
			String principal = (String) authentication.getPrincipal();

			if (!apiProperties.getApiKey().equals(principal))
				throw new BadCredentialsException("Api 키가 잘못됐습니다.");

			authentication.setAuthenticated(true);
			return authentication;
		});

		return apiKeyAuthenticationFilter;
	}

	@Bean
	@Order(1)
	public SecurityFilterChain filterChainWithJwt(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.csrf().disable()
			.headers().frameOptions().disable()
			.and()
			.httpBasic().disable()
			.formLogin().disable();

		http.userDetailsService(customUserDetailsService);

		http.exceptionHandling()
			.authenticationEntryPoint(new RestAuthenticationEntryPoint());

		http.requestMatchers(request -> request.antMatchers("/api/users/me/**","/api/restroom/use")
				.and()
				.addFilter(apiKeyAuthenticationFilter())
				.addFilterBefore(authenticationExceptionFilter(), ApiKeyAuthenticationFilter.class)
				.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
				.addFilterBefore(authenticationExceptionFilter(), JwtAuthenticationFilter.class))
			.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

		return http.build();
	}

	@Bean
	@Order(2)
	public SecurityFilterChain filterChainWithoutJwt(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
			.and()
			.csrf().disable()
			.headers().frameOptions().disable()
			.and()
			.httpBasic().disable()
			.formLogin().disable();

		http.userDetailsService(customUserDetailsService);

		http.exceptionHandling()
			.authenticationEntryPoint(new RestAuthenticationEntryPoint());

		http.requestMatchers(request -> request.antMatchers("/api/**")
				.and()
				.addFilter(apiKeyAuthenticationFilter())
				.addFilterBefore(authenticationExceptionFilter(), ApiKeyAuthenticationFilter.class))
			.authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

		return http.build();
	}
}