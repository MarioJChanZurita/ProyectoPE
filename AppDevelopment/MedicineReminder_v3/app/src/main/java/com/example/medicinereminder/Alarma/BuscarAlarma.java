package com.example.medicinereminder.Alarma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SearchView;

import com.example.medicinereminder.DataBase.AdminSQLiteOpenHelper;
import com.example.medicinereminder.MostrarAlarma.MostrarAdaptador;
import com.example.medicinereminder.MostrarAlarma.MostrarModelo;
import com.example.medicinereminder.R;

import java.util.ArrayList;
import java.util.List;

public class BuscarAlarma extends AppCompatActivity {

    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_alarma);

        EditText barra = (EditText)findViewById(R.id.barraBusqueda);

        ListView listView = findViewById(R.id.listaAlarmas);
        List<String> alarmas = alarmasLista();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alarmas);
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


    }

    public List<String> alarmasLista(){
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

}
