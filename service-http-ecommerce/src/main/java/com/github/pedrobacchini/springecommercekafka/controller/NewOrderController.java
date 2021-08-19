package com.github.pedrobacchini.springecommercekafka.controller;

import com.github.pedrobacchini.springecommercekafka.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class NewOrderController {

    @Autowired
    private KafkaTemplate<String, Order> template;

    private final Logger logger = LoggerFactory.getLogger(NewOrderController.class);

    @GetMapping("/new")
    private String generateNewOrder(@RequestParam String email, @RequestParam String amount) {
        var orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, amount, email);
        template.send("ECOMMERCE_NEW_ORDER", email, order);
        logger.info("New Order sent successfully.");
        return "New order sent";
    }
}
