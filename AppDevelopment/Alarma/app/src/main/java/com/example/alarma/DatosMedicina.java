package com.example.alarma;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DatosMedicina extends AppCompatActivity {

    private EditText nombreMedicina, duracionMedicina, notasMedicina;
    private TextView horaMedicina, periodoMedicina;
    private Spinner nombreAlarmas;

    private int hora, minutos, periodoHoras, periodoMinutos;
    boolean verificadorHora1, verificadorHora2;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datos_medicina);
        /**Preferencias de la app
         * el primero bloquea al app de forma vertical
         * el segundo elimina la barra superior
         * */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        //Creamos los objetos de la pantalla
        nombreAlarmas = (Spinner)findViewById(R.id.nombreAlarmas);
        nombreMedicina = (EditText)findViewById(R.id.nombreMedicina);
        horaMedicina = (TextView)findViewById(R.id.horaMedicina);
        periodoMedicina = (TextView)findViewById(R.id.periodoMedicina);
        duracionMedicina = (EditText)findViewById(R.id.duracionMedicina);
        notasMedicina = (EditText)findViewById(R.id.notasMedicina);

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
                //Para que cuando seleccione, el picker tenga la misma hora
                hora =datosAlarma.getInt(2);
                minutos= datosAlarma.getInt(3);
                periodoHoras = datosAlarma.getInt(4);
                periodoMinutos = datosAlarma.getInt(5);

                Calendar horaCompleta = Calendar.getInstance();

                nombreMedicina.setText(datosAlarma.getString(1));

                //Facilitador de mostrar hora
                horaCompleta.set(Calendar.HOUR_OF_DAY, hora);
                horaCompleta.set(Calendar.MINUTE, minutos);
                horaCompleta.set(Calendar.SECOND, 0);

                horaMedicina.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(horaCompleta.getTime()));

                //Facilitador de mostrar hora
                horaCompleta.set(Calendar.HOUR_OF_DAY, periodoHoras);
                horaCompleta.set(Calendar.MINUTE, periodoMinutos);
                horaCompleta.set(Calendar.SECOND, 0);
                periodoMedicina.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(horaCompleta.getTime()));

                duracionMedicina.setText(datosAlarma.getString(6));
                notasMedicina.setText(datosAlarma.getString(7));
            }
        }
    }

    public Calendar horaArmada(int hora, int minutos){
        Calendar horaCompleta = Calendar.getInstance();
        horaCompleta.set(Calendar.HOUR_OF_DAY, hora);
        horaCompleta.set(Calendar.MINUTE, minutos);
        horaCompleta.set(Calendar.SECOND, 0);

        return horaCompleta;
    }

    public void modificar(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        final SQLiteDatabase baseDatos = admin.getWritableDatabase(); //Abre la base de datos en modo escritura

         int opcion = nombreAlarmas.getSelectedItemPosition();
                //Obtenemos los valores de los campos correspondientes
                String nombre = nombreMedicina.getText().toString();
                            String dura = duracionMedicina.getText().toString();
                String nota = notasMedicina.getText().toString();

                //verificamos que los campos estan llenos
                if(!nombre.isEmpty() && verificadorHora1 && verificadorHora2 && !dura.isEmpty()){
                    //Convertimos los datos para coincidir con los de la base de datos
                    int duracion = Integer.parseInt(dura);

                    ContentValues modificar = new ContentValues();
                    modificar.put("nombre", nombre);
                    modificar.put("horas",hora);
                    modificar.put("minutos", minutos);
                    modificar.put("periodoHoras", periodoHoras);
                    modificar.put("periodoMinutos", periodoMinutos);
                    modificar.put("duracion", duracion);
                    modificar.put("notas", nota);

                    int modificacion = baseDatos.update("datos", modificar,"posicion="+opcion, null);
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

    public void obtenerToma(View view){
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
                verificadorHora1= true;

                //Mostramos al usuario la hora seleccionada
                horaMedicina.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(horaCompleta.getTime()));
            }
        },hora, minutos,false);
        reloj.show();
    }

    public void obtenerPeriodo(View view){
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
                verificadorHora2= true;

                //Mostramos al usuario la hora seleccionada
                periodoMedicina.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(horaCompleta.getTime()));
            }
        },periodoHoras, periodoMinutos,false);
        reloj.show();
    }

}
