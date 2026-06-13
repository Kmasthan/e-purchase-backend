package com.e_purchase.order_service.controller;

import com.e_purchase.order_service.entity.OrderEntity;
import com.e_purchase.order_service.repository.OrderRepository;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderController.class);

    private final OrderRepository orderRepository;

    public OrderController(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @PostMapping(path = "/create")
    public ResponseEntity<String> createOrder(@NonNull @RequestBody OrderEntity order) {
        LOGGER.info("Received request to create order for customer ID: {}", order.getCustomerId());
        this.orderRepository.save(order);
        return ResponseEntity.ok("Order created successfully with ID: " + order.getId());
    }
}
