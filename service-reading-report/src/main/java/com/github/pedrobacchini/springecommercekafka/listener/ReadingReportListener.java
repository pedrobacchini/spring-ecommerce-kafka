package com.github.pedrobacchini.springecommercekafka.listener;

import com.github.pedrobacchini.springecommercekafka.entity.User;
import com.github.pedrobacchini.springecommercekafka.utils.IO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class ReadingReportListener {

    private static final Path SOURCE = new File("src/main/resources/report.txt").toPath();

    private final Logger logger = LoggerFactory.getLogger(ReadingReportListener.class);

    @KafkaListener(
            id = "readingReportListener",
            topics = "USER_GENERAING_READING_REPORT"
    )
    public void consume(ConsumerRecord<String, User> record) throws IOException {
        logger.info("__________________________________");
        User user = record.value();
        System.out.println("Processing report for " + user);
        File target = new File(user.getReportPath());
        IO.copyTo(SOURCE, target);
        IO.append(target, "Created for "+ user.getEmail());
        logger.info("File Created "+target.getAbsolutePath());
    }
}
