package com.betvictor.processing.text;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

@Service
@Slf4j
@RequiredArgsConstructor
public class TextServiceImpl implements TextService {

    private final RestTemplate template;


    @Value("${loremIpsumUrl}")
    private String loremIpsumUrl;

    @Override
    public TextResponse getText(int paragraphNumber, ParagraphLengths length) {
        var current = new DummyResponse();
        long startProcess = System.currentTimeMillis();
        log.debug("Starting to process Lorem Ipsum response at.." + startProcess);
        IntStream.range(1, paragraphNumber)
                .mapToObj(i -> getBuilder(i, length))
                .map(uriBuilder -> template.getForObject(tryGetURI(uriBuilder), String.class))
                .forEach(dummy -> updateCurrentDummy(current, processDummy(dummy)));
        long processFinished = System.currentTimeMillis();
        log.debug("Process finished at.." + processFinished);
        var response = new TextResponse();
        response.setFreqWord(current.getWord());
        response.setAvgParagraphSize(current.getTotalParagraphSize() / current.getTotalParagraphs());
        response.setAvgParagraphProcessingTime(current.getTotalParagraphsProcessingTime() / current.getTotalParagraphs());
        response.setTotalProcessingTime(processFinished - startProcess);
        return response;
    }

    private DummyResponse processDummy(String dummy) {
        var paragraphs = dummy.split("\\n\\n");
        int paragraphsTotalSize = 0;
        var words = new HashMap<String, Integer>();
        int totalProcessingTime = 0;
        for (String p : paragraphs) {
            long start = System.currentTimeMillis();
            log.debug("Starting to process a paragraph at.." + start);
            System.out.println("Starting to process a paragraph at.." + start);
            var paragraph = p.subSequence(p.indexOf(">") + 1, p.lastIndexOf("<")).toString();
            paragraphsTotalSize += paragraph.length();
            Arrays.stream(paragraph.split(" "))
                    .forEach(w -> addWord(w.toLowerCase(), words));
            long end = System.currentTimeMillis();
            log.debug("Paragraph process finished at.." + start);
            totalProcessingTime += end - start;
        }
        long start = System.currentTimeMillis();
        log.debug("Searching most frequent word at.." + start);
        int maxNumber = Collections.max(words.values());
        var repeatedWord = "";
        for (Map.Entry<String, Integer> w : words.entrySet())
            if (w.getValue() == maxNumber && (repeatedWord.isEmpty() || w.getKey().compareTo(repeatedWord) <= 0))
                repeatedWord = w.getKey();
        long end = System.currentTimeMillis();
        log.debug("Search finished at " + end);
        totalProcessingTime += end - start;
        return DummyResponse.fromProcessed(repeatedWord, maxNumber, paragraphsTotalSize, totalProcessingTime, paragraphs.length);
    }

    private void updateCurrentDummy(DummyResponse current, DummyResponse processDummy) {
        if (current.getRepetitionNumber() < processDummy.getRepetitionNumber() && (current.getWord().isEmpty() || processDummy.getWord().compareTo(current.getWord()) <= 0)) {
            current.setWord(processDummy.getWord());
            current.setRepetitionNumber(processDummy.getRepetitionNumber());
        }
        current.setTotalParagraphs(current.getTotalParagraphs() + processDummy.getTotalParagraphs());
        current.setTotalParagraphSize(current.getTotalParagraphSize() + processDummy.getTotalParagraphSize());
        current.setTotalParagraphsProcessingTime(current.getTotalParagraphsProcessingTime() + processDummy.getTotalParagraphsProcessingTime());
    }

    private void addWord(String w, Map<String, Integer> words) {
        if (words.containsKey(w))
            words.put(w, words.get(w) + 1);
        else
            words.put(w, 1);
    }

    public URIBuilder getBuilder(int paragraphNumber, ParagraphLengths length) {
        URIBuilder uriBuilder = new URIBuilder();
        try {
            uriBuilder = new URIBuilder(loremIpsumUrl + "/" + paragraphNumber + "/" + length.getLength());
        } catch (URISyntaxException e) {
            log.error("Error building uri for Lorem Ipsum API ", e);
        }
        return uriBuilder;
    }

    public URI tryGetURI(URIBuilder uriBuilder) {
        try {
            return uriBuilder.build();
        } catch (URISyntaxException e) {
            log.error("Error building uri for Lorem Ipsum API ", e);
        }
        return null;
    }

    @Data
    public static class DummyResponse {
        String word = "";
        int repetitionNumber;
        private int totalParagraphs;
        private int totalParagraphsProcessingTime;
        private int totalParagraphSize;
        private int totalProcessingTime;

        public static DummyResponse fromProcessed(String repeatedWord, int maxNumber, int paragraphsTotalSize, int totalProcessingTime, int length) {
            var dummyResponse = new DummyResponse();
            dummyResponse.setWord(repeatedWord);
            dummyResponse.setRepetitionNumber(maxNumber);
            dummyResponse.setTotalParagraphSize(paragraphsTotalSize);
            dummyResponse.setTotalParagraphsProcessingTime(totalProcessingTime);
            dummyResponse.setTotalParagraphs(length);
            return dummyResponse;
        }
    }
}
