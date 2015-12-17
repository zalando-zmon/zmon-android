package de.zalando.zmon.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;

import de.zalando.zmon.R;
import de.zalando.zmon.ZmonDashboardActivity;
import de.zalando.zmon.client.domain.AlertDetails;

public class NotificationHelper {

    private final Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public void publishNewAlert(AlertDetails alert) {
        Intent startApplicationIntent = new Intent(context, ZmonDashboardActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ZmonDashboardActivity.class);
        stackBuilder.addNextIntent(startApplicationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(context)
                .setContentTitle(context.getResources().getString(R.string.app_name))
                .setContentText(String.format(context.getResources().getString(R.string.notification_new_alert), alert.getAlertDefinition().getName()))
                .setSmallIcon(R.drawable.zmon_logo)
                .setContentIntent(resultPendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
