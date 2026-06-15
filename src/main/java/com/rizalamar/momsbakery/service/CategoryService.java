package com.rizalamar.momsbakery.service;

import com.rizalamar.momsbakery.domain.Category;
import com.rizalamar.momsbakery.dto.category.CategoryRequest;
import com.rizalamar.momsbakery.dto.category.CategoryResponse;
import com.rizalamar.momsbakery.mapper.CategoryMapper;
import com.rizalamar.momsbakery.repository.CategoryRepository;
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
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final ValidationService validationService;
    private final CategoryMapper categoryMapper;

    @Transactional
    public CategoryResponse createCategory(CategoryRequest request){
        validationService.validate(request);

        Category category = Category.builder()
                .name(request.name())
                .description(request.description())
                .build();

        Category savedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(savedCategory);
    }

    @Transactional(readOnly = true)
    public CategoryResponse getCategoryById(UUID categoryId){
        Category category = categoryRepository.findCategoriesById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        return categoryMapper.toResponse(category);
    }

    @Transactional(readOnly = true)
    public List<CategoryResponse> getAllCategories(){
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Transactional
    public CategoryResponse updateCategory(UUID categoryId, CategoryRequest request){
        validationService.validate(request);

        Category category = categoryRepository.findCategoriesById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        if(Objects.nonNull(request.name())){
            category.setName(request.name());
        }

        if(Objects.nonNull(request.description())){
            category.setDescription(request.description());
        }

        Category upatedCategory = categoryRepository.save(category);

        return categoryMapper.toResponse(category);
    }

    @Transactional
    public void deleteCategory(UUID categoryId){
        Category category = categoryRepository.findCategoriesById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"));

        categoryRepository.delete(category);
    }
}
