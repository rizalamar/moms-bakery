package com.rizalamar.momsbakery.service;

import com.rizalamar.momsbakery.domain.*;
import com.rizalamar.momsbakery.dto.order.OrderRequest;
import com.rizalamar.momsbakery.dto.order.OrderResponse;
import com.rizalamar.momsbakery.mapper.OrderMapper;
import com.rizalamar.momsbakery.repository.OrderRepository;
import com.rizalamar.momsbakery.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final ValidationService validationService;
    private final OrderMapper orderMapper;

    @Transactional
    public OrderResponse createOrder(Account account, OrderRequest request){
        validationService.validate(request);

        Order order = Order.builder()
                .customerName(request.customerName())
                .waNumber(request.waNumber())
                .requestedDeliveryDate(request.requestedDeliveryDate())
                .notes(request.notes())
                .status(OrderStatus.WAITING_CONFIRMATION)
                .account(account)
                .build();

        List<OrderItem> orderItems = request.items().stream()
                .map(itemRequest -> {
                    Product product = productRepository.findProductById(itemRequest.productId())
                            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found"));

                    BigDecimal subTotal = product.getPrice().multiply(BigDecimal.valueOf(itemRequest.quantity()));

                    return OrderItem.builder()
                            .order(order)
                            .product(product)
                            .quantity(itemRequest.quantity())
                            .subTotal(subTotal)
                            .build();
                }).toList();

        BigDecimal total = orderItems.stream()
                .map(OrderItem::getSubTotal).reduce(BigDecimal.ZERO, BigDecimal::add);

        order.setTotalPrice(total);
        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        return orderMapper.toResponse(savedOrder);
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAll(){
        return orderRepository.findAll().stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<OrderResponse> getAllByMe(Account account){
        List<Order> orderList = orderRepository.findAllByAccount(account);
        return orderList.stream()
                .map(orderMapper::toResponse)
                .toList();
    }

    @Transactional
    public OrderResponse updateOrderStatus(UUID orderId, OrderRequest.UpdateOrderStatusRequest statusRequest){
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        order.setStatus(statusRequest.status());

        Order savedOrderStatus = orderRepository.save(order);

        return orderMapper.toResponse(savedOrderStatus);
    }
}
