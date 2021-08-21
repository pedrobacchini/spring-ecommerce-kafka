package com.github.pedrobacchini.springecommercekafka.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GenerateAllReportsController {

    @Autowired
    private KafkaTemplate<String, String> batchDispatcher;

    private final Logger logger = LoggerFactory.getLogger(GenerateAllReportsController.class);

    @GetMapping("/admin/generate-reports")
    private String generateAllReports() {

        String message = "USER_GENERAING_READING_REPORT";
        batchDispatcher.send("SEND_MESSAGE_TO_ALL_USERS", "USER_GENERAING_READING_REPORT", message)
                .addCallback(generatingReadingReportsCallback(message));

        logger.info("Sent generate report to all users");

        return "Report requests generated";
    }

    private ListenableFutureCallback<SendResult<String, String>> generatingReadingReportsCallback(String message) {
        return new ListenableFutureCallback<>() {
            @Override
            public void onSuccess(SendResult<String, String> result) {
                logger.info("Sent message=[" + message + "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }

            @Override
            public void onFailure(Throwable ex) {
                logger.info("Unable to send message=[" + message + "] due to : " + ex.getMessage());
            }
        };
    }
}
