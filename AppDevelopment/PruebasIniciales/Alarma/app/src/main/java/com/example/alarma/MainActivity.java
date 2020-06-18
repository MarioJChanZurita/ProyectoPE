package com.example.alarma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText nombreMedicina, horasMedicina, minutosMedicina, duracionMedicina, notasMedicina;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombreMedicina = (EditText)findViewById(R.id.nombreMedicina);
        horasMedicina = (EditText)findViewById(R.id.horasMedicina);
        minutosMedicina = (EditText)findViewById(R.id.minutosMedicina);
        duracionMedicina = (EditText)findViewById(R.id.duracionMedicina);
        notasMedicina = (EditText)findViewById(R.id.notasMedicina);
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

        //Verificamos que todos los campos estan llenados
        if(!nombre.isEmpty() && !hrs.isEmpty() && !min.isEmpty() && !dura.isEmpty()){
            //Convertimos los datos para coincidir con los de la base de datos
            int posicion = 1; //Inicializado en 1 por conveniencia en la BD
            int horas = Integer.parseInt(hrs);
            int minutos = Integer.parseInt(min);
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
            agregar.put("periodoHoras", horas);
            agregar.put("periodoMinutos", minutos);
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
        horasMedicina.setText("");
        minutosMedicina.setText("");
        duracionMedicina.setText("");
        notasMedicina.setText("");
    }

    //Funcion para modificar una alarma
    public void modificar(View view){
        Intent modificar = new Intent(this, AlarmaDatos.class);
        startActivity(modificar);
    }
}
