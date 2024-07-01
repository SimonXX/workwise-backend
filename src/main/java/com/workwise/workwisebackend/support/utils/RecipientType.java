package com.workwise.workwisebackend.support.utils;


public enum RecipientType {
    CANDIDATE("candidate"),
    COMPANY("company");

    private final String value;

    RecipientType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}