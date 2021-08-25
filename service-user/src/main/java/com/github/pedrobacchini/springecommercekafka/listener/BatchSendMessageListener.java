package com.github.pedrobacchini.springecommercekafka.listener;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBScanExpression;
import com.github.pedrobacchini.springecommercekafka.entity.User;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BatchSendMessageListener {

    @Autowired
    private final DynamoDBMapper dynamoDBMapper;

    @Autowired
    private KafkaTemplate<String, User> userDispatcher;

    private final Logger logger = LoggerFactory.getLogger(BatchSendMessageListener.class);

    public BatchSendMessageListener(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;
    }

    @KafkaListener(
            id = "batchSendMessageListener",
            topics = "SEND_MESSAGE_TO_ALL_USERS"
    )
    public void consume(ConsumerRecord<String, String> record) {
        logger.info("__________________________________");
        logger.info("Processing new batch");
        logger.info("Topic: " + record.value());

        for (User user : getAllUsers()) {
            userDispatcher.send(record.value(), user.getUuid(), user);
        }
    }

    private List<User> getAllUsers() {
        return dynamoDBMapper.scan(User.class, new DynamoDBScanExpression());
    }
}
