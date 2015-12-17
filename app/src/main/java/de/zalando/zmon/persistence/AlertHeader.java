package de.zalando.zmon.persistence;

import com.orm.SugarRecord;

public class AlertHeader extends SugarRecord<AlertHeader> {

    private String alertId;

    private String name;

    private String team;
    private String responsibleTeam;

    public AlertHeader(String alertId, String name, String team, String responsibleTeam) {
        this.alertId = alertId;
        this.name = name;
        this.team = team;
        this.responsibleTeam = responsibleTeam;
    }

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getResponsibleTeam() {
        return responsibleTeam;
    }

    public void setResponsibleTeam(String responsibleTeam) {
        this.responsibleTeam = responsibleTeam;
    }
}
