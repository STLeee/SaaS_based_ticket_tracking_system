package com.stleee.tts.ticket_service.model;

public class Bug extends Ticket {
    public Bug (String summary, String description) {
        super(Type.Bug, summary, description);
    }
}
