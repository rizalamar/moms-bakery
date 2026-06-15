package com.rizalamar.momsbakery.exception;

import com.rizalamar.momsbakery.dto.ErrorResponse;
import com.rizalamar.momsbakery.dto.WebResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<WebResponse<Object>> handleResponseStatusException(ResponseStatusException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getMessage(),
                request.getDescription(false).replace("uri", ""),
                LocalDateTime.now()
        );

        return ResponseEntity.status(exception.getStatusCode())
                .body(
                        WebResponse.builder()
                                .code(exception.getStatusCode().value())
                                .data(null)
                                .errors(errorResponse)
                                .paging(null)
                                .build()
                );
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<WebResponse<Object>> handleConstraintViolationsException(ConstraintViolationException exception, WebRequest request){
        ErrorResponse errorResponse = new ErrorResponse(
                exception.getMessage(),
                request.getDescription(false).replace("uri", ""),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        WebResponse.builder()
                                .code(HttpStatus.BAD_REQUEST.value())
                                .data(null)
                                .errors(errorResponse)
                                .paging(null)
                                .build()
                );
    }
}
