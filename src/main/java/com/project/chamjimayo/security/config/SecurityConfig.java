package com.project.chamjimayo.security.config;

import com.project.chamjimayo.security.ApiKeyAuthenticationFilter;
import com.project.chamjimayo.security.AuthTokenFactory;
import com.project.chamjimayo.security.CustomUserDetailsService;
import com.project.chamjimayo.security.JwtAuthenticationExceptionFilter;
import com.project.chamjimayo.security.JwtAuthenticationFilter;
import com.project.chamjimayo.security.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
  private final AuthTokenFactory authTokenFactory;
  private final ApiProperties apiProperties;

  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(authTokenFactory, customUserDetailsService);
  }

  public JwtAuthenticationExceptionFilter jwtAuthenticationExceptionFilter() {
    return new JwtAuthenticationExceptionFilter();
  }

  @Bean
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
  public SecurityFilterChain filterChainWithJwt(HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .csrf().disable()
        .headers().frameOptions().disable()
        .and()
        .httpBasic().disable()
        .formLogin().disable();

    http.authorizeRequests(auth -> auth
        .antMatchers("/api-docs/**", "/swagger-ui/**").permitAll()
        .anyRequest().authenticated());

    http.userDetailsService(customUserDetailsService);

    http.exceptionHandling()
        .authenticationEntryPoint(new RestAuthenticationEntryPoint());

    http.addFilter(apiKeyAuthenticationFilter());
    http
        .antMatcher("/api/auth/**")
        .antMatcher("/address/search/test/**")
        .antMatcher("/review/test/**")
        .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(jwtAuthenticationExceptionFilter(), JwtAuthenticationFilter.class);

    return http.build();
  }
}
