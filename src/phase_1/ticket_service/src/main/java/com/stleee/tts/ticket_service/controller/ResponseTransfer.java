package com.stleee.tts.ticket_service.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

public class ResponseTransfer {

    private boolean ok;

    @JsonInclude(Include.NON_EMPTY)
    private Object result;

    @JsonInclude(Include.NON_EMPTY)
    private Integer errorCode;

    @JsonInclude(Include.NON_EMPTY)
    private String description;

    public ResponseTransfer(Object result) {
        this.ok = true;
        this.result = result;
    }

    public ResponseTransfer(Integer errorCode, String description) {
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

    public Integer getErrorCode() {
        return this.errorCode;
    }

    public String getDescription() {
        return this.description;
    }
}
