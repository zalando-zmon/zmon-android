package de.zalando.zmon.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmPubSub;

import java.io.IOException;
import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.client.ZmonAlertsService;
import de.zalando.zmon.client.domain.ZmonAlertStatus;
import de.zalando.zmon.util.InstanceIdTokenStore;

public class RegisterAlertService extends IntentService {

    private static final String EXTRA_ALERT_ID = "extra.alert.id";

    public static class RegisterAlertIntent extends Intent {
        public RegisterAlertIntent(Context context, int alertId) {
            super(context, RegisterAlertService.class);
            putExtra(EXTRA_ALERT_ID, alertId);
        }
    }

    public RegisterAlertService() {
        super("RegisterAlertService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        long alertId = intent.getIntExtra(EXTRA_ALERT_ID, -1);

        if (alertId < 0) {
            Log.w("[zmon]", "Did not receive a valid alert id: " + alertId);
            return;
        }

        String token = new InstanceIdTokenStore(getApplicationContext()).getToken();

        ZmonAlertsService zmonAlertService = ServiceFactory.createZmonAlertService();
        List<ZmonAlertStatus> zmonAlertStatus = zmonAlertService.get(alertId);
        // TODO store zmon alert in local storage so that users can unsubscribe later

        try {
            GcmPubSub pubSub = GcmPubSub.getInstance(getApplicationContext());
            pubSub.subscribe(token, "/topics/zmon-alert-" + alertId, null);

            Log.i("[zmon]", "Successfully registered alert " + alertId + " for monitoring");

        } catch (IOException e) {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.register_alert_error_text,
                    Toast.LENGTH_LONG)
                    .show();
        }
    }
}
