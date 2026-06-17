package com.rizalamar.momsbakery.exception.custom;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rizalamar.momsbakery.dto.ErrorResponse;
import com.rizalamar.momsbakery.dto.WebResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class CustomAccessDenied implements AccessDeniedHandler {
    private final ObjectMapper objectMapper;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpStatus.FORBIDDEN.value());

        WebResponse<Object> webResponse = WebResponse.builder()
                .code(HttpStatus.FORBIDDEN.value())
                .errors(
                        new ErrorResponse(
                                accessDeniedException.getMessage(),
                                request.getRequestURI(),
                                LocalDateTime.now()
                        )
                )
                .build();

        response.getWriter().write(objectMapper.writeValueAsString(webResponse));
    }
}
