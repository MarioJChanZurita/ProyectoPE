package com.example.alarma;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AdminSQLiteOpenHelper extends SQLiteOpenHelper {
    public AdminSQLiteOpenHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase BaseDatos) {
        BaseDatos.execSQL("create table datos(posicion int primary key, tiempo int, nombre text, periodoHoras int, periodoMinutos int,periodoTotal int, duracion int,contador int, maximo int)");
        /**
         * posicion = numero de medicina agregada
         * tiempo = segundos independientes de cada medicina
         * nombre = nombre de la medicina
         * periodoHoras = horas que deberian pasar para tomar la medicina
         * periodoMinutos = minutos sobre la hora que deberian pasar para tomar la medicina
         * periodoTotal = segundos totales de la alarma
         * duracion = dias que el usuario tomara la medicina
         * contador = numero de veces que sono la alarma
         * maximo = maximo numero de veces que debe sonar la alarma
         *
         */
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
