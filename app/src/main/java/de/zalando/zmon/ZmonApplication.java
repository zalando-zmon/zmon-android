package de.zalando.zmon;

import android.content.Intent;
import android.os.AsyncTask;

import com.orm.SugarApp;

import de.zalando.zmon.auth.Credentials;
import de.zalando.zmon.auth.CredentialsStore;
import de.zalando.zmon.service.SetupInstanceIDService;
import de.zalando.zmon.service.SyncSubscriptionsService;
import de.zalando.zmon.task.GetAccessTokenTask;
import de.zalando.zmon.task.RegisterDeviceTask;
import de.zalando.zmon.util.InstanceIdTokenStore;

public class ZmonApplication extends SugarApp {

    @Override
    public void onCreate() {
        super.onCreate();
        startService(new Intent(this, SetupInstanceIDService.class));
        registerForPushNotifications();
    }

    public void registerForPushNotifications() {
        final CredentialsStore credentialsStore = new CredentialsStore(this);
        Credentials credentials = credentialsStore.getCredentials();

        if (credentials.getUsername() != null && credentials.getPassword() != null) {
            new GetAccessTokenTask(this) {
                @Override
                protected void onPostExecute(String accessToken) {
                    credentialsStore.setAccessToken(accessToken);
                    InstanceIdTokenStore tokenStore = new InstanceIdTokenStore(ZmonApplication.this);
                    new RegisterDeviceTask(ZmonApplication.this).execute(tokenStore.getToken());
                }
            }.execute(credentials);
        }
    }

    public void syncAlertSubscriptions() {
        startService(new Intent(this, SyncSubscriptionsService.class));
    }
}
