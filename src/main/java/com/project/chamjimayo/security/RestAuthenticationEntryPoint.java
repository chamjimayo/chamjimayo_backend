package com.project.chamjimayo.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.chamjimayo.controller.dto.ErrorCode;
import com.project.chamjimayo.controller.dto.ErrorResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private static final Logger LOGGER = LoggerFactory.getLogger(RestAuthenticationEntryPoint.class);

  @Override
  public void commence(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse,
      AuthenticationException e) throws IOException {
    LOGGER.info("Responding with unauthorized error. Message - {}", e.getMessage());

    ErrorResponse errorResponse = ErrorResponse.create(ErrorCode.AUTHENTICATION_EXCEPTION,
        "인증에 실패하였습니다.");
    httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    httpServletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
    httpServletResponse.setCharacterEncoding("utf-8");
    httpServletResponse.getWriter().write(new ObjectMapper().writeValueAsString(errorResponse));
  }
}
