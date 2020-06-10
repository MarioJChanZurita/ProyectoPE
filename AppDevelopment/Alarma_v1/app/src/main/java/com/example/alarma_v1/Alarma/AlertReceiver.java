package com.example.alarma_v1.Alarma;

import android.app.DatePickerDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.app.NotificationCompat;

import com.example.alarma_v1.AdminSQLiteOpenHelper;

import java.text.DateFormat;
import java.util.Calendar;

public class AlertReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Calendar calendarioHora = Calendar.getInstance();
        Calendar calendarioFecha = Calendar.getInstance();

        //Obtenemos la fecha del sistema
        calendarioFecha.get(Calendar.DAY_OF_YEAR);
        String fecha_sistema = DateFormat.getDateInstance(DateFormat.FULL).format(calendarioFecha.getTime());

        //Obtenemos la hora del sistema
        calendarioHora.get(Calendar.HOUR_OF_DAY);
        calendarioHora.get(Calendar.MINUTE);
        String hora_sistema = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendarioHora.getTime());

        //Llamamos a la base de datos para sacar los valores de fecha y hora introducidos por el usuario
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(context, "administracion",null,1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();

        //Hacemos una comparación entre las fechas y horas para activar la notificacion
        if(baseDatos!=null){
            Cursor fila = baseDatos.rawQuery("SELECT * FROM datos WHERE fecha ='"+fecha_sistema+"' AND hora = '"+hora_sistema+"'",null);
            if(fila.moveToFirst()){

                //Obtenemos los valores de nombre de alarma y notas para mostrar en la notificacion
                String nombre = fila.getString(1);
                String mensaje = fila.getString(5);

                //Llamamos a la clase de la notificacion para crearla y mandarla
                NotificationHelper notificationHelper = new NotificationHelper(context);
                NotificationCompat.Builder nb = notificationHelper.getNotification(nombre, mensaje);
                notificationHelper.getManager().notify((int)System.currentTimeMillis(),nb.build());
            }



        }
        baseDatos.close();
    }
}
