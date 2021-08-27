package com.github.pedrobacchini.springecommercekafka.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.stereotype.Service;

@Service
public class LogListener {

    private final Logger logger = LoggerFactory.getLogger(LogListener.class);

    @KafkaListener(
            id = "logService",
            topicPattern = "ECOMMERCE.*"
    )
    public void consume(ConsumerRecord<String, String> record, @Headers MessageHeaders headers) {
        logger.info("__________________________________");
        logger.info("LOG " + record.topic());
        logger.info(record.key());
        logger.info(record.value());
        logger.info(String.valueOf(record.partition()));
        logger.info(String.valueOf(record.offset()));
        logger.info(String.valueOf(headers));
    }
}
