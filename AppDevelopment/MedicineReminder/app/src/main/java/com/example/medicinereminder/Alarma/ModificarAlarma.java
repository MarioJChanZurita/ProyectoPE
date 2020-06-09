package com.example.medicinereminder.Alarma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.medicinereminder.DataBase.AdminSQLiteOpenHelper;
import com.example.medicinereminder.MainActivity;
import com.example.medicinereminder.R;

import java.util.ArrayList;

public class ModificarAlarma extends AppCompatActivity {

    private EditText nombreMedicina, horasMedicina, minutosMedicina, duracionMedicina, notasMedicina;
    private Spinner nombreAlarmas;

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
                nombreMedicina.setText(datosAlarma.getString(1));
                horasMedicina.setText(datosAlarma.getString(2));
                minutosMedicina.setText(datosAlarma.getString(3));
                duracionMedicina.setText(datosAlarma.getString(4));
                notasMedicina.setText(datosAlarma.getString(5));
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
        String dura = duracionMedicina.getText().toString();
        String nota = notasMedicina.getText().toString();

        //verificamos que los campos estan llenos
        if(!nombre.isEmpty() && !hrs.isEmpty() && !min.isEmpty() && !dura.isEmpty()){
            //Convertimos los datos para coincidir con los de la base de datos
            int horas = Integer.parseInt(hrs);
            int minutos = Integer.parseInt(min);
            int duracion = Integer.parseInt(dura);

            ContentValues modificar = new ContentValues();
            modificar.put("nombre", nombre);
            modificar.put("periodoHoras", horas);
            modificar.put("periodoMinutos", minutos);
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

    public void regresar(View view){
        Intent regresar = new Intent(this, MainActivity.class);
        startActivity(regresar);
    }

}


