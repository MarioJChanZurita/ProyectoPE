package com.example.medicinereminder.Alarma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medicinereminder.DataBase.AdminSQLiteOpenHelper;
import com.example.medicinereminder.MainActivity;
import com.example.medicinereminder.Notificaciones.AlertReceiver;
import com.example.medicinereminder.Notificaciones.IniciarAlarma;
import com.example.medicinereminder.R;

import java.text.DateFormat;
import java.util.Calendar;

public class NuevaAlarma extends AppCompatActivity{

    private EditText nombreMedicina, notasMedicina;
    private TextView horaMedicina, periodoMedicina, mostrarFechaFinal;
    private String primeraToma, fechaFinal;
    private int hora, minutos, periodoHoras, periodoMinutos, ano, dia, mes;
    private boolean verificadorHora1 = false, verificadorHora2 = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_nueva_alarma);

        nombreMedicina = (EditText) findViewById(R.id.nombreMedicina);
        horaMedicina = (TextView) findViewById(R.id.horaMedicina);
        periodoMedicina = (TextView) findViewById(R.id.periodoMedicina);
        notasMedicina = (EditText) findViewById(R.id.notasMedicina);
        mostrarFechaFinal = (TextView) findViewById(R.id.fechaFinalSeleccionada);

        //Correccion para la fecha del date picker a la fecha actual
        correccionFecha();
    }

    //Funcion para agregar una medicina
    public void agregarDatos(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        //Obtenemos los valores de los campos correspondientes
        String nombre = nombreMedicina.getText().toString();
        String notas = notasMedicina.getText().toString();

        //Verificamos que los campos estan llenados
        if (!nombre.isEmpty() && verificadorHora1 && verificadorHora2) {
            //Convertimos los datos para coincidir con los de la base de datos
            int posicion = 1; //Inicializado en 1 por conveniencia en la BD

            //Obtenemos la ultima posicion de la base de datos
            Cursor fila = admin.obtenerPosicionesAlarmas();
            //Verificamos si existen mas datos en la BD
            if (fila != null) {
                //Nos posicionamos a la ultima posicion
                if (fila.moveToLast()) {
                    posicion = fila.getInt(0) + 1;
                }
            }

            //Objeto que almacena los datos anteriores
            ContentValues agregar = new ContentValues();
            agregar.put("posicion", posicion);
            agregar.put("activar", 1);
            agregar.put("nombre", nombre);
            agregar.put("periodoHoras", periodoHoras);
            agregar.put("periodoMinutos", periodoMinutos);
            agregar.put("notas", notas);
            agregar.put("hora", primeraToma);
            agregar.put("fechaFinal", fechaFinal);

            //Agregamos y cerramos la base de datos
            admin.agregarDatos(agregar);

            //Iniciamos el funcionamiento de la alarma
            IniciarAlarma iniciarAlarma = new IniciarAlarma();
            iniciarAlarma.startAlarm(this);

            //Limpiamos los campos y avisamos al usuario del registro exitoso
            limpiado();
            Toast.makeText(this, "Medicina almacenada", Toast.LENGTH_SHORT).show();
        } else {
            //Si los campos estan vacios se le avisa al usuario
            Toast.makeText(this, "Debes llenar todos los datos", Toast.LENGTH_SHORT).show();
        }
    }
    //Funcion que limpia los campos
    public void limpiado() {
        nombreMedicina.setText("");
        horaMedicina.setText("");
        periodoMedicina.setText("");
        notasMedicina.setText("");
        mostrarFechaFinal.setText("");
    }
    //Funcion que obtiene la fecha actual
    public void correccionFecha(){
        Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        ano = c.get(Calendar.YEAR);
    }
    //Funcion con date picker para obtener la fecha en la que se deberá auto desactivar la alarma
    public void obtenerFecha(View view){
        DatePickerDialog calendario = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar fecha = Calendar.getInstance();
                fecha.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                fecha.set(Calendar.MONTH, month);
                fecha.set(Calendar.YEAR, year);

                ano = year;
                mes = month;
                dia = dayOfMonth;

                fechaFinal = DateFormat.getDateInstance(DateFormat.FULL).format(fecha.getTime());
                mostrarFechaFinal.setText(fechaFinal);
            }
        }, ano, mes, dia);
        calendario.show();
    }
    //Funcion con time picker para obtener la primera toma de la medicina
    public void obtenerPrimeraToma(View view) {
        final TimePickerDialog reloj = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                //Usamos calendar por la facilidad de mostrar la hora
                Calendar horaCompleta = Calendar.getInstance();
                horaCompleta.set(Calendar.HOUR_OF_DAY, hourOfDay);
                horaCompleta.set(Calendar.MINUTE, minute);
                horaCompleta.set(Calendar.SECOND, 0);

                hora = hourOfDay;
                minutos = minute;
                primeraToma = DateFormat.getTimeInstance(DateFormat.SHORT).format(horaCompleta.getTime());
                verificadorHora1 = true;

                //Mostramos al usuario la hora seleccionada
                horaMedicina.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(horaCompleta.getTime()));
            }
        }, hora, minutos, false);
        reloj.show();
    }
    //Funcion con time picker para obtener cada cuanto tiempo deberia de activarse la alarma
    public void obtenerIntervalo(View view) {
        final TimePickerDialog reloj = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Usamos calendar por la facilidad de mostrar la hora
                Calendar horaCompleta = Calendar.getInstance();
                horaCompleta.set(Calendar.HOUR_OF_DAY, hourOfDay);
                horaCompleta.set(Calendar.MINUTE, minute);
                horaCompleta.set(Calendar.SECOND, 0);

                periodoHoras = hourOfDay;
                periodoMinutos = minute;
                verificadorHora2 = true;

                //Mostramos al usuario la hora seleccionada
                periodoMedicina.setText(String.format("%02d:%02d", periodoHoras, periodoMinutos));
            }
        }, periodoHoras, periodoMinutos, false);
        reloj.show();
    }

    //Funcion para regresar al Main Activity
    public void cerrarActivity(View view) {
        finish();
    }

}

