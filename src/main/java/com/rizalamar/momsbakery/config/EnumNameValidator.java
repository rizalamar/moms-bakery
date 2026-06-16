package com.rizalamar.momsbakery.config;


import com.rizalamar.momsbakery.annotation.EnumPattern;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class EnumNameValidator implements ConstraintValidator<EnumPattern, Enum<?>> {
    private List<String> acceptedValues;

    @Override
    public void initialize(EnumPattern constraintAnnotation) {
        acceptedValues = Arrays.asList(constraintAnnotation.anyOf());
    }

    @Override
    public boolean isValid(Enum<?> anEnum, ConstraintValidatorContext constraintValidatorContext) {
        if (anEnum == null) return true;
        return acceptedValues.contains(anEnum.name());
    }
}
