package com.stleee.tts.ticket_service.controller;

public class ResponseTransfer {
    private boolean ok;
    private Object result;
    private int errorCode;
    private String description;

    public ResponseTransfer(Object result) {
        this.ok = true;
        this.result = result;
    }

    public ResponseTransfer(int errorCode, String description) {
        this.ok = false;
        this.errorCode = errorCode;
        this.description = description;
    }

    public boolean getOk() {
        return this.ok;
    }

    public Object getResult() {
        return this.result;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getDescription() {
        return this.description;
    }
}
