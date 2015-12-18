package de.zalando.zmon.util;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import de.zalando.zmon.R;
import de.zalando.zmon.ZmonDashboardActivity;

public class NotificationHelper {

    private final Context context;

    public NotificationHelper(Context context) {
        this.context = context;
    }

    public void publishNewAlert(String title, String message) {
        Intent startApplicationIntent = new Intent(context, ZmonDashboardActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(ZmonDashboardActivity.class);
        stackBuilder.addNextIntent(startApplicationIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(context)
                .setContentTitle(title)
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_zmon_orange_24dp)
                .setContentIntent(resultPendingIntent)
                .setLights(context.getResources().getColor(R.color.colorPrimary), 3000, 3000)
                .build();

        notification.defaults |= Notification.DEFAULT_VIBRATE;
        notification.sound = Uri.parse("android.resource://" + context.getPackageName() + "/" + R.raw.zonk);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
