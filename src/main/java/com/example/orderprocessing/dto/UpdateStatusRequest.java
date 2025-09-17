package com.example.orderprocessing.dto;

import com.example.orderprocessing.model.OrderStatus;
import jakarta.validation.constraints.NotNull;

public class UpdateStatusRequest {
    @NotNull
    private OrderStatus status;

    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
}
