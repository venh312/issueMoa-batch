package com.issuemoa.batch.presentation.enums;

import lombok.Getter;

@Getter
public enum BatchType {
    NEWS("news"),
    YOUTUBE("youtube"),
    KEYWORD("keyword");

    private final String value;

    BatchType(String value) {
        this.value = value;
    }
}
