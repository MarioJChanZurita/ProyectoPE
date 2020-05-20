package com.example.timepicker;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

public class NotificationHelper extends ContextWrapper {

    public static final String Notification_ID = "NotificationID";
    public static final String Notification_Name = "Notification";

    private NotificationManager mManager;

    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotification();
        }

    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createNotification() {
        NotificationChannel Notificacion = new NotificationChannel(Notification_ID, Notification_Name, NotificationManager.IMPORTANCE_DEFAULT);
        Notificacion.enableLights(true);
        Notificacion.enableVibration(true);
        Notificacion.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(Notificacion);
    }

    public NotificationManager getManager(){
        if(mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getNotification(){
        return new NotificationCompat.Builder(getApplicationContext(), Notification_ID)
                .setContentTitle("Alarma")
                .setContentText("Message")
                .setSmallIcon(R.drawable.icon_medicines);
    }

}
