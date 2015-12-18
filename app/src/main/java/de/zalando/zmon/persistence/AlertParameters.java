package de.zalando.zmon.persistence;

import com.orm.SugarRecord;

public class AlertParameters extends SugarRecord<AlertParameters> {

    private NotificationThreshold notificationThreshold;

    public AlertParameters() {
    }

    public AlertParameters(NotificationThreshold notificationThreshold) {
        this.notificationThreshold = notificationThreshold;
    }

    public NotificationThreshold getNotificationThreshold() {
        return notificationThreshold;
    }

    public void setNotificationThreshold(NotificationThreshold notificationThreshold) {
        this.notificationThreshold = notificationThreshold;
    }
}
