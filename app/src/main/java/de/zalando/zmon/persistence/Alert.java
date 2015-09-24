package de.zalando.zmon.persistence;

import com.orm.SugarRecord;

import java.util.Date;

public class Alert extends SugarRecord<Alert> {

    private int alertDefinitionId;

    private String name;

    private Date lastModified;

    public int getAlertDefinitionId() {
        return alertDefinitionId;
    }

    public void setAlertDefinitionId(int alertDefinitionId) {
        this.alertDefinitionId = alertDefinitionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public static Alert of(int alertId, String name) {
        Alert alert = new Alert();
        alert.setAlertDefinitionId(alertId);
        alert.setName(name);

        return alert;
    }

    @Override
    public void save() {
        lastModified = new Date();

        super.save();
    }
}
