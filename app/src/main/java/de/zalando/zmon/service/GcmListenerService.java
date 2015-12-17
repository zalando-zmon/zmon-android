package de.zalando.zmon.service;

import android.os.Bundle;
import android.util.Log;

import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.client.ZmonService;
import de.zalando.zmon.client.domain.AlertDetails;
import de.zalando.zmon.util.NotificationHelper;
import de.zalando.zmon.util.TopicAlertIdExtractor;

public class GcmListenerService extends com.google.android.gms.gcm.GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        Log.i("[gcm]", "Received message");
        Log.i("[gcm]", "  --> From: " + from);

        for (String key : data.keySet()) {
            Log.i("[gcm]", "  --> " + key + ": " + data.getString(key));
        }

        String alertId = TopicAlertIdExtractor.extractId(from);

        ZmonService zmonService = ServiceFactory.createZmonService(getApplicationContext());
        AlertDetails alert = zmonService.getAlertDetails(alertId);

        new NotificationHelper(getApplicationContext()).publishNewAlert(alert);
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
