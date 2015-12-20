package de.zalando.zmon.task;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.google.common.base.Strings;

import java.util.ArrayList;
import java.util.List;

import de.zalando.zmon.R;
import de.zalando.zmon.client.NotificationService;
import de.zalando.zmon.client.ServiceFactory;
import retrofit.client.Response;

public class UnregisterAlertTask extends AsyncTask<String, Void, List<String>> {

    private final Context context;

    public UnregisterAlertTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<String> doInBackground(String... alertIds) {
        List<String> unregisteredAlerts = new ArrayList<>();

        for (String alertId : alertIds) {
            if (unregisterAlert(alertId)) {
                unregisteredAlerts.add(alertId);
            }
        }

        return unregisteredAlerts;
    }

    private boolean unregisterAlert(String alertId) {
        if (Strings.isNullOrEmpty(alertId)) {
            Log.w("[rest]", "Did not receive a valid alert id: " + alertId);
            return false;
        }

        NotificationService service = ServiceFactory.createNotificationService(context);
        Response response = service.unregisterAlert(alertId);

        if (response.getStatus() == 200) {
            Log.i("[rest]", "Successfully unregistered alert " + alertId + " for monitoring");
            displayToastMessage(R.string.unregister_alert_success_message, alertId);
        } else {
            displayToastMessage(R.string.unregister_alert_error_message, alertId);
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
