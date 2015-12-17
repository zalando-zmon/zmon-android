package de.zalando.zmon;

import android.content.Intent;

import com.orm.SugarApp;

import de.zalando.zmon.service.SetupInstanceIDService;

public class ZmonApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, SetupInstanceIDService.class));
    }
}
