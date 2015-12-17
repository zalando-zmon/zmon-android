package de.zalando.zmon.client.domain;

public class Entity {

    private String entity;

    private EntityResult result;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public EntityResult getResult() {
        return result;
    }

    public void setResult(EntityResult result) {
        this.result = result;
    }
}
