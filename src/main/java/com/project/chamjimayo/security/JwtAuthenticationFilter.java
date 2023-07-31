package com.project.chamjimayo.security;

import com.project.chamjimayo.exception.InvalidTokenException;
import com.project.chamjimayo.service.AuthTokenService;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  private final AuthTokenService authTokenService;
  private final CustomUserDetailsService customUserDetailsService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {

    String accessToken = getAccessTokenFromRequest(request);

    if (!StringUtils.hasText(accessToken) && !authTokenService.isValid(accessToken)) {
      throw new InvalidTokenException("jwt 토큰이 유효하지 않습니다.");
    }

    String userId = authTokenService.extractPayload(accessToken);

    UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId);
    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
        userDetails, null, userDetails.getAuthorities());
    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    SecurityContextHolder.getContext().setAuthentication(authentication);

    filterChain.doFilter(request, response);
  }

  private String getAccessTokenFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader("Bearer-Token");
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return "";
  }
}
