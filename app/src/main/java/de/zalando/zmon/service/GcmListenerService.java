package de.zalando.zmon.service;

import android.os.Bundle;
import android.util.Log;

import de.zalando.zmon.util.NotificationHelper;

public class GcmListenerService extends com.google.android.gms.gcm.GcmListenerService {

    private static final String NOTIFICATION_TITLE = "gcm.notification.title";
    private static final String NOTIFICATION_BODY = "gcm.notification.body";
    private static final String NOTIFICATION_ALERT_ID = "alert_id";
    private static final String NOTIFICATION_ENTITY_ID = "entity_id";

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        Log.i("[gcm]", "Received message");
        Log.i("[gcm]", "  --> From: " + from);

        for (String key : data.keySet()) {
            Log.i("[gcm]", "  --> " + key + ": " + data.getString(key));
        }

        String title = data.getString(NOTIFICATION_TITLE);
        String message = data.getString(NOTIFICATION_BODY);
        String entity = data.getString(NOTIFICATION_ENTITY_ID);
        String alertId = data.getString(NOTIFICATION_ALERT_ID);

        new NotificationHelper(getApplicationContext())
                .publishNewAlert(alertId, title, message + " / " + entity + " (" + alertId + ")");
    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
        Log.i("[gcm]", "Deleted messages");
    }

    @Override
    public void onMessageSent(String msgId) {
        super.onMessageSent(msgId);
        Log.i("[gcm]", "Message sent: " + msgId);
    }

    @Override
    public void onSendError(String msgId, String error) {
        super.onSendError(msgId, error);
        Log.i("[gcm]", "Send error: " + error + " (" + msgId + ")");
    }
}
