package de.zalando.zmon.client.domain;

import java.util.List;

public class AlertDetails {

    private String message;

    private AlertDefinition alertDefinition;

    private List<Entity> entities;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public AlertDefinition getAlertDefinition() {
        return alertDefinition;
    }

    public void setAlertDefinition(AlertDefinition alertDefinition) {
        this.alertDefinition = alertDefinition;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }
}
