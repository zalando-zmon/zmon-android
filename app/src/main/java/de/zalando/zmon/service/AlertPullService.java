package de.zalando.zmon.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Collection;
import java.util.List;

import de.zalando.zmon.ZmonApplication;
import de.zalando.zmon.client.domain.ZmonAlertStatus;
import de.zalando.zmon.persistence.Team;
import de.zalando.zmon.task.GetZmonAlertsTask;

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
                if (alertStatusList != null) {
                    Log.d("zmon", "Received list with " + alertStatusList.size() + " alerts");
                    // TODO
                }
                else {
                    Log.w("zmon", "Received 'null' alerts");
                }
            }
        }).execute(getTeams());

        return START_CONTINUATION_MASK;
    }

    private String[] getTeams() {
        Collection<String> teamNames = Team.getAllTeamNames();
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
                PendingIntent.FLAG_ONE_SHOT);

        alarmManager.setRepeating(
                AlarmManager.ELAPSED_REALTIME_WAKEUP,
                0,
                1000 * 1 * 5,
                pendingIntent);
    }
}
