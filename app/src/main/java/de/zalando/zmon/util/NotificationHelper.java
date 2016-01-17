package de.zalando.zmon.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import de.zalando.zmon.AlertDetailActivity;
import de.zalando.zmon.R;
import de.zalando.zmon.ZmonDashboardActivity;

public class NotificationHelper {

    private final Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public void publishNewAlert(String alertId, String title, String message) {
        if (isNotificationDisabled()) {
            Log.d("[notify]", "Notification is not created because it is disabled.");
            return;
        }

        Intent startApplicationIntent = new AlertDetailActivity.AlertDetailActivityIntent(context, alertId);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ZmonDashboardActivity.class);
        stackBuilder.addNextIntent(startApplicationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.zmon_logo)
                .setContentIntent(resultPendingIntent)
                .setLights(context.getResources().getColor(R.color.colorPrimary), 3000, 3000)
                .build();

        if (shouldNotificationVibrate()) {
            notification.defaults |= Notification.DEFAULT_VIBRATE;
        }

        if (shouldNotificationPlaySound()) {
            notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.zonk);
        }

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }

    private boolean isNotificationDisabled() {
        PreferencesHelper prefs = new PreferencesHelper(context);
        return !prefs.isAlertNotificationActive();
    }

    private boolean shouldNotificationVibrate() {
        PreferencesHelper prefs = new PreferencesHelper(context);
        return prefs.shouldNotificationVibrate();
    }

    private boolean shouldNotificationPlaySound() {
        PreferencesHelper prefs = new PreferencesHelper(context);
        return prefs.shouldNotificationPlaySound();
    }
}
