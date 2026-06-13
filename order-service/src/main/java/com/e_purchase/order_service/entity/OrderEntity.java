package com.e_purchase.order_service.entity;

import com.e_purchase.order_service.enums.Currency;
import com.e_purchase.order_service.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "ORDERS", schema = "E_PURCHASE_ORDERS")
@Getter
@Setter
public class OrderEntity extends AuditEntity {
    private static final String ORDER_SEQUENCE_GEN = "order_seq_gen";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = ORDER_SEQUENCE_GEN)
    @SequenceGenerator(name = ORDER_SEQUENCE_GEN, sequenceName = "order_seq", allocationSize = 1)
    private Long id;

    // Order Information
    @Column(name = "CUSTOMER_ID", nullable = false)
    private Long customerId;

    @Column(name = "ORDER_NUMBER", nullable = false, unique = true)
    private String orderNumber;

    @Column(name = "ORDER_DATE", nullable = false)
    private LocalDateTime orderDate;

    @Column(name = "ORDER_STATUS", nullable = false)
    private OrderStatus orderStatus;

    @Column(name = "TOTAL_AMOUNT", nullable = false)
    private BigDecimal totalAmount;

    @Column(name = "CURRENCY", nullable = false)
    private Currency currency;
}
