package com.example.medicinereminder.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.medicinereminder.HelperClasses.AlarmReceiver;
import com.example.medicinereminder.R;

import java.util.Calendar;

import com.example.medicinereminder.HelperClasses.NotificationHelper;

public class AddReminder extends AppCompatActivity {

    //Variables
    EditText medicineName;
    EditText periodTime;
    EditText duration;
    EditText notes;
    int passDays = 0;

    private NotificationHelper mNotificationHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reminder);

        periodTime = (EditText) findViewById(R.id.medicine_period);
        medicineName = (EditText) findViewById(R.id.medicine_name);
        duration = (EditText) findViewById(R.id.medicine_duration);
        notes = (EditText) findViewById(R.id.medicine_notes);

        mNotificationHelper = new NotificationHelper(this);

        startAlarm(passDays, this);

        Bundle alarm = new Bundle();
        alarm.putString("Name", medicineName.getText().toString());
        alarm.putString("Notes", notes.getText().toString());
        Intent receiver = new Intent(AddReminder.this, AlarmReceiver.class);
        receiver.putExtras(alarm);
        startActivity(receiver);

    }

    public void startAlarm(int passDays, Context context) {

        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //Parse
        String periodTime_String = periodTime.getText().toString();
        int periodTime_Int = Integer.parseInt(periodTime_String);

        String duration_String = duration.getText().toString();
        int duration_Int = Integer.parseInt(duration_String);


        while (passDays != duration_Int) {

            // Get current time
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.get(Calendar.HOUR_OF_DAY);
            calendar.get(Calendar.MINUTE);

            // setRepeating() lets you specify a precise custom interval by the user
            alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                    1000 * 60 * 60 * periodTime_Int, alarmIntent);

            passDays += 1;
        }

        cancelAlarm();

    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Alarm canceled", Toast.LENGTH_SHORT).show();
    }

}
