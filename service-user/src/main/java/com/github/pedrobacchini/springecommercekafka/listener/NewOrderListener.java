package com.github.pedrobacchini.springecommercekafka.listener;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.github.pedrobacchini.springecommercekafka.domain.Order;
import com.github.pedrobacchini.springecommercekafka.entity.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NewOrderListener {

    @Autowired
    private final DynamoDBMapper dynamoDBMapper;

    private final Logger logger = LoggerFactory.getLogger(NewOrderListener.class);

    public NewOrderListener(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

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
        dynamoDBMapper.save(new User(UUID.randomUUID().toString(), email));
    }

    private boolean isNewUser(String email) {
        return true;
    }
}
