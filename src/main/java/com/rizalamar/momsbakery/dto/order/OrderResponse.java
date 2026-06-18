package com.rizalamar.momsbakery.dto.order;

import com.rizalamar.momsbakery.domain.OrderStatus;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
public record OrderResponse(
        UUID id,
        String customerName,
        String waNumber,
        LocalDateTime requestedDeliveryDate,
        String notes,
        BigDecimal totalPrice,
        OrderStatus status,
        LocalDateTime createdAt,
        List<OrderItemResponse> items
) {
    @Builder
    public record OrderItemResponse(
            UUID productId,
            String productName,
            BigDecimal price,
            Integer quantity,
            BigDecimal subTotal
    ){}
}
