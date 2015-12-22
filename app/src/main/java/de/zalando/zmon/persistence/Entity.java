package de.zalando.zmon.persistence;

import com.google.gson.JsonElement;

import java.util.Date;

public class Entity {

    private String name;
    private String worker;

    private Date startTime;

    private JsonElement captures;
    private JsonElement value;

    public Entity() {
    }

    public Entity(String name, String worker, Date startTime) {
        this.name = name;
        this.worker = worker;
        this.startTime = startTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWorker() {
        return worker;
    }

    public void setWorker(String worker) {
        this.worker = worker;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public JsonElement getCaptures() {
        return captures;
    }

    public void setCaptures(JsonElement captures) {
        this.captures = captures;
    }

    public JsonElement getValue() {
        return value;
    }

    public void setValue(JsonElement value) {
        this.value = value;
    }
}
