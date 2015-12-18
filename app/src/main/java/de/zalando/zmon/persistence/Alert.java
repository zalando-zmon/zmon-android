package de.zalando.zmon.persistence;

import com.orm.SugarRecord;

import java.util.Date;

public class Alert extends SugarRecord<Alert> {

    private String alertDefinitionId;

    private String name;

    private Date lastModified;

    private String teamName;

    public String getAlertDefinitionId() {
        return alertDefinitionId;
    }

    public void setAlertDefinitionId(String alertDefinitionId) {
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

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public static Alert of(String alertId, String name) {
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
