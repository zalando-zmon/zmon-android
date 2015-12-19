package de.zalando.zmon;

import android.content.Intent;

import com.orm.SugarApp;

import de.zalando.zmon.service.SetupInstanceIDService;
import de.zalando.zmon.service.SyncSubscriptionsService;
import de.zalando.zmon.task.RegisterDeviceTask;
import de.zalando.zmon.util.InstanceIdTokenStore;

public class ZmonApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, SetupInstanceIDService.class));
    }

    public void registerForPushNotifications() {
        InstanceIdTokenStore tokenStore = new InstanceIdTokenStore(this);
        new RegisterDeviceTask(this).execute(tokenStore.getToken());
    }

    public void syncAlertSubscriptions() {
        startService(new Intent(this, SyncSubscriptionsService.class));
    }
}
