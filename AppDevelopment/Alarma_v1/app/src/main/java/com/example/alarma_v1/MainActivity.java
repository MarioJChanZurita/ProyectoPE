package com.example.alarma_v1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.service.autofill.TextValueSanitizer;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.alarma_v1.AdminSQLiteOpenHelper;
import com.example.alarma_v1.Alarma.AlertReceiver;
import com.example.alarma_v1.Alarma.DatePickerFragment;
import com.example.alarma_v1.Alarma.NotificationHelper;
import com.example.alarma_v1.Alarma.TimePickerFragment;
import com.example.alarma_v1.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    EditText nombreMedicina, horasMedicina, minutosMedicina, duracionMedicina, notasMedicina;
    TextView mostrarFecha, mostrarHora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreMedicina = (EditText)findViewById(R.id.nombreMedicina);
        horasMedicina = (EditText)findViewById(R.id.horasMedicina);
        minutosMedicina = (EditText)findViewById(R.id.minutosMedicina);
        duracionMedicina = (EditText)findViewById(R.id.duracionMedicina);
        notasMedicina = (EditText)findViewById(R.id.notasMedicina);

        mostrarFecha = (EditText)findViewById(R.id.fechaSeleccionada);
        mostrarHora = (EditText)findViewById(R.id.horaSeleccionada);

        //boton para abrir el date picker y seleccionar la fecha
        Button btDate = (Button)findViewById(R.id.seleccionarFecha);
        btDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(),"date picker");
            }
        });

        //boton para abrir el time picker y seleccionar la hora
        Button btTime = (Button)findViewById(R.id.seleccionarHora);
        btTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(),"time picker");
            }
        });

    }

    //Funcion para agregar una medicina
    public void agregarDatos(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase(); //Abre la base de datos en modo escritura

        //Obtenemos los valores de los campos correspondientes
        String nombre = nombreMedicina.getText().toString();
        String hrs = horasMedicina.getText().toString();
        String min = minutosMedicina.getText().toString();
        String dura = duracionMedicina.getText().toString();
        String notas = notasMedicina.getText().toString();

        String fecha = mostrarFecha.getText().toString();
        String hora = mostrarHora.getText().toString();

        //Verificamos que todos los campos estan llenados
        if(!nombre.isEmpty() && !hrs.isEmpty() && !min.isEmpty() && !dura.isEmpty()){
            //Convertimos los datos para coincidir con los de la base de datos
            int posicion = 1; //Inicializado en 1 por conveniencia en la BD
            int horas = Integer.parseInt(hrs);
            int minutos = Integer.parseInt(min);
            int duracion = Integer.parseInt(dura);

            //Llamar a la funcion de repetidor de alarma
            startAlarm(horas, minutos);

            //Obtenemos la ultima posicion de la base de datos
            Cursor fila = baseDatos.rawQuery("SELECT posicion FROM datos", null);
            //Verificamos si existen mas datos en la BD
            if(fila != null){
                //Nos posicionamos a la ultima posicion
                if(fila.moveToLast()){
                    posicion = fila.getInt(0)+1;
                }
            }

            //Objeto que almacena los datos anteriores
            ContentValues agregar = new ContentValues();
            agregar.put("posicion",posicion);
            agregar.put("nombre", nombre);
            agregar.put("periodoHoras", horas);
            agregar.put("periodoMinutos", minutos);
            agregar.put("duracion", duracion);
            agregar.put("notas", notas);

            agregar.put("fecha", fecha);
            agregar.put("hora", hora);

            //Agregamos y cerramos la base de datos
            baseDatos.insert("datos", null, agregar);
            baseDatos.close();

            //Limpiamos los campos y avisamos al usuario del registro exitoso
            limpiado();

            Toast.makeText(this, "Medicina almacenada", Toast.LENGTH_SHORT).show();
        }else{
            //Si los campos estan vacios se le avisa al usuario
            Toast.makeText(this, "Debes llenar todos los datos", Toast.LENGTH_SHORT).show();
        }
    }

    //Funcion que limpia los campos
    public void limpiado() {
        nombreMedicina.setText("");
        horasMedicina.setText("");
        minutosMedicina.setText("");
        duracionMedicina.setText("");
        notasMedicina.setText("");
        mostrarFecha.setText("");
        mostrarHora.setText("");
    }

    //Funcion para modificar una alarma
    public void modificar(View view){
        Intent modificar = new Intent(this, AlarmaDatos.class);
        startActivity(modificar);
    }

    //Funcion de TimePicker
    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);

        updateTimeText(c);
    }

    private void updateTimeText(Calendar c) {
        String timeText = DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        mostrarHora.setText(timeText);
    }

    //Funcion de datePicker
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);

        updateDateText(c);
    }
    private void updateDateText(Calendar c) {
        String dateText = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        mostrarFecha.setText(dateText);
    }

    //Funcion de repetidor de alarma
    private void startAlarm(int horas, int minutos){
        AlarmManager alarmManager = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        //Tengo desactivada esta parte porque no encuentro la forma de pasar la variable "c", que esta en la funcion onTimeSet para meterla a esta funcion
                /*
                if(c.before(Calendar.getInstance())) {
                    c.add(Calendar.DATE, 1);
                }
                */
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000*60*minutos + 1000*60*60*horas, pendingIntent);
    }
}
