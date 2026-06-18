package com.rizalamar.momsbakery.controller;

import com.rizalamar.momsbakery.dto.WebResponse;
import com.rizalamar.momsbakery.dto.category.CategoryRequest;
import com.rizalamar.momsbakery.dto.category.CategoryResponse;
import com.rizalamar.momsbakery.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WebResponse<CategoryResponse>> create(@Valid @RequestBody CategoryRequest request) {
        CategoryResponse categoryResponse = categoryService.createCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        WebResponse.<CategoryResponse>builder()
                                .code(HttpStatus.CREATED.value())
                                .data(categoryResponse)
                                .build()
                );
    }

    @GetMapping("/{categoryId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<WebResponse<CategoryResponse>> get(
            @PathVariable UUID categoryId
    ) {
        CategoryResponse categoryResponse = categoryService.getCategoryById(categoryId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<CategoryResponse>builder()
                                .code(HttpStatus.OK.value())
                                .data(categoryResponse)
                                .build()
                );
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'CUSTOMER')")
    public ResponseEntity<WebResponse<List<CategoryResponse>>> getAll() {
        List<CategoryResponse> allCategories = categoryService.getAllCategories();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<List<CategoryResponse>>builder()
                                .code(HttpStatus.OK.value())
                                .data(allCategories)
                                .build()
                );
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WebResponse<CategoryResponse>> update(
            @PathVariable UUID categoryId,
            @Valid @RequestBody CategoryRequest request
    ) {
        CategoryResponse categoryResponse = categoryService.updateCategory(categoryId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<CategoryResponse>builder()
                                .code(HttpStatus.OK.value())
                                .data(categoryResponse)
                                .build()
                );
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WebResponse<String>> delete(@PathVariable UUID categoryId) {
        categoryService.deleteCategory(categoryId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<String>builder()
                                .code(HttpStatus.OK.value())
                                .data("OK")
                                .build()
                );
    }
}
