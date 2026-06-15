package com.rizalamar.momsbakery.mapper;

import com.rizalamar.momsbakery.domain.Product;
import com.rizalamar.momsbakery.dto.product.ProductResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductMapper {
    private final CategoryMapper categoryMapper;

    public ProductResponse toResponse(Product product){

        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .imageUrl(product.getImageUrl())
                .stockStatus(product.isStockStatus())
                .category(categoryMapper.toResponse(product.getCategory()))
                .build();
    }
}
