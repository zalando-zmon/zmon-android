package de.zalando.zmon.client.domain;

public class AlertSubscription {

    private String alertId;

    public AlertSubscription() {
    }

    public AlertSubscription(String alertId) {
        this.alertId = alertId;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }
}
