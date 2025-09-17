package com.example.orderprocessing.controller;

import com.example.orderprocessing.dto.CreateOrderRequest;
import com.example.orderprocessing.dto.OrderResponse;
import com.example.orderprocessing.dto.UpdateStatusRequest;
import com.example.orderprocessing.mapper.OrderMapper;
import com.example.orderprocessing.model.Order;
import com.example.orderprocessing.model.OrderStatus;
import com.example.orderprocessing.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest request) {
        Order order = OrderMapper.toEntity(request);
        Order saved = service.createOrder(order);
        return ResponseEntity.ok(OrderMapper.toDto(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(OrderMapper.toDto(service.getOrder(id)));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> listOrders(@RequestParam(required = false) OrderStatus status) {
        List<Order> orders = service.listOrders(Optional.ofNullable(status));
        List<OrderResponse> resp = orders.stream().map(OrderMapper::toDto).collect(Collectors.toList());
        return ResponseEntity.ok(resp);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<OrderResponse> updateStatus(@PathVariable Long id, @Valid @RequestBody UpdateStatusRequest req) {
        return ResponseEntity.ok(OrderMapper.toDto(service.updateStatus(id, req.getStatus())));
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<String> cancel(@PathVariable Long id) {
        service.cancelOrder(id);
        return ResponseEntity.ok("Order cancelled");
    }
}
