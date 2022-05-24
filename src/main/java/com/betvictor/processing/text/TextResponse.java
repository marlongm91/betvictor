package com.betvictor.processing.text;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TextResponse {

    private String freqWord;
    private int avgParagraphSize;
    private long avgParagraphProcessingTime;
    private long totalProcessingTime;
}
