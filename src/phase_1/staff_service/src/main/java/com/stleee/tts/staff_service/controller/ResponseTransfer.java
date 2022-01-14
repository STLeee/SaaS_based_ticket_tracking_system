package com.stleee.tts.staff_service.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseTransfer {

    private int status;

    @JsonInclude(Include.NON_EMPTY)
    private Object result;

    @JsonInclude(Include.NON_EMPTY)
    private String error;

    public ResponseTransfer(Object result) {
        this.status = 200;
        this.result = result;
    }

    public ResponseTransfer(int status, String error) {
        this.status = status;
        this.error = error;
    }

    public int getStatus() {
        return this.status;
    }

    public Object getResult() {
        return this.result;
    }

    public String getError() {
        return this.error;
    }
}
