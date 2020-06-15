package com.example.medicinereminder.Alarma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
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


        ListView listView = findViewById(R.id.listaAlarmas);
        List<String> alarmas = alarmasLista();

        arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, alarmas);
        listView.setAdapter(arrayAdapter);
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

    public boolean onCreateOptionsMenu(Menu menu){

        getMenuInflater().inflate(R.menu.item_barra, menu);
        MenuItem menuItem = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Searh Here");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                arrayAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return super.onCreateOptionsMenu(menu);

    }
}
