package com.salah.amr.workplace.Tasks;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.salah.amr.workplace.Model.Task;
import com.salah.amr.workplace.R;

public class NotificationReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 10001;
    private static final String TAG = "NotificationReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: Notification receiver");
        Notification.Builder builder = new Notification.Builder(context);
        builder.setContentTitle("Task Notification");
        builder.setContentText("The task should be done by now");
        builder.setSmallIcon(R.drawable.ic_notification);
        Intent notifyIntent = new Intent(context, TasksActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, -5, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Notification notificationCompat = builder.build();
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
        managerCompat.notify(NOTIFICATION_ID, notificationCompat);
    }
}
