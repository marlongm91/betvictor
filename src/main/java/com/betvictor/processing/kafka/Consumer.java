package com.betvictor.processing.kafka;

import com.betvictor.processing.text.TextResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@Slf4j
public class Consumer {

//    @KafkaListener(
//            topics = "word.processed",
//            containerFactory = "wordProcessedListenerContainerFactory")
//    public void textProcessedListener(TextResponse text) {
//        System.out.println(text);
//    }
}
