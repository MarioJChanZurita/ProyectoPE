package com.example.medicinereminder.MostrarAlarma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;

import com.example.medicinereminder.DataBase.AdminSQLiteOpenHelper;
import com.example.medicinereminder.MainActivity;
import com.example.medicinereminder.R;

import java.util.ArrayList;
import java.util.List;

public class MostrarAlarmas extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_alarmas);

        //Recycler View donde se muestran las alarmas
        RecyclerView recyclerViewAlarma = (RecyclerView)findViewById(R.id.recyclerAlarmas);
        recyclerViewAlarma.setLayoutManager(new LinearLayoutManager(this));

        MostrarAdaptador mostrarAdaptador = new MostrarAdaptador(mostrarAlarmas());
        recyclerViewAlarma.setAdapter(mostrarAdaptador);

    }

    //Funcion para obtener todas las alarmas que se van a mostrar en el recycler view
    public List<MostrarModelo> mostrarAlarmas(){
        //Abrimos la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        Cursor lista = baseDatos.rawQuery("SELECT * FROM datos", null);
        //Creamos una lista y alamcenamos en ella el nombre y notas de todas las alarmas
        List<MostrarModelo> mostrar = new ArrayList<>();
        if(lista.moveToFirst()){
            do{
                mostrar.add(new MostrarModelo(lista.getString(2), lista.getString(5)));
            }while(lista.moveToNext());
        }
        baseDatos.close();
        //Regresamos la lista
        return mostrar;
    }

    public void regresar(View view) {
        Intent regresar = new Intent(this, MainActivity.class);
        startActivity(regresar);
    }
}
