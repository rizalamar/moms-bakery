package com.rizalamar.momsbakery.dto.category;

import com.rizalamar.momsbakery.domain.CategoryType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CategoryRequest(
        @NotNull(message = "Category name is required")
        CategoryType name,

        @Size(min = 3)
        String description
) {
}
