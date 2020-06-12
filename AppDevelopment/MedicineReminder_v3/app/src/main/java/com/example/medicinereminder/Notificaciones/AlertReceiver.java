package com.example.medicinereminder.Notificaciones;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.core.app.NotificationCompat;

import com.example.medicinereminder.DataBase.AdminSQLiteOpenHelper;

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
            Cursor fila = baseDatos.rawQuery("SELECT * FROM datos WHERE activar='"+1+"' AND fecha ='"+fecha_sistema+"' AND hora = '"+hora_sistema+"'",null);
            if(fila.moveToFirst()){

                //Obtenemos los valores de nombre de alarma y notas para mostrar en la notificacion
                String nombre = fila.getString(2);
                String mensaje = fila.getString(5);

                //Llamamos a la clase de la notificacion para crearla y mandarla
                NotificationHelper notificationHelper = new NotificationHelper(context);
                NotificationCompat.Builder nb = notificationHelper.getNotification(nombre, mensaje);
                notificationHelper.getManager().notify((int)System.currentTimeMillis(),nb.build());


                //Llamamos a los valores de cada cuanto tiempo debe activarse la alarma, al valor de la posicion y la fecha en la que debe de desactivarse la alarma
                int opcion = fila.getInt(0);
                int pHoras = fila.getInt(3);
                int pMinutos = fila.getInt(4);
                String fechaFinal = fila.getString(8);

                //A la hora en la que se activo la alarma por ultima vez le agregamos el periodo de horas y el periodo de minutos
                calendarioHora.add(Calendar.HOUR_OF_DAY, pHoras);
                calendarioHora.add(Calendar.MINUTE, pMinutos);
                String horaNueva = DateFormat.getTimeInstance(DateFormat.SHORT).format(calendarioHora.getTime());

                //Se agrega un dia a fecha en caso de ser necesario y se actualiza en la base de datos
                //La duracion al alcanzar el valor de 0 se elimina la alarma
                if(calendarioHora.before(Calendar.getInstance())) {
                    calendarioFecha.add(Calendar.DAY_OF_YEAR, 1);
                    String fechaNueva = DateFormat.getDateInstance(DateFormat.FULL).format(calendarioFecha.getTime());

                    //Almacenamos la nueva hora en la base de datos a traves de una actualizacion
                    ContentValues actualizar = new ContentValues();
                    actualizar.put("fecha", fechaNueva);
                    baseDatos.update("datos", actualizar,"posicion="+opcion, null);

                    //Si la fecha final es igual a la fecha actual entonces se desactiva la alarma
                    if(fechaNueva == fechaFinal){
                        ContentValues actualizarActivado = new ContentValues();
                        actualizarActivado.put("activar", 0); //0 significa que la alarma esta desactivada
                        baseDatos.update("datos", actualizarActivado,"posicion="+opcion, null);
                    }

                }

                //Almacenamos la nueva hora en la base de datos a traves de una actualizacion
                ContentValues actualizarHora = new ContentValues();
                actualizarHora.put("hora", horaNueva);
                baseDatos.update("datos", actualizarHora,"posicion="+opcion, null);

            }

        }
        baseDatos.close();
    }
}
