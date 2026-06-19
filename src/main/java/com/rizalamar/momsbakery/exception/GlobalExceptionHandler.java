package com.rizalamar.momsbakery.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.rizalamar.momsbakery.dto.ErrorResponse;
import com.rizalamar.momsbakery.dto.WebResponse;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

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

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<WebResponse<Object>> handleHttpMessageNotReadable(
            HttpMessageNotReadableException exception,
            WebRequest request
    ){
        Throwable mostSpecificCause = exception.getMostSpecificCause();

        String message = "Invalid JSON format";

        if(mostSpecificCause instanceof DateTimeParseException dateTimeParseException){
            message = String.format("Invalid format in %s. Error in character position -%d",
                    dateTimeParseException.getParsedString(),
                    dateTimeParseException.getErrorIndex());
        } else if (exception.getCause() instanceof InvalidFormatException invalidFormatException){
            message = "Data field format " + invalidFormatException.getPath().getFirst().getFieldName();
        }

        ErrorResponse errorResponse = new ErrorResponse(
                message,
                request.getDescription(false).replace("uri", ""),
                LocalDateTime.now()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        WebResponse.builder()
                                .code(HttpStatus.BAD_REQUEST.value())
                                .data(null)
                                .errors(errorResponse)
                                .build()
                );
    }
}
