package com.example.orderprocessing.service;

import com.example.orderprocessing.exception.InvalidOperationException;
import com.example.orderprocessing.exception.NotFoundException;
import com.example.orderprocessing.model.Order;
import com.example.orderprocessing.model.OrderStatus;
import com.example.orderprocessing.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository repo;

    public OrderService(OrderRepository repo) { this.repo = repo; }

    @Transactional
    public Order createOrder(Order order) {
        return repo.save(order);
    }

    public Order getOrder(Long id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("Order not found: " + id));
    }

    public List<Order> listOrders(Optional<OrderStatus> status) {
        if (status.isPresent()) {
            return repo.findByStatus(status.get());
        } else {
            return repo.findAll();
        }
    }

    @Transactional
    public Order updateStatus(Long id, OrderStatus newStatus) {
        Order order = getOrder(id);
        order.setStatus(newStatus);
        return repo.save(order);
    }

    @Transactional
    public void cancelOrder(Long id) {
        Order order = getOrder(id);
        if (order.getStatus() != OrderStatus.PENDING) {
            throw new InvalidOperationException("Only PENDING orders can be cancelled.");
        }
        order.setStatus(OrderStatus.CANCELLED);
        repo.save(order);
    }

    @Transactional
    public int processPendingOrders(int olderThanMinutes) {
        List<Order> pending = repo.findByStatus(OrderStatus.PENDING);
        // For production you might filter by createdAt older than X minutes; here we update all pending.
        pending.forEach(o -> o.setStatus(OrderStatus.PROCESSING));
        repo.saveAll(pending);
        return pending.size();
    }
}
