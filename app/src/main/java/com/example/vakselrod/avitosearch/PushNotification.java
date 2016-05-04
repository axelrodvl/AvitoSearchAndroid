package com.example.vakselrod.avitosearch;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class PushNotification {
    private static final int NOTIFY_ID = 101;

    /**
     * Showing PUSH notification
     *
     * Calling example:
     * PushNotification.show(getApplicationContext(), "test1", "text2");
     *
     * @param context
     * @param notificationTitle
     * @param notificationText
     */
    public static void show(Context context, String notificationTitle, String notificationText) {
        Intent notificationIntent = new Intent(context, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(context,
                0, notificationIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        Resources res = context.getResources();
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentIntent(contentIntent)
                .setSmallIcon(R.drawable.ic_launcher)
                        // большая картинка
                .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher))
                        //.setTicker(res.getString(R.string.warning)) // текст в строке состояния
                .setTicker(notificationTitle)
                .setWhen(System.currentTimeMillis())
                .setAutoCancel(true)
                        //.setContentTitle(res.getString(R.string.notifytitle)) // Заголовок уведомления
                .setContentTitle(notificationTitle)
                        //.setContentText(res.getString(R.string.notifytext))
                .setContentText(notificationText); // Текст уведомленимя

        // Notification notification = builder.getNotification(); // до API 16
        Notification notification = builder.build();

        NotificationManager notificationManager = (NotificationManager) context
                .getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFY_ID, notification);
    }
}
