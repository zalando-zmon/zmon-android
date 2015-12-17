package de.zalando.zmon.client.domain;

public class AlertDetails {

    private String message;

    private AlertDefinition alertDefinition;

    //"entities" : [],


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
