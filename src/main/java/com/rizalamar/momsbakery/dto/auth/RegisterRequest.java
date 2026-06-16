package com.rizalamar.momsbakery.dto.auth;

import com.rizalamar.momsbakery.annotation.EnumPattern;
import com.rizalamar.momsbakery.domain.Role;
import jakarta.validation.constraints.*;
import lombok.Builder;

@Builder
public record RegisterRequest(

        @NotBlank(message = "Username is required")
        @Size(min = 3, max = 50, message = "Username must be at least 3 characters and maximum 50 characters")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 6, message = "Password must  at least 6 characters")
        @Pattern(
                regexp = "^(?=.*[A-Z])(?=.*\\d).*$",
                message = "Password must contains one Capital and 1 Number"
        )
        String password,

        @NotBlank(message = "Full name is required")
        @Size(min = 3, max = 50, message = "Full name must  at least 3 characters")
        String fullName,

        @NotBlank(message = "Email is required")
        @Email(message = "Email format is invalid")
        String email,

        @NotBlank(message = "Phone number is required")
        @Size(min = 10, max = 15, message = "Phone number must be between 10 and 15 digits")
        @Pattern(regexp = "^[0-9+]+$", message = "Phone number can only contain digits and '+' sign")
        String phone,

        @NotNull(message = "Role is required")
        @EnumPattern(anyOf = {"ADMIN", "CUSTOMER"})
        Role role
){}
