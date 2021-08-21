package com.github.pedrobacchini.springecommercekafka.controller;

import com.github.pedrobacchini.springecommercekafka.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class NewOrderController {

    @Autowired
    private KafkaTemplate<String, Order> orderDispatcher;

    @Autowired
    private KafkaTemplate<String, String> emailDispatcher;

    private final Logger logger = LoggerFactory.getLogger(NewOrderController.class);

    @GetMapping("/new")
    private String generateNewOrder(@RequestParam String email, @RequestParam String amount) {
        var orderId = UUID.randomUUID().toString();
        Order order = new Order(orderId, amount, email);

        orderDispatcher.send("ECOMMERCE_NEW_ORDER", email, order).addCallback(generateNewOrderCallback(order));

        var emailCode = "Thank you for your order! We are processing your order";
        emailDispatcher.send("ECOMMERCE_SEND_EMAIL", email, emailCode).addCallback(sendEmailCallback(emailCode));

        return "New order sent";
    }

    private ListenableFutureCallback<SendResult<String, Order>> generateNewOrderCallback(Order order) {
        return new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, Order> result) {
                logger.info("Sent message=[" + order + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info("Unable to send message=[" + order + "] due to : " + ex.getMessage());
            }
        };
    }

    private ListenableFutureCallback<SendResult<String, String>> sendEmailCallback(String emailCode) {
        return new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Sent message=[" + emailCode + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info("Unable to send message=[" + emailCode + "] due to : " + ex.getMessage());
            }
        };
    }
}
