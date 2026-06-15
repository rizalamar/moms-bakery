package com.rizalamar.momsbakery.dto.product;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductRequest(

        @NotBlank(message = "Product name is required")
        @Size(min = 3)
        String name,

        String description,

        @NotNull
        @Positive(message = "Price must be positive number")
        BigDecimal price,


        String imageUrl,

        @NotNull(message = "Stock status is required")
        boolean stockStatus,

        @NotNull(message = "Category is required")
        UUID categoryId
        ) {
}
