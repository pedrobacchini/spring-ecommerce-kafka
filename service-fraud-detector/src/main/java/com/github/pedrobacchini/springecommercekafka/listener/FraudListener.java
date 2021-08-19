package com.github.pedrobacchini.springecommercekafka.listener;

import com.github.pedrobacchini.springecommercekafka.domain.Order;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FraudListener {

    private final Logger logger = LoggerFactory.getLogger(FraudListener.class);

    @KafkaListener(
            id = "fraudDetector",
            topics = "ECOMMERCE_NEW_ORDER",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(ConsumerRecord<String, Order> record) {

        logger.info("__________________________________");
        logger.info("Processing new order, checking for fraud");
        logger.info(record.key());
        logger.info(String.valueOf(record.value()));
        logger.info(String.valueOf(record.partition()));
        logger.info(String.valueOf(record.offset()));
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Order order = record.value();
        if (isFraud(order)) {
            logger.info("Order is a fraud!!!!!");
        } else {
            logger.info("Approved: " + order);
        }
    }

    private boolean isFraud(Order order) {
        return order.getAmount().compareTo(String.valueOf(new BigDecimal("45000"))) >= 0;
    }
}
