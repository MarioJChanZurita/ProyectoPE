package com.example.medicinereminder.Alarma;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.medicinereminder.DataBase.AdminSQLiteOpenHelper;
import com.example.medicinereminder.MostrarAlarma.MostrarAdaptador;
import com.example.medicinereminder.MostrarAlarma.MostrarModelo;
import com.example.medicinereminder.R;

import java.util.ArrayList;
import java.util.List;

import static android.widget.Toast.makeText;

public class BuscarAlarma extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;
    ListView listView;
    EditText barra;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_buscar_alarma);

        barra = (EditText)findViewById(R.id.barraBusqueda);
        listView = findViewById(R.id.listaAlarmas);
        listaAlarmas(generarLista());
    }

    public List<String>generarLista(){
        //Abrimos la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase baseDatos = admin.getWritableDatabase();
        Cursor lista = admin.obtenerAlarmas();
        List<String> alarmas = new ArrayList<>();
        if(lista.moveToFirst()){
            do{
                alarmas.add(lista.getString(2));
            }while(lista.moveToNext());
        }
        baseDatos.close();
        //Regresamos la lista
        return alarmas;
    }

    public void listaAlarmas(List<String> listaNombres){
        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaNombres);
        listView.setAdapter(arrayAdapter);

        barra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                (BuscarAlarma.this).arrayAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int seleccion, long id) {
                String nombre = (String) listView.getItemAtPosition(seleccion);
                iniciarModificar(nombre);
            }
        });
    }

    public void iniciarModificar(String nombre){
        Intent prueba = new Intent(this, ModificarAlarma.class);
        Bundle Parametros = new Bundle();
        Parametros.putString("nombre", nombre);
        prueba.putExtras(Parametros);
        startActivity(prueba);
    }

}
