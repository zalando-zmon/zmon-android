package de.zalando.zmon.service;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.iid.InstanceIDListenerService;

public class InstanceIdService extends InstanceIDListenerService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        Log.i("[gcm]", "Received token refresh event -> setup InstanceID again");

        Intent intent = new Intent(this, SetupInstanceIDService.class);
        startService(intent);
    }
}
