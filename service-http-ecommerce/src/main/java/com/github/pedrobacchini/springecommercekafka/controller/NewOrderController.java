package com.github.pedrobacchini.springecommercekafka.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class NewOrderController {

    @Autowired
    private KafkaTemplate<Object, Object> template;

    @GetMapping("/new")
    private String generateNewOrder(@RequestParam String email, @RequestParam String amount) {
        var orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, amount, email);
        template.send("ECOMMERCE_NEW_ORDER", email, order);
        System.out.println("New Order sent successfully.");
        return "New order sent";
    }
}
