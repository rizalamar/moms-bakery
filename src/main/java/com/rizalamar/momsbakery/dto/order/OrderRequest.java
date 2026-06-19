package com.rizalamar.momsbakery.dto.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.rizalamar.momsbakery.domain.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

public record OrderRequest(
        @NotBlank(message = "Customer name is required")
        String customerName,

        @NotBlank(message = "Whatsapp number is required")
        String waNumber,

        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate requestedDeliveryDate,

        @JsonFormat(pattern = "HH:mm")
        LocalTime requestedDeliveryTime,

        String notes,
        List<OrderItemRequest> items
) {
    public record OrderItemRequest(
            UUID productId,
            Integer quantity
    ){}

    public record UpdateOrderStatusRequest(
            OrderStatus status
    ){}
}
