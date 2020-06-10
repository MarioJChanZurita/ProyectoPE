package com.example.medicinereminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.medicinereminder.Alarma.ModificarAlarma;
import com.example.medicinereminder.Alarma.NuevaAlarma;
import com.example.medicinereminder.DataBase.AdminSQLiteOpenHelper;
import com.example.medicinereminder.MostrarAlarma.MostrarAdaptador;
import com.example.medicinereminder.MostrarAlarma.MostrarModelo;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Recycler View donde se muestran las alarmas
        RecyclerView recyclerViewAlarma = (RecyclerView)findViewById(R.id.recyclerAlarmas);
        recyclerViewAlarma.setLayoutManager(new LinearLayoutManager(this));

        MostrarAdaptador mostrarAdaptador = new MostrarAdaptador(mostrarAlarmas());
        recyclerViewAlarma.setAdapter(mostrarAdaptador);

    }

    //funcion para ir al activity de agregar alarma
    public void agregar(View view){
        Intent agregar = new Intent(this, NuevaAlarma.class);
        startActivity(agregar);
    }

    //funcion para ir al activiy de modificar alarma
    public void modificar(View view){
        Intent modificar = new Intent(this, ModificarAlarma.class);
        startActivity(modificar);
    }

    //Funcion para obtener todas las alarmas que se van a mostrar en el recycler view
    public List<MostrarModelo> mostrarAlarmas(){
        //Abrimos la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        Cursor lista = baseDatos.rawQuery("SELECT nombre, notas FROM datos", null);
        //Creamos una lista y alamcenamos en ella el nombre y notas de todas las alarmas
        List<MostrarModelo> mostrar = new ArrayList<>();
        if(lista.moveToFirst()){
            do{
                mostrar.add(new MostrarModelo(lista.getString(0), lista.getString(1)));
            }while(lista.moveToNext());
        }
        baseDatos.close();
        //Regresamos la lista
        return mostrar;
    }
}
