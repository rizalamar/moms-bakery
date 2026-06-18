package com.rizalamar.momsbakery.mapper;

import com.rizalamar.momsbakery.domain.Order;
import com.rizalamar.momsbakery.domain.OrderItem;
import com.rizalamar.momsbakery.dto.order.OrderResponse;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {
    public OrderResponse toResponse(Order order){
        if(order == null) return null;

        List<OrderResponse.OrderItemResponse> itemResponses = order.getItems().stream()
                .map(this::toItemResponse)
                .toList();

        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .waNumber(order.getWaNumber())
                .requestedDeliveryDate(order.getRequestedDeliveryDate())
                .notes(order.getNotes())
                .totalPrice(order.getTotalPrice())
                .status(order.getStatus())
                .createdAt(order.getCreatedDate())
                .items(itemResponses)
                .build();
    }

    private OrderResponse.OrderItemResponse toItemResponse(OrderItem orderItem){
        return OrderResponse.OrderItemResponse.builder()
                .productId(orderItem.getProduct().getId())
                .productName(orderItem.getProduct().getName())
                .price(orderItem.getProduct().getPrice())
                .quantity(orderItem.getQuantity())
                .subTotal(orderItem.getSubTotal())
                .build();
    }
}
