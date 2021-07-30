package com.slzh.model.config.constant;

public enum TimeZone {
    SHANGHAI("Asia/Shanghai");
    private final String value;

    private TimeZone(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}
