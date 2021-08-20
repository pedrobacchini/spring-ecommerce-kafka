package com.github.pedrobacchini.springecommercekafka.controller.listener;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class EmailListener {

    private final Logger logger = LoggerFactory.getLogger(EmailListener.class);

    @KafkaListener(
            id = "emailService",
            topics = "ECOMMERCE_SEND_EMAIL"
    )
    public void consume(ConsumerRecord<String, String> record) {
        logger.info("__________________________________");
        logger.info("Send email");
        logger.info(record.key());
        logger.info(record.value());
        logger.info(String.valueOf(record.partition()));
        logger.info(String.valueOf(record.offset()));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("Email sent");
    }
}
