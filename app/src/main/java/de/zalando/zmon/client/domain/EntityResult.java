package de.zalando.zmon.client.domain;

import com.google.gson.JsonElement;

import java.util.Date;

public class EntityResult {

    private float ts;
    private float td;

    private String worker;

    private Date startTime;

    private JsonElement captures;
    private JsonElement value;

    public float getTs() {
        return ts;
    }

    public void setTs(float ts) {
        this.ts = ts;
    }

    public float getTd() {
        return td;
    }

    public void setTd(float td) {
        this.td = td;
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
