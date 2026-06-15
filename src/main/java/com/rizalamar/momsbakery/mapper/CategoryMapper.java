package com.rizalamar.momsbakery.mapper;

import com.rizalamar.momsbakery.domain.Category;
import com.rizalamar.momsbakery.dto.category.CategoryResponse;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {

    public CategoryResponse toResponse(Category category){
        if(category == null) return null;

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .description(category.getDescription())
                .build();
    }
}
