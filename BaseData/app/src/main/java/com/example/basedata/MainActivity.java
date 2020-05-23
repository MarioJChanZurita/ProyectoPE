package com.example.basedata;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private EditText et_nombre,et_duracion,et_periodo , et_notas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_nombre= (EditText)findViewById(R.id.txt_nombre);
        et_duracion = (EditText)findViewById(R.id.txt_duracion);
        et_periodo = (EditText)findViewById(R.id.txt_periodo;
        et_notas = (EditText)findViewById(R.id.txt_notas);
    }

    //Método para dar de alta de los medicamentos
    public void Registrar(View view){
        AdmiSQLiteOpenHelper admin = new AdmiSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String nombre = et_nombre.getText().toString();
        String duracion = et_duracion.getText().toString();
        String periodo = et_periodo.getText().toString();
        String notas = et_notas.getText().toString();

        if(!nombre.isEmpty() && !duracion.isEmpty() && !periodo.isEmpty() && !notas.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("nombre", nombre);
            registro.put("duracion", duracion);
            registro.put("periodo", periodo);
            registro.put("notas", notas);

            BaseDeDatos.insert("medicamentos", null, registro);
            BaseDeDatos.close();
            limpiar();
            Toast.makeText(this,"Registro exitoso", Toast.LENGTH_SHORT).show();

        } else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }
    }

    //Método para consultar un medicamento
    public void Buscar(View view){
        AdmiSQLiteOpenHelper admin = new AdmiSQLiteOpenHelper(this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatabase = admin.getWritableDatabase();

        String nombre = et_nombre.getText().toString();

        if(!nombre.isEmpty()){
            Cursor fila = BaseDeDatabase.rawQuery
                    ("select duracion, periodo, notas from medicamentos where nombre=" + nombre, null);

            if(fila.moveToFirst()){
                et_duracion.setText(fila.getString(0));
                et_periodo.setText(fila.getString(1));
                et_notas.setText(fila.getString(2));
                BaseDeDatabase.close();
            } else {
                Toast.makeText(this,"No existe el medicamento", Toast.LENGTH_SHORT).show();
                BaseDeDatabase.close();
            }

        } else {
            Toast.makeText(this, "Debes introducir el nombre del medicamento", Toast.LENGTH_SHORT).show();
        }
    }

    //Metodo para eliminar un medicamento
    public void Eliminar(View view){
        AdmiSQLiteOpenHelper admin = new AdmiSQLiteOpenHelper
                (this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String nombre = et_nombre.getText().toString();

        if(!nombre.isEmpty()){
            int cantidad = BaseDeDatos.delete
                    ("medicamentos", "nombre="+nombre, null);
            BaseDeDatos.close();
            limpiar();

            //Si se elimina correctamente
            if(cantidad == 1){
                Toast.makeText(this,"Medicamento eliminado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"El medicamento no existe", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this,"Debes introducir el nombre del medicamento", Toast.LENGTH_SHORT).show();
        }

    }

    //Modificar
    public void modificar(View view){
        AdmiSQLiteOpenHelper admin = new AdmiSQLiteOpenHelper
                (this, "administracion", null, 1);
        SQLiteDatabase BaseDeDatos = admin.getWritableDatabase();

        String nombre = et_nombre.getText().toString();
        String duracion = et_duracion.getText().toString();
        String periodo = et_periodo.getText().toString();
        String notas = et_notas.getText().toString();

        if(!nombre.isEmpty() && !duracion.isEmpty() && !periodo.isEmpty() && !notas.isEmpty()){
            ContentValues registro = new ContentValues();
            registro.put("nombre", nombre);
            registro.put("duracion", duracion);
            registro.put("periodo", periodo);
            registro.put("notas", notas);

            int cantidad = BaseDeDatos.update
                    ("medicamentos", registro, "nombre=" + nombre, null);
            BaseDeDatos.close();

            if(cantidad == 1){
                Toast.makeText(this, "Medicamento modificado correctamente", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "Medicamento no existe", Toast.LENGTH_SHORT).show();
            }

        }else{
            Toast.makeText(this, "Debes llenar todos los campos", Toast.LENGTH_SHORT).show();
        }

    }

    public void limpiar(View view){
        et_nombre.setText("");
        et_duracion.setText("");
        et_periodo.setText("");
        et_notas.setText("");
    }

}
