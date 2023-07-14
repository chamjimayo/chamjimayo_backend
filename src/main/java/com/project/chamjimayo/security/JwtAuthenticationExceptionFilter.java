package com.project.chamjimayo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.chamjimayo.controller.dto.ErrorCode;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import com.project.chamjimayo.exception.InvalidTokenException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JwtAuthenticationExceptionFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (InvalidTokenException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.setContentType(MediaType.APPLICATION_JSON_VALUE);
      ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.INVALID_TOKEN_EXCEPTION,
          "유효한 토큰이 아닙니다.");
      response.setCharacterEncoding("utf-8");
      response.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
    }
  }
}
