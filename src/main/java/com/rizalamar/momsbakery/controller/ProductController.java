package com.rizalamar.momsbakery.controller;

import com.rizalamar.momsbakery.dto.WebResponse;
import com.rizalamar.momsbakery.dto.product.ProductRequest;
import com.rizalamar.momsbakery.dto.product.ProductResponse;
import com.rizalamar.momsbakery.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<WebResponse<ProductResponse>> create(@Valid @RequestBody ProductRequest request){
        ProductResponse productResponse = productService.createProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        WebResponse.<ProductResponse>builder()
                                .code(HttpStatus.CREATED.value())
                                .data(productResponse)
                                .build()
                );
    }

    @GetMapping("/{productId}")
    public ResponseEntity<WebResponse<ProductResponse>> get(@PathVariable UUID productId){
        ProductResponse productResponse = productService.getProductById(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<ProductResponse>builder()
                                .code(HttpStatus.OK.value())
                                .data(productResponse)
                                .build()
                );
    }

    @GetMapping
    public ResponseEntity<WebResponse<List<ProductResponse>>> getAll() {
        List<ProductResponse> allProducts = productService.getAllProducts();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<List<ProductResponse>>builder()
                                .code(HttpStatus.OK.value())
                                .data(allProducts)
                                .build()
                );
    }

    @PutMapping("/{productId}")
    public ResponseEntity<WebResponse<ProductResponse>> update(
            @PathVariable UUID productId,
            @Valid @RequestBody ProductRequest request
    ) {
        ProductResponse productResponse = productService.updateProduct(productId, request);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<ProductResponse>builder()
                                .code(HttpStatus.OK.value())
                                .data(productResponse)
                                .build()
                );
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<WebResponse<String>> delete(@PathVariable UUID productId) {
        productService.deleteProduct(productId);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<String>builder()
                                .code(HttpStatus.OK.value())
                                .data("OK")
                                .build()
                );
    }
}
