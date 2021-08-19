package com.github.pedrobacchini.springecommercekafka.listener;

import com.github.pedrobacchini.springecommercekafka.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.converter.RecordMessageConverter;
import org.springframework.kafka.support.converter.StringJsonMessageConverter;
import org.springframework.stereotype.Service;

@Service
public class FraudListener {

    private final Logger logger = LoggerFactory.getLogger(FraudListener.class);

    @Bean
    public RecordMessageConverter other() {
        return new StringJsonMessageConverter();
    }

    @KafkaListener(id = "fraudDetector", topics = "ECOMMERCE_NEW_ORDER")
    public void consume(Order order) {
        logger.info("Received: " + order);
    }
}
