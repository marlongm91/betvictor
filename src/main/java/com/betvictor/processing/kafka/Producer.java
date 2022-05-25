package com.betvictor.processing.kafka;

import com.betvictor.processing.text.TextResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class Producer {
    private static final String TOPIC = "word.processed";
    private final KafkaTemplate<String, TextResponse> template;

    public void sendMessage(TextResponse wordProcessed) {
        log.info(String.format("#### -> Producing message -> %s", wordProcessed));
        template.send(TOPIC, wordProcessed);
    }

}
