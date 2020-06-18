package com.example.medicinereminder.Notificaciones;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.example.medicinereminder.R;

public class NotificationHelper extends ContextWrapper {

    public static final String Notification_ID = "NotificationID";
    public static final String Notification_Name = "Notification";

    private NotificationManager mManager;

    //Funcion para compatibilizar las notificaciones con todas las versiones
    public NotificationHelper(Context base) {
        super(base);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createNotification();
        }

    }

    //Funcion para darle sus atributos a la notificacion
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

    //Funcion para crear el diseño de la notificacion
    public NotificationCompat.Builder getNotification(String nombreMedicina, String notas){
        return new NotificationCompat.Builder(getApplicationContext(), Notification_ID)
                .setContentTitle(nombreMedicina)
                .setContentText(notas)
                .setSmallIcon(R.drawable.icon_medicines);
    }

}
