package com.slzh.exception;

public class ResponseErrorMessage {

    private int code;

    private String message;

    public ResponseErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getReason() {
        return message;
    }

    public void setReason(String msg) {
        this.message = msg;
    }
}

