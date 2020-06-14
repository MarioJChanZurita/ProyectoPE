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
import com.example.medicinereminder.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ModificarAlarma extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText nombreMedicina, notasMedicina;
    private Spinner nombreAlarmas;
    private TextView horaMedicina, periodoMedicina, mostrarFecha, mostrarFechaFinal;
    private SwitchCompat switchAlarma;
    private int hora, minutos, periodoHoras, periodoMinutos;
    private String primeraToma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_modificar_alarma);

        //Creamos los objetos de la pantalla
        nombreAlarmas = (Spinner)findViewById(R.id.nombreAlarmas);
        nombreMedicina = (EditText)findViewById(R.id.nombreMedicina);
        horaMedicina = (TextView)findViewById(R.id.horaMedicina);
        periodoMedicina = (TextView)findViewById(R.id.periodoMedicina);
        notasMedicina = (EditText)findViewById(R.id.notasMedicina);

        mostrarFecha = (EditText) findViewById(R.id.fechaSeleccionada);
        mostrarFechaFinal = (EditText) findViewById(R.id.fechaFinalSeleccionada);
        switchAlarma = findViewById(R.id.switchActivarAlarma);

        //boton para abrir el date picker y seleccionar la fecha
        Button btDate = (Button) findViewById(R.id.seleccionarFecha);
        btDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });


        //boton para abrir el date picker y seleccionar la fecha en la que se desactivará la alarma
        Button btFinalDate = (Button) findViewById(R.id.seleccionarFechaFinal);
        btFinalDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment finalTimePicker = new TimePickerFragment();
                finalTimePicker.show(getSupportFragmentManager(), "final time picker");
            }
        });

        //Creamos el spinner donde se muestra la lista de las alarmas creadas
        spinnerAlarmas(generarLista());
    }

    //Funcion que genera la lista de alarmas
    public ArrayList<String> generarLista(){
        //Cosas para acceder a la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        //Lista dinamica de nombres de alarmas
        final ArrayList<String> nombresAlarmas = new ArrayList<String>();

        //Sistema para agregar los nombres de la base de datos
        Cursor lista = admin.obtenerNombresAlarmas();
        if((lista != null) && lista.moveToFirst()){
            nombresAlarmas.add("Lista de alarmas"); //Asi la primera opcion sirve de accion
            //Ciclo para agregar todos los elementos de la lista
            do{
                nombresAlarmas.add(lista.getString(0));
            }while (lista.moveToNext());
        }
        //C regresamos el arreglo
        return  nombresAlarmas;
    };

    //Funcion que crea el spinner con la lista de alarmas
    public void spinnerAlarmas(ArrayList<String> Alarmas){
        //Generar el spiner
        ArrayAdapter<String> spinnerNombres = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, Alarmas);
        nombreAlarmas.setAdapter(spinnerNombres);

        //Mostramos los datos que selecciono
        nombreAlarmas.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int seleccion, long id) {
                cargarDatos(seleccion);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                //Sin seleccion
            }
        });
    }

    //Funcion para cargar los datos respectivos de la alarma seleccionada en los campos
    public void cargarDatos(int seleccion){
        //Cosas para acceder a la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        //La BD empieza desde 1
        if(seleccion !=0){
            Cursor datosAlarma = admin.obtenerAlarmaSeleccionada(seleccion);
            if(datosAlarma.moveToFirst()){
                //Con esto conseguimos que los timePicker inicien con la hora puesta por el usuario
                periodoHoras = datosAlarma.getInt(3);
                periodoMinutos = datosAlarma.getInt(4);

                nombreMedicina.setText(datosAlarma.getString(2));
                periodoMedicina.setText(String.format("%02d:%02d", periodoHoras, periodoMinutos));
                notasMedicina.setText(datosAlarma.getString(5));
                mostrarFecha.setText(datosAlarma.getString(6));
                horaMedicina.setText(datosAlarma.getString(7));
                mostrarFechaFinal.setText(datosAlarma.getString(8));

                //traemos el valor de activar para saber mostrar si esta activada o no la alarma
                int activar = datosAlarma.getInt(1);
                if(activar == 1) {
                    switchAlarma.setChecked(true);
                }
            }
        }
    }

    //Funcion que modifica mediante una actualizacion en la base de datos los cambios hechos por el usuario
    public void modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        final SQLiteDatabase baseDatos = admin.getWritableDatabase(); //Abre la base de datos en modo escritura

        int opcion = nombreAlarmas.getSelectedItemPosition();
        //Obtenemos los valores de los campos correspondientes
        String nombre = nombreMedicina.getText().toString();
        String nota = notasMedicina.getText().toString();
        String fecha = mostrarFecha.getText().toString();
        String fechaFinal = mostrarFechaFinal.getText().toString();

        //Checamos el swith para ver si la alarma esta activada o desactivada y lo actualizamos en la base de datos
        if(switchAlarma.isChecked()){
            int activar = 1;
            ContentValues modificarActivar = new ContentValues();
            modificarActivar.put("activar", activar);
            baseDatos.update("datos", modificarActivar,"posicion="+opcion, null);
        }else{
            int activar = 0;
            ContentValues modificarActivar = new ContentValues();
            modificarActivar.put("activar", activar);
            baseDatos.update("datos", modificarActivar,"posicion="+opcion, null);
        }

        //verificamos que los campos estan llenos
        if(!nombre.isEmpty()){

            ContentValues modificar = new ContentValues();
            modificar.put("nombre", nombre);
            modificar.put("periodoHoras", periodoHoras);
            modificar.put("periodoMinutos", periodoMinutos);
            modificar.put("notas", nota);
            modificar.put("fecha", fecha);
            modificar.put("hora", primeraToma);
            modificar.put("fechaFinal", fechaFinal);
            int modificacion = admin.modificarDatos(modificar, opcion);

            //Iniciamos el funcionamiento de la alarma
            startAlarm();

            //Vemos si se modifico la base de datos
            if(modificacion == 1){
                Toast.makeText(this,"Cambios guardados", Toast.LENGTH_SHORT).show();
                //Actualizamos el spinner
                spinnerAlarmas(generarLista());
            }else{
                Toast.makeText(this,"Ah ocurrido un error", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this,"Debes llenar los campos correctamente", Toast.LENGTH_SHORT).show();
        }
    }

    //Funcion del date picker
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();

        //Obtenemos el dia, mes, año seleccionado por el usuario al abrir el date picker
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);

        //Revisamos en cual Text View se debe de mostrar la fecha seleccionada
        if(!mostrarFecha.getText().toString().isEmpty()){
            updateDateText(c);
        }else{
            updateFinalDateText(c);
        }
    }

    public void obtenerPrimeraToma(View view){
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
        },hora, minutos,false);
        reloj.show();
    }
    public void obtenerIntervalo(View view){
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
        },periodoHoras, periodoMinutos,false);
        reloj.show();
    }


    //Funcion para colocar la fecha seleccionada en la fecha de inicio de la alarma
    private void updateDateText(Calendar c) {
        String dateText = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        mostrarFecha.setText(dateText);
    }
    //Funcion para colocar la fecha seleccionada en la fecha final de la alarma
    private void updateFinalDateText(Calendar c){
        String dateText = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        mostrarFechaFinal.setText(dateText);
    }

    //Funcion que permite ingresar al alert receiver cada 10 segundos para verificar si se debe de activar alguna alarma
    private void startAlarm() {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),10*1000, pendingIntent);

    }

    //Funcion para regresar al Main Activity
    public void regresar(View view){
        Intent regresar = new Intent(this, MainActivity.class);
        startActivity(regresar);
    }

}


