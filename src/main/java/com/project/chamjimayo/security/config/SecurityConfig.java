package com.project.chamjimayo.security.config;

import com.project.chamjimayo.security.AuthTokenFactory;
import com.project.chamjimayo.security.CustomUserDetailsService;
import com.project.chamjimayo.security.JwtAuthenticationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

  @Bean
  public JwtAuthenticationFilter jwtAuthenticationFilter() {
    return new JwtAuthenticationFilter(authTokenFactory, customUserDetailsService);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .csrf().disable()
        .headers().frameOptions().disable()
        .and()
        .httpBasic().disable()
        .formLogin().disable();

    http.authorizeRequests(auth -> auth
            .antMatchers("/api/signup").permitAll()
            .antMatchers("/api/login").permitAll()
        );

    http.userDetailsService(customUserDetailsService);

    http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
