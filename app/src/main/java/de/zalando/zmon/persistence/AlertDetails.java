package de.zalando.zmon.persistence;

import com.orm.SugarRecord;

import java.util.List;


public class AlertDetails extends SugarRecord<AlertDetails> {

    private String message;

    private AlertDefinition alertDefinition;

    private List<Entity> entities;

    //"entities_exclude" : [],
    //"notifications" : [],

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
