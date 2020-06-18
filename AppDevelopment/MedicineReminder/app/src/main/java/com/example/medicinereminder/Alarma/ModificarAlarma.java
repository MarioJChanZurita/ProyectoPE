package com.example.medicinereminder.Alarma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.medicinereminder.DataBase.AdminSQLiteOpenHelper;
import com.example.medicinereminder.MainActivity;
import com.example.medicinereminder.Notificaciones.AlertReceiver;
import com.example.medicinereminder.Notificaciones.IniciarAlarma;
import com.example.medicinereminder.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ModificarAlarma extends AppCompatActivity{

    private EditText nombreMedicina, notasMedicina;
    private TextView horaMedicina, periodoMedicina, mostrarFechaFinal;
    private SwitchCompat switchAlarma;
    private int opcion, hora, minutos, periodoHoras, periodoMinutos, ano, mes, dia;
    private String primeraToma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_modificar_alarma);

        //Creamos los objetos de la pantalla
        nombreMedicina = (EditText) findViewById(R.id.nombreMedicina);
        horaMedicina = (TextView) findViewById(R.id.horaMedicina);
        periodoMedicina = (TextView) findViewById(R.id.periodoMedicina);
        notasMedicina = (EditText) findViewById(R.id.notasMedicina);

        mostrarFechaFinal = (EditText) findViewById(R.id.fechaFinalSeleccionada);
        switchAlarma = findViewById(R.id.switchActivarAlarma);

        //Bundle para pasar el nombre de la alarma seleccionada
        Bundle extras = getIntent().getExtras();
        String nombreAlarma = extras.getString("nombre");
        Cursor alarmaBuscada = obtenerAlarma(nombreAlarma);

        //Mostrar los datos de la alarma seleccionada en los campos
        cargarDatos(alarmaBuscada);
        //Correccion para la fecha del date picker a la fecha actual
        correccionFecha();

    }

    //Funcion para obtener la alarma seleccionada en el buscador
    public Cursor obtenerAlarma(String nombre) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        Cursor alarmaBuscada = admin.obtenerAlarmaBuscada(nombre);
        return alarmaBuscada;
    }


    //Funcion para cargar los datos respectivos de la alarma seleccionada en los campos
    public void cargarDatos(Cursor datosAlarma) {

        if (datosAlarma.moveToFirst()) {
            //Con esto conseguimos que los timePicker inicien con la hora puesta por el usuario
            opcion = datosAlarma.getInt(0);

            nombreMedicina.setText(datosAlarma.getString(2));
            periodoHoras = datosAlarma.getInt(3);
            periodoMinutos = datosAlarma.getInt(4);
            periodoMedicina.setText(String.format("%02d:%02d", periodoHoras, periodoMinutos));
            notasMedicina.setText(datosAlarma.getString(5));
            horaMedicina.setText(datosAlarma.getString(6));
            mostrarFechaFinal.setText(datosAlarma.getString(7));

            //traemos el valor de activar para saber mostrar si esta activada o no la alarma
            int activar = datosAlarma.getInt(1);
            if (activar == 1) {
                switchAlarma.setChecked(true);
            }
        }

    }

    //Funcion que modifica mediante una actualizacion en la base de datos los cambios hechos por el usuario
    public void modificar(View view) {
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        //Obtenemos los valores de los campos correspondientes
        String nombre = nombreMedicina.getText().toString();
        String nota = notasMedicina.getText().toString();
        String fechaFinal = mostrarFechaFinal.getText().toString();

        //verificamos que los campos estan llenos
        if (!nombre.isEmpty()) {

            ContentValues modificar = new ContentValues();
            modificar.put("nombre", nombre);
            modificar.put("periodoHoras", periodoHoras);
            modificar.put("periodoMinutos", periodoMinutos);
            modificar.put("notas", nota);
            modificar.put("hora", primeraToma);
            modificar.put("fechaFinal", fechaFinal);

            //Checamos el swith para ver si la alarma esta activada o desactivada y lo actualizamos en la base de datos
            if (switchAlarma.isChecked()) {
                //1 significa alarma activa
                modificar.put("activar", 1);
            } else {
                //0 significa alarma desactivada
                modificar.put("activar", 0);
            }

            int modificacion = admin.modificarDatos(modificar, opcion);

            //Iniciamos el funcionamiento de la alarma
            IniciarAlarma iniciarAlarma = new IniciarAlarma();
            iniciarAlarma.startAlarm(this);

            //Vemos si se modifico la base de datos
            if (modificacion == 1) {
                Toast.makeText(this, "Cambios guardados", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Ah ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Debes llenar los campos correctamente", Toast.LENGTH_SHORT).show();
        }
    }

    //Funcion que elimina de la base de datos la alarma seleccionada por el usuario
    public void eliminar(View view) {
        //Cosas para acceder a la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase(); //Abre la base de datos en modo escritura

        //Se elimina la alarma seleccionada de la base de datos
        if (baseDatos != null) {
            int eliminar = baseDatos.delete("datos", "posicion=" + opcion, null);
            //Se verifica si se elimino la alarma
            if (eliminar == 1) {
                Toast.makeText(this, "Medicina eliminada", Toast.LENGTH_SHORT).show();
                limpiado();
            } else {
                Toast.makeText(this, "Ah ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        }
        baseDatos.close();
    }

    //Funcion de limpiado de todos los campos
    public void limpiado() {
        nombreMedicina.setText("");
        horaMedicina.setText("");
        periodoMedicina.setText("");
        notasMedicina.setText("");
        mostrarFechaFinal.setText("");
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

                //Mostramos al usuario la hora seleccionada
                periodoMedicina.setText(String.format("%02d:%02d", periodoHoras, periodoMinutos));
            }
        }, periodoHoras, periodoMinutos, false);
        reloj.show();
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

                mostrarFechaFinal.setText(DateFormat.getDateInstance(DateFormat.FULL).format(fecha.getTime()));
            }
        }, ano, mes, dia);
        calendario.show();
    }
    //Funcion que obtiene la fecha actual
    public void correccionFecha(){
        Calendar c = Calendar.getInstance();
        dia = c.get(Calendar.DAY_OF_MONTH);
        mes = c.get(Calendar.MONTH);
        ano = c.get(Calendar.YEAR);
    }

    //Funcion para regresar al Main Activity
    public void cerrarActivity(View view) {
        finish();
    }

}


