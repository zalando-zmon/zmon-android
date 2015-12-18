package de.zalando.zmon.persistence;

import com.orm.SugarRecord;

import java.util.Date;
import java.util.List;

public class AlertDefinition extends SugarRecord<AlertDefinition> {

    private String alertId;

    private String name;
    private String status;
    private String team;
    private String responsibleTeam;
    private String description;
    private List<String> tags;

    private String parentId;

    private AlertParameters parameters;

    private String period; // XXX correct type?
    private String checkDefinitionId;
    private String condition;

    private Date lastModified;
    private String lastModifiedBy;

    private boolean cloneable;
    private boolean deletable;
    private boolean editable;
    private boolean template;

    private int priority;


    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
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

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getResponsibleTeam() {
        return responsibleTeam;
    }

    public void setResponsibleTeam(String responsibleTeam) {
        this.responsibleTeam = responsibleTeam;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public String getCheckDefinitionId() {
        return checkDefinitionId;
    }

    public void setCheckDefinitionId(String checkDefinitionId) {
        this.checkDefinitionId = checkDefinitionId;
    }

    public AlertParameters getParameters() {
        return parameters;
    }

    public void setParameters(AlertParameters parameters) {
        this.parameters = parameters;
    }

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
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

    public boolean isTemplate() {
        return template;
    }

    public void setTemplate(boolean template) {
        this.template = template;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
