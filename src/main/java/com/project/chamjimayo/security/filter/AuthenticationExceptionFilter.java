package com.project.chamjimayo.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.chamjimayo.controller.dto.response.ApiStandardResponse;
import com.project.chamjimayo.controller.dto.response.ErrorResponse;
import com.project.chamjimayo.security.exception.ApiKeyNotValidException;
import com.project.chamjimayo.service.exception.ErrorStatus;
import com.project.chamjimayo.security.exception.InvalidTokenException;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class AuthenticationExceptionFilter extends OncePerRequestFilter {

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      filterChain.doFilter(request, response);
    } catch (InvalidTokenException e) {
      ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.INVALID_TOKEN_EXCEPTION,
          e.getMessage());
      createApiErrorResponse(HttpStatus.BAD_REQUEST, response, errorResponse);
    } catch (ApiKeyNotValidException e) {
      ErrorResponse errorResponse = ErrorResponse.create(ErrorStatus.API_KEY_NOT_VALID_EXCEPTION,
          e.getMessage());
      createApiErrorResponse(HttpStatus.UNAUTHORIZED, response, errorResponse);
    }
  }

  private void createApiErrorResponse(HttpStatus status, HttpServletResponse response,
      ErrorResponse errorResponse)
      throws IOException {
    ApiStandardResponse<ErrorResponse> apiResponse = ApiStandardResponse.fail(errorResponse);
    response.setStatus(status.value());
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("utf-8");
    response.getWriter().write(new ObjectMapper().writeValueAsString(apiResponse));
  }
}
