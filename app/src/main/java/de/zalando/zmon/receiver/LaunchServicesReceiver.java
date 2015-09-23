package de.zalando.zmon.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import de.zalando.zmon.service.AlertPullService;

public class LaunchServicesReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        AlertPullService.setup(context);
    }
}
