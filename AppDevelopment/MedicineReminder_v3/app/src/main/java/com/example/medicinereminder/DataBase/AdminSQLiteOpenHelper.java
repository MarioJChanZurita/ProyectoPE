package com.example.medicinereminder.DataBase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.medicinereminder.Alarma.NuevaAlarma;
import com.example.medicinereminder.MostrarAlarma.MostrarAdaptador;
import com.example.medicinereminder.MostrarAlarma.MostrarModelo;

import java.util.List;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDatos) {
        BaseDatos.execSQL("create table datos(" +
                "posicion int primary key, activar int, nombre text, periodoHoras int, periodoMinutos int, notas text, fecha date, hora time, fechaFinal date)");
        /**
         * posicion = numero de medicina agregada
         * activar = verificar de si alarma esta activada (0-desactivada, 1-activada)
         * nombre = nombre de la medicina
         * periodoHoras = horas que deberian pasar para tomar la medicina
         * periodoMinutos = minutos sobre la hora que deberian pasar para tomar la medicina
         * notas = notas adicionaes
         * fecha = fecha a partir de la cual la alarma se activará
         * hora = hora a partir de la cual la alarma se activará
         * fechaFinal = fecha en la que se desactivará la alarma
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists datos");
        db.execSQL("create table datos(" +
                "posicion int primary key, activar int, nombre text, periodoHoras int, periodoMinutos int, notas text, fecha date, hora time, fechaFinal date)");
        /**
         * posicion = numero de medicina agregada
         * activar = verificador de si alarma esta activada (0-desactivada, 1-activada)
         * nombre = nombre de la medicina
         * periodoHoras = horas que deberian pasar para tomar la medicina
         * periodoMinutos = minutos sobre la hora que deberian pasar para tomar la medicina
         * notas = notas adicionaes
         * fecha = fecha a partir de la cual la alarma se activará
         * hora = hora a partir de la cual la alarma se activará
         * fechaFinal = fecha en la que se desactivará la alarma
         */
    }
    public void agregarDatos(ContentValues agregar){
        SQLiteDatabase baseDatos = getWritableDatabase(); //Abre la base de datos en modo escritura
        baseDatos.insert("datos", null, agregar);
        baseDatos.close();
    }

    public int modificarDatos(ContentValues modificar, int opcionSeleccionada){
        SQLiteDatabase baseDatos = getWritableDatabase(); //Abre la base de datos en modo escritura
        int modificado = baseDatos.update("datos", modificar,"posicion="+opcionSeleccionada, null);

        return modificado;

    }

    //Funcion para obtener solamente las alarmas que se encuentran activas
    public Cursor obtenerAlarmasActivas (){
        SQLiteDatabase baseDatos = getWritableDatabase(); //Abre la base de datos en modo escritura
        Cursor alarmasActivas = baseDatos.rawQuery("SELECT * FROM datos WHERE activar="+1, null);
        return alarmasActivas;
    }

    //Funcion para obtener todas las alarmas
    public Cursor obtenerAlarmas (){
        SQLiteDatabase baseDatos = getWritableDatabase(); //Abre la base de datos en modo escritura
        Cursor alarmas = baseDatos.rawQuery("SELECT * FROM datos", null);
        return alarmas;
    }

    //Funcion para obtener las alarmas que deben activarse
    public Cursor obtenerAlarmaActivarse (String fecha_sistema, String hora_sistema){
        SQLiteDatabase baseDatos = getWritableDatabase(); //Abre la base de datos en modo escritura
        Cursor alarmaActivarse = baseDatos.rawQuery("SELECT * FROM datos WHERE activar='"+1+"' AND fecha ='"+fecha_sistema+"' AND hora = '"+hora_sistema+"'",null);
        return alarmaActivarse;
    }

    //Funcion para obtener la alarma seleccionada
    public Cursor obtenerAlarmaSeleccionada (int seleccion){
        SQLiteDatabase baseDatos = getWritableDatabase(); //Abre la base de datos en modo escritura
        Cursor alarmaSeleccionada = baseDatos.rawQuery("Select * From datos Where posicion="+seleccion,null);
        return alarmaSeleccionada;
    }

    //Funcion para obtener solamente los nombres de todas las alarmas
    public Cursor obtenerNombresAlarmas (){
        SQLiteDatabase baseDatos = getWritableDatabase(); //Abre la base de datos en modo escritura
        Cursor nombresAlarmas = baseDatos.rawQuery("SELECT nombre FROM datos", null);
        return nombresAlarmas;
    }

    //Funcion para obtener solamente las posiciones de todas las alarmas
    public Cursor obtenerPosicionesAlarmas (){
        SQLiteDatabase baseDatos = getWritableDatabase(); //Abre la base de datos en modo escritura
        Cursor posicionesAlarmas =  baseDatos.rawQuery("SELECT posicion FROM datos", null);
        return posicionesAlarmas;
    }

    //Funcion para obtener alarma buscada
    public Cursor obtenerAlarmaBuscada (String alarma){
        SQLiteDatabase baseDatos = getWritableDatabase();
        Cursor alarmaBuscada = baseDatos.rawQuery("SELECT * FROM datos WHERE nombre ='"+alarma+"'",null);
        return alarmaBuscada;
    }

}
