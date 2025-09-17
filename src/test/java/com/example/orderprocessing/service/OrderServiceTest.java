package com.example.orderprocessing.service;

import com.example.orderprocessing.model.Order;
import com.example.orderprocessing.model.OrderItem;
import com.example.orderprocessing.model.OrderStatus;
import com.example.orderprocessing.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class OrderServiceTest {

    @Autowired
    private OrderRepository repo;

    @Test
    void processPendingOrders_movesStatus() {
        OrderService service = new OrderService(repo);
        Order order = new Order("Alice");
        order.addItem(new OrderItem("Widget", 2, BigDecimal.valueOf(9.99)));
        repo.save(order);

        int moved = service.processPendingOrders(0);
        assertThat(moved).isEqualTo(1);

        Order persisted = repo.findById(order.getId()).orElseThrow();
        assertThat(persisted.getStatus()).isEqualTo(OrderStatus.PROCESSING);
    }
}
