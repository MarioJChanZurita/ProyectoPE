package com.example.alarma;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Calendar;

public class AgregarMedicina extends AppCompatActivity {

    EditText nombreMedicina, duracionMedicina, notasMedicina;
    TextView primeraToma, periodoToma;
    private int horaBase, minutosBase, hora, minutos, periodoHoras, periodoMinutos;
    boolean verificadorHora1, verificadorHora2;


    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar_medicina);
        /**Preferencias de la app
         * el primero bloquea al app de forma vertical
         * el segundo elimina la barra superior
         * */
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        nombreMedicina = (EditText)findViewById(R.id.nombreMedicina);
        primeraToma = (TextView)findViewById(R.id.primeraToma);
        periodoToma = (TextView)findViewById(R.id.periodoToma);
        duracionMedicina = (EditText)findViewById(R.id.duracionMedicina);
        notasMedicina = (EditText)findViewById(R.id.notasMedicina);
    }

    //Funcion para agregar una medicina
    public void agregarDatos(View view){
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase(); //Abre la base de datos en modo escritura

        //Obtenemos los valores de los campos correspondientes
        String nombre = nombreMedicina.getText().toString();
        String dura = duracionMedicina.getText().toString();
        String notas = notasMedicina.getText().toString();

        //Verificamos que todos los campos estan llenados
        if(!nombre.isEmpty() && verificadorHora1 && verificadorHora2 && !dura.isEmpty()){
            //Convertimos los datos para coincidir con los de la base de datos
            int posicion = 1; //Inicializado en 1 por conveniencia en la BD
            int duracion = Integer.parseInt(dura);


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
            agregar.put("horas",hora);
            agregar.put("minutos", minutos);
            agregar.put("periodoHoras", periodoHoras);
            agregar.put("periodoMinutos", periodoMinutos);
            agregar.put("duracion", duracion);
            agregar.put("notas", notas);

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
    public void limpiado(){
        nombreMedicina.setText("");
        primeraToma.setText("AQUI VA UN TEXTO");
        periodoToma.setText("AQUI VA UN TEXTO");
        duracionMedicina.setText("");
        notasMedicina.setText("");
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
                verificadorHora1= true;

                //Mostramos al usuario la hora seleccionada
                primeraToma.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(horaCompleta.getTime()));
            }
        },horaBase, minutosBase,false);
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
                periodoToma.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(horaCompleta.getTime()));
            }
        },horaBase, minutosBase,false);
        reloj.show();
    }

    //Funcion para modificar una alarma
    public void modificar(View view){
        Intent modificar = new Intent(this, DatosMedicina.class);
        startActivity(modificar);
    }
    public void TEST(View view){
        Intent prueba = new Intent(this, PruebaPicker.class);
        startActivity(prueba);
    }

}
