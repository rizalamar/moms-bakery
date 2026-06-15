package com.rizalamar.momsbakery.dto.product;

import com.rizalamar.momsbakery.domain.Category;
import com.rizalamar.momsbakery.dto.category.CategoryResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record ProductResponse(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        String imageUrl,
        boolean stockStatus,
        CategoryResponse category
) {
}
