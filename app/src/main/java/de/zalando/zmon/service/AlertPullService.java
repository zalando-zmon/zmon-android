package de.zalando.zmon.service;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import de.zalando.zmon.ZmonApplication;
import de.zalando.zmon.client.domain.ZmonAlertStatus;
import de.zalando.zmon.persistence.Alert;
import de.zalando.zmon.persistence.Team;
import de.zalando.zmon.task.GetZmonAlertsTask;
import de.zalando.zmon.util.NotificationHelper;

public class AlertPullService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("zmon", "Start pulling alerts for observed teams");

        new GetZmonAlertsTask((ZmonApplication) getApplication(), new GetZmonAlertsTask.Callback() {
            @Override
            public void onError(Exception e) {
                // TODO
            }

            @Override
            public void onResult(List<ZmonAlertStatus> alertStatusList) {
                checkForNewAlerts(alertStatusList);
            }
        }).execute(getTeams());

        return START_CONTINUATION_MASK;
    }

    private void checkForNewAlerts(List<ZmonAlertStatus> alertStatusList) {
        List<Alert> oldAlerts = Alert.listAll(Alert.class);

        if (alertStatusList != null) {
            Log.d("zmon", "Online Alerts: " + alertStatusList.size() + " | Local Alerts: " + oldAlerts.size());
            List<ZmonAlertStatus> newAlerts = filterNewAlerts(oldAlerts, alertStatusList);

            if (newAlerts != null && !newAlerts.isEmpty()) {
                Log.i("zmon", "Found " + newAlerts.size() + " new alerts!!");
                new NotificationHelper(getApplicationContext()).publishNewAlerts(newAlerts);
            }

            updateRaisedAlerts(alertStatusList);
        } else {
            Log.w("zmon", "Received 'null' alerts");
        }
    }

    private void updateRaisedAlerts(List<ZmonAlertStatus> alertStatusList) {
        Alert.deleteAll(Alert.class);
        for (ZmonAlertStatus alertStatus : alertStatusList) {
            Alert.of(alertStatus.getAlertDefinition().getId(), alertStatus.getAlertDefinition().getName()).save();
        }
    }

    private List<ZmonAlertStatus> filterNewAlerts(List<Alert> oldAlerts, List<ZmonAlertStatus> alertStatusList) {
        List<ZmonAlertStatus> newAlerts = new ArrayList<>();

        for (ZmonAlertStatus alertStatus : alertStatusList) {
            int alertDefinitionId = alertStatus.getAlertDefinition().getId();

            if (isNewAlert(oldAlerts, alertDefinitionId)) {
                Log.i("zmon", "--> new alert: " + alertDefinitionId);
                newAlerts.add(alertStatus);
            }
        }

        return newAlerts;
    }

    private boolean isNewAlert(List<Alert> oldAlerts, int alertDefinitionId) {
        // TODO include entities into evaluation!
        for (Alert alert : oldAlerts) {
            if (alert.getAlertDefinitionId() == alertDefinitionId) {
                return false;
            }
        }

        return true;
    }

    private String[] getTeams() {
        Collection<String> teamNames = Team.getAllObservedTeamNames();
        return teamNames.toArray(new String[teamNames.size()]);
    }

    public static void setup(Context context) {
        Log.i("zmon", "Setup jobs");

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent serviceIntent = new Intent(context, AlertPullService.class);
        PendingIntent pendingIntent = PendingIntent.getService(
                context,
                0,
                serviceIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                0,
                1000 * 60 * 5,
                pendingIntent);
    }
}
