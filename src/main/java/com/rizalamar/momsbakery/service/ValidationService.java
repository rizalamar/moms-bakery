package com.rizalamar.momsbakery.service;

import com.rizalamar.momsbakery.dto.order.OrderRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class ValidationService {
    private final Validator validator;

    public void validate(Object request){
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(request);
        if(!constraintViolations.isEmpty()){
            throw new ConstraintViolationException(constraintViolations);
        }

        if(request instanceof OrderRequest orderRequest){
            LocalDate requestDelivaryDate = orderRequest.requestedDeliveryDate();
            if(requestDelivaryDate == null){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requested delivery date is required");
            }

            LocalDate tomorrow = LocalDate.now().plusDays(1);
            if(requestDelivaryDate.isBefore(tomorrow)){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Requested delivery date must be at least tomorrow or later");
            }
        }
    }
}
