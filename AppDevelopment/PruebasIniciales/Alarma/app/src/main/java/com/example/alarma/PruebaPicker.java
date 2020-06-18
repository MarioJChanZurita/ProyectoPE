package com.example.alarma;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.TimePicker;
import android.content.pm.ActivityInfo;
import android.view.WindowManager;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

public class PruebaPicker extends AppCompatActivity {

    private int hora, minutos;
    private TextView TvHora;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_picker);
        /**Preferencias de la app
         * el primero bloquea al app de forma vertical
         * el segundo elimina la barra superior
         * */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Objetos que se modifican de forma logica
        TvHora = (TextView)findViewById(R.id.TvHora);
    }

    public void obtenerHora(View view){
        final TimePickerDialog reloj = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Usamos calendar por la facilidad de mostrar la hora
                Calendar horaCompleta = Calendar.getInstance();
                horaCompleta.set(Calendar.HOUR_OF_DAY, hourOfDay);
                horaCompleta.set(Calendar.MINUTE, minute);
                horaCompleta.set(Calendar.SECOND, 0);

                //Mostramos al usuario la hora seleccionada
                TvHora.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(horaCompleta.getTime()));
            }
        },hora, minutos,false);
        reloj.show();
    }
}
