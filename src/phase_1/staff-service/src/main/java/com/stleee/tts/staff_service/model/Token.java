package com.stleee.tts.staff_service.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("tokens")
public class Token {

    @Id
    private String id;
    private String uid;

    public Token () {
        this.id = UUID.randomUUID().toString().replaceAll("-", "") + UUID.randomUUID().toString().replaceAll("-", "");
        this.uid = "";
    }

    public Token (String id, String uid) {
        this.id = id;
        this.uid = uid;
    }

    public String getID () {
        return this.id;
    }

    public void setID (String id) {
        this.id = id;
    }

    public String getUID () {
        return this.uid;
    }

    public void setUID (String uid) {
        this.uid = uid;
    }

    public String toString() {
        return String.format("Token[%s] %s ", this.uid, this.id);
    }
}
