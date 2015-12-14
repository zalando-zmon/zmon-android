package de.zalando.zmon.service;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;

import java.io.IOException;

import de.zalando.zmon.R;
import de.zalando.zmon.util.InstanceIdTokenStore;

public class SetupInstanceIDService extends IntentService {

    public SetupInstanceIDService() {
        super("SetupInstanceIDService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.i("[gcm]", "Setup InstanceID");

        try {
            InstanceID instanceId = InstanceID.getInstance(this);

            String id = instanceId.getId();
            String token = instanceId.getToken(
                    getString(R.string.gcm_sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE,
                    null);

            Log.d("[gcm]", "Received a token " + token + " for instanceId " + id);

            if (new InstanceIdTokenStore(getApplicationContext()).setToken(token)) {
                Log.i("[gcm]", "Updated InstanceID token: " + token);
            } else {
                Log.e("[gcm]", "Failed to set InstanceID token");
            }

        } catch (IOException e) {
            Log.e("[gcm]", "IOException: " + e.getMessage(), e);
        }
    }
}
