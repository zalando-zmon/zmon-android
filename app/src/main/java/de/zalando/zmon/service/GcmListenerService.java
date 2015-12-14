package de.zalando.zmon.service;

import android.os.Bundle;
import android.util.Log;

public class GcmListenerService extends com.google.android.gms.gcm.GcmListenerService {

    @Override
    public void onMessageReceived(String from, Bundle data) {
        super.onMessageReceived(from, data);
        Log.i("[gcm]", "Received message");
        Log.i("[gcm]", "  --> From: " + from);

        for (String key : data.keySet()) {
            Log.i("[gcm]", "  --> " + key + ": " + data.getString(key));
        }
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
