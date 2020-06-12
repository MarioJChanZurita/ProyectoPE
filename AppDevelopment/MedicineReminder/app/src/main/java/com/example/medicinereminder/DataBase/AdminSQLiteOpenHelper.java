package com.example.medicinereminder.DataBase;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

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
                "posicion int primary key, nombre text, periodoHoras int, periodoMinutos int, duracion int,notas text, fecha date, hora time)");
        /**
         * posicion = numero de medicina agregada
         * nombre = nombre de la medicina
         * periodoHoras = horas que deberian pasar para tomar la medicina
         * periodoMinutos = minutos sobre la hora que deberian pasar para tomar la medicina
         * duracion = dias que el usuario tomara la medicina
         * notas = notas adicionaes
         * fecha = fecha a partir de la cual la alarma se activará
         * hora = hora a partir de la cual la alarma se activará
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists datos");
        db.execSQL("create table datos(" +
                "posicion int primary key, nombre text, periodoHoras int, periodoMinutos int, duracion int,notas text, fecha date, hora time)");
        /**
         * posicion = numero de medicina agregada
         * nombre = nombre de la medicina
         * periodoHoras = horas que deberian pasar para tomar la medicina
         * periodoMinutos = minutos sobre la hora que deberian pasar para tomar la medicina
         * duracion = dias que el usuario tomara la medicina
         * notas = notas adicionaes
         * fecha = fecha a partir de la cual la alarma se activará
         * hora = hora a partir de la cual la alarma se activará
         */
    }

}
