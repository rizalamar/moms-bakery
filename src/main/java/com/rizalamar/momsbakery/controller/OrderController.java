package com.rizalamar.momsbakery.controller;

import com.rizalamar.momsbakery.annotation.CurrentAccount;
import com.rizalamar.momsbakery.domain.Account;
import com.rizalamar.momsbakery.dto.WebResponse;
import com.rizalamar.momsbakery.dto.order.OrderRequest;
import com.rizalamar.momsbakery.dto.order.OrderResponse;
import com.rizalamar.momsbakery.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<WebResponse<OrderResponse>> createOrder(
            @CurrentAccount Account account,
            @Valid @RequestBody OrderRequest request
            ) {
        OrderResponse orderResponse = orderService.createOrder(account, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(
                        WebResponse.<OrderResponse>builder()
                                .code(HttpStatus.CREATED.value())
                                .data(orderResponse)
                                .build()
                );
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WebResponse<List<OrderResponse>>> getAll(){
        List<OrderResponse> orderResponses = orderService.getAll();
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<List<OrderResponse>>builder()
                                .code(HttpStatus.OK.value())
                                .data(orderResponses)
                                .build()
                );
    }

    @GetMapping("/me")
    @PreAuthorize("hasRole('CUSTOMER')")
    public ResponseEntity<WebResponse<List<OrderResponse>>> getAllByMe(
            @CurrentAccount Account account
    ) {
        List<OrderResponse> orderResponses = orderService.getAllByMe(account);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<List<OrderResponse>>builder()
                                .code(HttpStatus.OK.value())
                                .data(orderResponses)
                                .build()
                );
    }

    @PatchMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WebResponse<OrderResponse>> updateOrder(
            @PathVariable UUID orderId,
            @Valid @RequestBody OrderRequest.UpdateOrderStatusRequest updateOrderStatusRequest
            ) {
        OrderResponse orderResponse = orderService.updateOrderStatus(orderId, updateOrderStatusRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(
                        WebResponse.<OrderResponse>builder()
                                .code(HttpStatus.OK.value())
                                .data(orderResponse)
                                .build()
                );
    }
}
