package de.zalando.zmon.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.client.ZmonService;
import de.zalando.zmon.client.domain.AlertDetails;
import de.zalando.zmon.client.domain.AlertSubscription;
import de.zalando.zmon.persistence.Alert;
import retrofit.client.Response;

public class RegisterAlertTask extends AsyncTask<String, Void, List<String>> {

    private final Context context;

    public RegisterAlertTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<String> doInBackground(String... alertIds) {
        List<String> registeredAlerts = new ArrayList<>();

        for (String alertId : alertIds) {
            if (registerAlert(alertId)) {
                registeredAlerts.add(alertId);
            }
        }

        return registeredAlerts;
    }

    private boolean registerAlert(String alertId) {
        if (Strings.isNullOrEmpty(alertId)) {
            Log.w("[rest]", "Did not receive a valid alert id: " + alertId);
            return false;
        }

        ZmonService zmonService = ServiceFactory.createZmonService(context);
        AlertDetails alertDetails = zmonService.getAlertDetails(String.valueOf(alertId));

        Alert alert = new Alert();
        alert.setAlertDefinitionId(alertDetails.getAlertDefinition().getId());
        alert.setName(alertDetails.getAlertDefinition().getName());
        alert.setLastModified(new Date());
        Alert.saveInTx(alert);


        Response response = zmonService.registerAlert(
                new AlertSubscription(alertDetails.getAlertDefinition().getId()));

        if (response.getStatus() == 200) {
            Log.i("[rest]", "Successfully registered alert " + alertId + " for monitoring");
            displayToastMessage(R.string.register_alert_success_message, alertId);
        } else {
            displayToastMessage(R.string.register_alert_error_text, alertId);
        }

        return true;
    }

    private void displayToastMessage(final int stringId, final String alertId) {
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
