package de.zalando.zmon.client.domain;

public class AlertHeader {

    private String id;

    private String name;

    private String team;
    private String responsibleTeam;

    public String getId() {
        return id;
    }

    public void setAlertId(String id) {
        this.id = id;
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
