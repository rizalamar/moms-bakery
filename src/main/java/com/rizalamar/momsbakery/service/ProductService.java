package com.rizalamar.momsbakery.service;

import com.rizalamar.momsbakery.domain.Category;
import com.rizalamar.momsbakery.domain.Product;
import com.rizalamar.momsbakery.dto.category.CategoryResponse;
import com.rizalamar.momsbakery.dto.product.ProductRequest;
import com.rizalamar.momsbakery.dto.product.ProductResponse;
import com.rizalamar.momsbakery.mapper.ProductMapper;
import com.rizalamar.momsbakery.repository.CategoryRepository;
import com.rizalamar.momsbakery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ValidationService validationService;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductResponse createProduct(ProductRequest request){
        validationService.validate(request);

        Category category = categoryRepository.findCategoriesById(request.categoryId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        Product product = Product.builder()
                .name(request.name())
                .description(request.description())
                .price(request.price())
                .imageUrl(request.imageUrl())
                .stockStatus(true)
                .category(category)
                .build();

        Product savedProduct = productRepository.save(product);

        return productMapper.toResponse(savedProduct);
    }

    @Transactional(readOnly = true)
    public ProductResponse getProductById(UUID productId){
        Product product = productRepository.findProductById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        return productMapper.toResponse(product);
    }

    @Transactional(readOnly = true)
    public List<ProductResponse> getAllProducts(){
        return productRepository.findAll().stream()
                .map(productMapper::toResponse)
                .toList();
    }

    @Transactional
    public ProductResponse updateProduct(UUID productId, ProductRequest request){
        validationService.validate(request);

        Product product = productRepository.findProductById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        if(Objects.nonNull(request.name())){
            product.setName(request.name());
        }

        if(Objects.nonNull(request.description())){
            product.setDescription(request.description());
        }

        if(Objects.nonNull(request.price())){
            product.setPrice(request.price());
        }

        if(Objects.nonNull(request.imageUrl())){
            product.setImageUrl(request.imageUrl());
        }

        if(Objects.nonNull(request.stockStatus())){
            product.setStockStatus(request.stockStatus());
        }

        Product updatedProduct = productRepository.save(product);

        return productMapper.toResponse(updatedProduct);
    }

    @Transactional
    public void deleteProduct(UUID productId){
        Product product = productRepository.findProductById(productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

        productRepository.delete(product);
    }
}
