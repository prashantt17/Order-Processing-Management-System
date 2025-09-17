package com.example.orderprocessing.scheduler;

import com.example.orderprocessing.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PendingOrderProcessor {
    private final OrderService service;
    private final Logger log = LoggerFactory.getLogger(PendingOrderProcessor.class);

    // cron or fixed rate can be configured via application properties
    @Value("${orders.processor.fixedRateMillis:300000}")
    private long fixedRateMillis;

    public PendingOrderProcessor(OrderService service) {
        this.service = service;
    }

    @Scheduled(fixedRateString = "${orders.processor.fixedRateMillis:300000}")
    public void movePendingToProcessing() {
        int moved = service.processPendingOrders(0);
        if (moved > 0) {
            log.info("Moved {} pending orders to PROCESSING", moved);
        } else {
            log.debug("No pending orders to process");
        }
    }
}
