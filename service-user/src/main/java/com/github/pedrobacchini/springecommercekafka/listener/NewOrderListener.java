package com.github.pedrobacchini.springecommercekafka.listener;

import com.github.pedrobacchini.springecommercekafka.domain.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NewOrderListener {

    private final Logger logger = LoggerFactory.getLogger(NewOrderListener.class);

    @KafkaListener(
            id = "userService",
            topicPattern = "ECOMMERCE_NEW_ORDER"
    )
    public void consume(ConsumerRecord<String, Order> record) {
        logger.info("__________________________________");
        logger.info("Processing new order, checking for new user");
        logger.info(String.valueOf(record.value()));
        var order = record.value();
        if (isNewUser(order.getEmail())) {
            insertNewUser(order.getEmail());
        }
    }

    private void insertNewUser(String email) {

    }

    private boolean isNewUser(String email) {
        return true;
    }
}