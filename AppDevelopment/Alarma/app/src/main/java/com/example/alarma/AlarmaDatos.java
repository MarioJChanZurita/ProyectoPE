package com.example.alarma;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AlarmaDatos extends AppCompatActivity {

    private TextView nombre, horas, minutos, duracion,posicion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarma_datos);

        //Creamos los objetos de la pantalla
        nombre = (TextView)findViewById(R.id.nombre);
        horas = (TextView)findViewById(R.id.horas);
        minutos = (TextView)findViewById(R.id.minutos);
        duracion = (TextView)findViewById(R.id.duracion);
        posicion =(TextView)findViewById(R.id.posicion);

    }

    public void cargar(View view){
        //Cosas de la BD
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase(); //Abre la base de datos en modo escritura
        String lugar="";

        //Sistema para ver el ultimo elemento en la BD
        //Obtenomos la lista con todas las posiciones en la BD
        Cursor ultimo = baseDatos.rawQuery("SELECT posicion FROM datos", null);
        //Nos posicionamos en el ultimo elemento
        if(ultimo.moveToLast()){
            lugar = ultimo.getString(0);
        }


        Cursor alarma = baseDatos.rawQuery("SELECT posicion, nombre, periodoHoras, periodoMinutos, duracion FROM datos WHERE posicion =" + lugar, null);

        if(alarma.moveToFirst()){
            posicion.setText(alarma.getString(0));
            nombre.setText(alarma.getString(1));
            horas.setText(alarma.getString(2));
            minutos.setText(alarma.getString(3));
            duracion.setText(alarma.getString(4));
            baseDatos.close();
        }else{
            Toast.makeText(this,"No encontrado", Toast.LENGTH_SHORT);
        }
    }

}
