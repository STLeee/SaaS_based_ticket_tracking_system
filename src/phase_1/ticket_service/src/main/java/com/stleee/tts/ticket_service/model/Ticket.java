package com.stleee.tts.ticket_service.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tickets")
public class Ticket {
    public enum Type {
        Bug
    }

    public enum Status {
        Opened, Closed
    }

    @Id
    private String id;
    private Type type;
    private Status status;
    private String summary;
    private String description;

    public Ticket (Type type, String summary, String description) {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
        this.type = type;
        this.status = Status.Opened;
        this.summary = summary;
        this.description = description;
    }

    public String getSID () {
        return this.id;
    }

    public void setSID (String id) {
        this.id = id;
    }

    public Type getType () {
        return this.type;
    }

    public void setType (Type type) {
        this.type = type;
    }

    public Status getStatus () {
        return this.status;
    }

    public void setStatus (Status status) {
        this.status = status;
    }

    public String getSummary () {
        return this.summary;
    }

    public void setSummary (String summary) {
        this.summary = summary;
    }

    public String getDescription () {
        return this.description;
    }

    public void setDescription (String description) {
        this.description = description;
    }
}
