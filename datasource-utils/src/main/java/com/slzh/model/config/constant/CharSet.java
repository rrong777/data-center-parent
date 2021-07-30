package com.slzh.model.config.constant;

public enum CharSet {
    UTF_8("utf-8");
    private final String value;

    private CharSet(String value) {
        this.value = value;
    }
    public String getValue() {
        return this.value;
    }
}
