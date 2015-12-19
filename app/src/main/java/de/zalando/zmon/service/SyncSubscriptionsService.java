package de.zalando.zmon.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import de.zalando.zmon.task.SyncAlertSubscriptionsTask;

public class SyncSubscriptionsService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new SyncAlertSubscriptionsTask(this).execute();

        return START_NOT_STICKY;
    }
}
