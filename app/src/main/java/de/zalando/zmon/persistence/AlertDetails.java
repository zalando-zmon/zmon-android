package de.zalando.zmon.persistence;

import com.orm.SugarRecord;


public class AlertDetails extends SugarRecord<AlertDetails> {

    private String message;

    private AlertDefinition alertDefinition;

    //"entities_exclude" : [],
    //"entities" : [],
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
}
