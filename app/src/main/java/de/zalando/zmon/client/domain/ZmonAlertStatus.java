package de.zalando.zmon.client.domain;

import com.google.common.base.MoreObjects;

import java.util.List;

public class ZmonAlertStatus {

    private String message;

    private List<ZmonEntity> entities;

    private ZmonAlertDefinition alertDefinition;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ZmonEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<ZmonEntity> entities) {
        this.entities = entities;
    }

    public ZmonAlertDefinition getAlertDefinition() {
        return alertDefinition;
    }

    public void setAlertDefinition(ZmonAlertDefinition alertDefinition) {
        this.alertDefinition = alertDefinition;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("message", message)
                .add("entities", entities)
                .add("alertDefinition", alertDefinition)
                .toString();
    }
}
