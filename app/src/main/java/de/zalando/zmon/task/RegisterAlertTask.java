package de.zalando.zmon.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.gcm.GcmPubSub;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.client.ZmonAlertsService;
import de.zalando.zmon.client.domain.ZmonAlertStatus;
import de.zalando.zmon.persistence.Alert;
import de.zalando.zmon.util.InstanceIdTokenStore;

public class RegisterAlertTask extends AsyncTask<Long, Void, List<Long>> {

    private final Context context;

    public RegisterAlertTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<Long> doInBackground(Long... alertIds) {
        List<Long> registeredAlerts = new ArrayList<>();

        for (Long alertId : alertIds) {
            if (registerAlert(alertId)) {
                registeredAlerts.add(alertId);
            }
        }

        return registeredAlerts;
    }

    private boolean registerAlert(long alertId) {
        if (alertId < 0) {
            Log.w("[zmon]", "Did not receive a valid alert id: " + alertId);
            return false;
        }

        String token = new InstanceIdTokenStore(context).getToken();

        ZmonAlertsService zmonAlertService = ServiceFactory.createZmonAlertService(context);
        ZmonAlertStatus zmonAlertStatus = zmonAlertService.get(alertId).get(0);

        Alert alert = new Alert();
        alert.setAlertDefinitionId(zmonAlertStatus.getAlertDefinition().getId());
        alert.setName(zmonAlertStatus.getAlertDefinition().getName());
        alert.setLastModified(new Date());
        Alert.saveInTx(alert);

        try {
            GcmPubSub pubSub = GcmPubSub.getInstance(context);
            pubSub.subscribe(token, "/topics/zmon-alert-" + alertId, null);

            Log.i("[zmon]", "Successfully registered alert " + alertId + " for monitoring");
            displayToastMessage(R.string.register_alert_success_message, (int) alertId);

            return true;

        } catch (IOException e) {
            displayToastMessage(R.string.register_alert_error_text, (int) alertId);
        }

        return false;
    }

    private void displayToastMessage(final int stringId, final int alertId) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(
                        context,
                        context.getString(stringId, alertId),
                        Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }
}
