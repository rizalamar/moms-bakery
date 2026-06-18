package com.rizalamar.momsbakery.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order extends AbstractAuditingEntity {

    @Column(nullable = false)
    private String customerName;

    @Column(nullable = false)
    private String waNumber;

    @Column(nullable = false)
    private LocalDateTime requestedDeliveryDate;

    private String notes;

    @Column(nullable = false)
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", referencedColumnName = "id")
    private Account account;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items;
}
