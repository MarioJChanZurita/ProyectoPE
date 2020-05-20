package com.example.medicinereminder.HelperClasses;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Intent receiver = ((Activity)context).getIntent();
        Bundle alarm = receiver.getExtras();
        String Name = alarm.getString("Name");
        String Notes = alarm.getString("Notes");

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getNotification(Name, Notes);
        notificationHelper.getManager().notify(1,nb.build());
    }
}
