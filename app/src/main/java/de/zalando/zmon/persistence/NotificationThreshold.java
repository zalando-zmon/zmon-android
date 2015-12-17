package de.zalando.zmon.persistence;

import com.orm.SugarRecord;

public class NotificationThreshold extends SugarRecord<NotificationThreshold> {

    private String comment;
    private String value;
    private String type;

    public NotificationThreshold() {
    }

    public NotificationThreshold(String comment, String value, String type) {
        this.comment = comment;
        this.value = value;
        this.type = type;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
