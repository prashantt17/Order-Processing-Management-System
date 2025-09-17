package com.example.orderprocessing.mapper;

import com.example.orderprocessing.dto.CreateOrderRequest;
import com.example.orderprocessing.dto.OrderResponse;
import com.example.orderprocessing.model.Order;
import com.example.orderprocessing.model.OrderItem;

import java.util.List;
import java.util.stream.Collectors;

public class OrderMapper {
    public static Order toEntity(CreateOrderRequest req) {
        Order order = new Order(req.getCustomerName());
        if (req.getItems() != null) {
            req.getItems().forEach(i -> {
                OrderItem oi = new OrderItem(i.getProductName(), i.getQuantity(), i.getUnitPrice());
                order.addItem(oi);
            });
        }
        return order;
    }

    public static OrderResponse toDto(Order order) {
        OrderResponse r = new OrderResponse();
        r.setId(order.getId());
        r.setCustomerName(order.getCustomerName());
        r.setCreatedAt(order.getCreatedAt());
        r.setStatus(order.getStatus());
        List<OrderResponse.OrderItemResponse> items = order.getItems().stream().map(i -> {
            OrderResponse.OrderItemResponse ir = new OrderResponse.OrderItemResponse();
            ir.setId(i.getId());
            ir.setProductName(i.getProductName());
            ir.setQuantity(i.getQuantity());
            ir.setUnitPrice(i.getUnitPrice());
            return ir;
        }).collect(Collectors.toList());
        r.setItems(items);
        return r;
    }
}
