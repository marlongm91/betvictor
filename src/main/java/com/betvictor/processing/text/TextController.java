package com.betvictor.processing.text;

import com.betvictor.processing.kafka.Producer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated()
public class TextController {
    private final TextService service;
    private final Producer producer;


    @GetMapping(value = "/betvictor/text")
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    public TextResponse getText(@RequestParam(value = "p") int paragraphNumber, @RequestParam(value = "l") ParagraphLengths length) {

        log.debug("Processing request with p: {} and l: {}", paragraphNumber, length);
        var response = service.getText(paragraphNumber, length);
        producer.sendMessage(response);
        return response;
    }
}

