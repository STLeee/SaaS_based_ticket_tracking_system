package com.stleee.tts.backend.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("stuffs")
public class Stuff {
    public enum Type {
        QA, RD
    }

    @Id
    private String sid;
    private Type type;

    public Stuff (Type type) {
        this.sid = UUID.randomUUID().toString().replaceAll("-", "");
        this.type = type;
    }

    public String getSID () {
        return this.sid;
    }

    public void setSID (String sid) {
        this.sid = sid;
    }

    public Type getType () {
        return this.type;
    }

    public void setType (Type type) {
        this.type = type;
    }
}
