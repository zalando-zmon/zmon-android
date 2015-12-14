package de.zalando.zmon;

import android.content.Intent;

import com.orm.SugarApp;

import de.zalando.zmon.auth.Authorization;
import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.client.ZmonAlertsService;
import de.zalando.zmon.client.ZmonLoginService;
import de.zalando.zmon.client.ZmonStatusService;
import de.zalando.zmon.client.ZmonTeamService;
import de.zalando.zmon.service.AlertPullService;
import de.zalando.zmon.service.SetupInstanceIDService;

public class ZmonApplication extends SugarApp {

    private Authorization authorization;

    @Override
    public void onCreate() {
        super.onCreate();
        AlertPullService.setup(this);

        startService(new Intent(this, SetupInstanceIDService.class));
    }

    @Deprecated
    public ZmonLoginService getZmonLoginService() {
        return ServiceFactory.createZmonLoginService();
    }

    @Deprecated
    public ZmonStatusService getZmonStatusService() {
        return ServiceFactory.createZmonStatusService();
    }

    @Deprecated
    public ZmonAlertsService getZmonAlertsService() {
        return ServiceFactory.createZmonAlertService();
    }

    @Deprecated
    public ZmonTeamService getZmonTeamService() {
        return ServiceFactory.createZmonTeamService();
    }

    public Authorization getAuthorization() {
        return authorization;
    }

    public void setAuthorization(Authorization authorization) {
        this.authorization = authorization;
    }
}
