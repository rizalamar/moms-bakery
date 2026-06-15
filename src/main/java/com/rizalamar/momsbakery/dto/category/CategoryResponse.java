package com.rizalamar.momsbakery.dto.category;

import com.rizalamar.momsbakery.domain.CategoryType;
import lombok.Builder;

import java.util.UUID;

@Builder
public record CategoryResponse(
        UUID id,
        CategoryType name,
        String description
) {
}
