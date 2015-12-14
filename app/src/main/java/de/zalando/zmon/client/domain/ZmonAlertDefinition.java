package de.zalando.zmon.client.domain;

import com.google.common.base.MoreObjects;

import java.util.Date;

public class ZmonAlertDefinition {

    private int id;
    private int checkDefinitionId;
    private int priority;

    private boolean cloneable;
    private boolean deletable;
    private boolean editable;

    private String name;
    private String description;
    private String status;
    private String condition;
    private String lastModifiedBy;
    private String team;
    private String responsibleTeam;

    private Date lastModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCheckDefinitionId() {
        return checkDefinitionId;
    }

    public void setCheckDefinitionId(int checkDefinitionId) {
        this.checkDefinitionId = checkDefinitionId;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public boolean isCloneable() {
        return cloneable;
    }

    public void setCloneable(boolean cloneable) {
        this.cloneable = cloneable;
    }

    public boolean isDeletable() {
        return deletable;
    }

    public void setDeletable(boolean deletable) {
        this.deletable = deletable;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
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

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("checkDefinitionId", checkDefinitionId)
                .add("name", name)
                .add("description", description)
                .add("priority", priority)
                .add("status", status)
                .add("team", team)
                .add("responsibleTeam", responsibleTeam)
                .toString();
    }
}
