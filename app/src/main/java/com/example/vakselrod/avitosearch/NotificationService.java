package com.example.vakselrod.avitosearch;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class NotificationService extends Service
{
    private static final int NOTIFY_ID = 101;

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        Toast.makeText(this, "Служба создана",
                Toast.LENGTH_SHORT).show();
        // Логика здесь
    }

    @Override
    public void onDestroy()
    {
        Toast.makeText(this, "Служба остановлена",
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        PushNotification.show(getApplicationContext(), "This started in background", "Служба запущена в фоне");
        return Service.START_STICKY;
    }
}