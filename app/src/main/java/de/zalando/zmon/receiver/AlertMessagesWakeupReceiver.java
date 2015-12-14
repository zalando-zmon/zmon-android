package de.zalando.zmon.receiver;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GcmReceiver;

public class AlertMessagesWakeupReceiver extends GcmReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        Log.i("[gcm]", "Received google cloud message");
        // XXX not sure what to do here; only used when application is in background?
    }
}
