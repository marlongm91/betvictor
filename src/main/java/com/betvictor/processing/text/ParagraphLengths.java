package com.betvictor.processing.text;

import lombok.Getter;

@Getter
public enum ParagraphLengths {
    SHORT("short"),
    MEDIUM("medium"),
    LONG("long"),
    VERYLONG("verylong");

    String length;

    ParagraphLengths(String length) {
        this.length = length;
    }
}
