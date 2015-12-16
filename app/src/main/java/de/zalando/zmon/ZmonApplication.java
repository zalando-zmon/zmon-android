package de.zalando.zmon;

import android.content.Intent;

import com.orm.SugarApp;

import de.zalando.zmon.auth.Credentials;
import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.client.ZmonAlertsService;
import de.zalando.zmon.client.OAuthAccessTokenService;
import de.zalando.zmon.client.ZmonStatusService;
import de.zalando.zmon.client.ZmonTeamService;
import de.zalando.zmon.service.AlertPullService;
import de.zalando.zmon.service.SetupInstanceIDService;

public class ZmonApplication extends SugarApp {

    private Credentials credentials;

    @Override
    public void onCreate() {
        super.onCreate();
        AlertPullService.setup(this);

        startService(new Intent(this, SetupInstanceIDService.class));
    }

    @Deprecated
    public OAuthAccessTokenService getZmonLoginService() {
        return ServiceFactory.createZmonLoginService(this);
    }

    @Deprecated
    public ZmonStatusService getZmonStatusService() {
        return ServiceFactory.createZmonStatusService(this);
    }

    @Deprecated
    public ZmonAlertsService getZmonAlertsService() {
        return ServiceFactory.createZmonAlertService(this);
    }

    @Deprecated
    public ZmonTeamService getZmonTeamService() {
        return ServiceFactory.createZmonTeamService(this);
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public void setCredentials(Credentials credentials) {
        this.credentials = credentials;
    }
}
