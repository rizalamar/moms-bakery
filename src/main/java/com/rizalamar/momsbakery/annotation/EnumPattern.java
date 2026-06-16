package com.rizalamar.momsbakery.annotation;

import com.rizalamar.momsbakery.config.EnumNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumNameValidator.class)
public @interface EnumPattern {
    String[] anyOf(); // Daftar nilai yang diizinkan
    String message() default "Must be any of {anyOf}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
