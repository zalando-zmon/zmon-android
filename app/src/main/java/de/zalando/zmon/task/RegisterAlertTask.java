package de.zalando.zmon.task;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.common.base.Strings;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.client.DataService;
import de.zalando.zmon.client.NotificationService;
import de.zalando.zmon.client.ServiceFactory;
import de.zalando.zmon.client.domain.AlertDetails;
import de.zalando.zmon.client.domain.AlertSubscription;
import de.zalando.zmon.persistence.Alert;

public class RegisterAlertTask extends HttpSafeAsyncTask<String, Void, List<String>> {

    public RegisterAlertTask(Context context) {
        super(context);
    }

    @Override
    protected List<String> callSafe(String... alertIds) throws IOException {
        List<String> registeredAlerts = new ArrayList<>();

        for (String alertId : alertIds) {
            if (registerAlert(alertId)) {
                registeredAlerts.add(alertId);
            }
        }

        return registeredAlerts;
    }

    private boolean registerAlert(String alertId) throws IOException {
        if (Strings.isNullOrEmpty(alertId)) {
            Log.w("[rest]", "Did not receive a valid alert id: " + alertId);
            return false;
        }

        final DataService dataService = ServiceFactory.createDataService(context);
        final NotificationService notificationService = ServiceFactory.createNotificationService(context);

        AlertDetails alertDetails = dataService
                .getAlertDetails(String.valueOf(alertId))
                .execute()
                .body();

        Alert alert = new Alert();
        alert.setAlertDefinitionId(alertDetails.getAlertDefinition().getId());
        alert.setName(alertDetails.getAlertDefinition().getName());
        alert.setTeamName(alertDetails.getAlertDefinition().getTeam());
        alert.setLastModified(new Date());
        Alert.saveInTx(alert);

        Response response = notificationService
                .registerAlert(new AlertSubscription(alertDetails.getAlertDefinition().getId()))
                .execute()
                .raw();

        if (response.code() == 200) {
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
