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

public class ModificarAlarma extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener{

    private EditText nombreMedicina, horasMedicina, minutosMedicina, notasMedicina;
    private Spinner nombreAlarmas;
    TextView mostrarFecha, mostrarHora, mostrarFechaFinal;
    SwitchCompat switchAlarma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_modificar_alarma);

        //Creamos los objetos de la pantalla
        nombreAlarmas = (Spinner)findViewById(R.id.nombreAlarmas);
        nombreMedicina = (EditText)findViewById(R.id.nombreMedicina);
        horasMedicina = (EditText)findViewById(R.id.horasMedicina);
        minutosMedicina = (EditText)findViewById(R.id.minutosMedicina);
        notasMedicina = (EditText)findViewById(R.id.notasMedicina);

        mostrarFecha = (EditText) findViewById(R.id.fechaSeleccionada);
        mostrarHora = (EditText) findViewById(R.id.horaSeleccionada);
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

        //boton para abrir el time picker y seleccionar la hora
        Button btTime = (Button) findViewById(R.id.seleccionarHora);
        btTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment timePicker = new TimePickerFragment();
                timePicker.show(getSupportFragmentManager(), "time picker");
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

        spinnerAlarmas(generarLista());
    }

    //Funcion que genera la lista de
    public ArrayList<String> generarLista(){
        //Cosas para acceder a la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase(); //Abre la base de datos en modo escritura

        //Lista dinamica de nombres de alarmas
        final ArrayList<String> nombresAlarmas = new ArrayList<String>();

        //Sistema para agregar los demas nombres
        Cursor lista = baseDatos.rawQuery("SELECT nombre FROM datos", null);
        if((lista != null) && lista.moveToFirst()){
            nombresAlarmas.add(""); //Asi la primera opcion está vacia
            //Ciclo para agregar casi todos los elementos de la lista
            while(!lista.isLast()){
                //Agregamos al arreglo la variable y nos movemos a la siguiente posicion
                nombresAlarmas.add(lista.getString(0));
                lista.moveToNext();
            }
            //Para agregar el ultimo elemento
            nombresAlarmas.add(lista.getString(0));
        }
        //Cerramos la base de datos y regresamos el arreglo
        baseDatos.close();
        return  nombresAlarmas;
    };

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
    public void cargarDatos(int seleccion){
        //Cosas para acceder a la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase(); //Abre la base de datos en modo escritura
        if(seleccion !=0){
            Cursor datosAlarma = baseDatos.rawQuery("Select * From datos Where posicion="+seleccion,null);
            if(datosAlarma.moveToFirst()){
                nombreMedicina.setText(datosAlarma.getString(2));
                horasMedicina.setText(datosAlarma.getString(3));
                minutosMedicina.setText(datosAlarma.getString(4));
                notasMedicina.setText(datosAlarma.getString(5));

                mostrarFecha.setText(datosAlarma.getString(6));
                mostrarHora.setText(datosAlarma.getString(7));

                mostrarFechaFinal.setText(datosAlarma.getString(8));
                //traemos el valor de activar para saber mostrar si esta activada o no la alarma
                int activar = datosAlarma.getInt(1);
                if(activar == 1) {
                    switchAlarma.setChecked(true);
                }
            }
        }
    }

    public void modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        final SQLiteDatabase baseDatos = admin.getWritableDatabase(); //Abre la base de datos en modo escritura

        int opcion = nombreAlarmas.getSelectedItemPosition();
        //Obtenemos los valores de los campos correspondientes
        String nombre = nombreMedicina.getText().toString();
        String hrs = horasMedicina.getText().toString();
        String min = minutosMedicina.getText().toString();
        String nota = notasMedicina.getText().toString();

        String fecha = mostrarFecha.getText().toString();
        String hora = mostrarHora.getText().toString();

        String fechaFinal = mostrarFechaFinal.getText().toString();

        //verificamos que los campos estan llenos
        if(!nombre.isEmpty() && !hrs.isEmpty() && !min.isEmpty()){
            //Convertimos los datos para coincidir con los de la base de datos
            int horas = Integer.parseInt(hrs);
            int minutos = Integer.parseInt(min);

            ContentValues modificar = new ContentValues();
            modificar.put("nombre", nombre);
            modificar.put("periodoHoras", horas);
            modificar.put("periodoMinutos", minutos);
            modificar.put("notas", nota);

            modificar.put("fecha", fecha);
            modificar.put("hora", hora);
            modificar.put("fechaFinal", fechaFinal);

            int modificacion = baseDatos.update("datos", modificar,"posicion="+opcion, null);

            startAlarm();

            //Vemos si se modifico la base de datos
            if(modificacion == 1){
                Toast.makeText(this,"Cambios guardados", Toast.LENGTH_SHORT).show();
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

        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.YEAR, year);

        if(!mostrarFecha.getText().toString().isEmpty()){
            updateDateText(c);
        }else{
            updateFinalDateText(c);
        }
    }
    private void updateDateText(Calendar c) {
        String dateText = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        mostrarFecha.setText(dateText);
    }
    private void updateFinalDateText(Calendar c){
        String dateText = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        mostrarFechaFinal.setText(dateText);
    }

    //Funcion del time picker
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

    //Funcion que permite ingresar al alert receiver cada 10 segundos para verificar si se debe de activar alguna alarma
    private void startAlarm() {
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(),10*1000, pendingIntent);

    }

    //Funcion para activar o desactivar la alarma
    public void activacion(View view) {

        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase(); //Abre la base de datos en modo escritura

        int opcion = nombreAlarmas.getSelectedItemPosition();
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

        baseDatos.close();
    }

    public void regresar(View view){
        Intent regresar = new Intent(this, MainActivity.class);
        startActivity(regresar);
    }

}


