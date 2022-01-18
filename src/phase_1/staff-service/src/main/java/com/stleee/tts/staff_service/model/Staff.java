package com.stleee.tts.staff_service.model;

import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("staffs")
public class Staff {
    public enum Type {
        QA,
        RD
    }

    @Id
    private String id;
    private Type type;

    public Staff () {
        this.id = UUID.randomUUID().toString().replaceAll("-", "");
        this.type = Type.QA;
    }

    public Staff (String id, Type type) {
        this.id = id;
        this.type = type;
    }

    public String getID () {
        return this.id;
    }

    public void setID (String id) {
        this.id = id;
    }

    public Type getType () {
        return this.type;
    }

    public void setType (Type type) {
        this.type = type;
    }

    public String toString() {
        return String.format("Staff[%s] %s ", this.type, this.id);
    }
}
