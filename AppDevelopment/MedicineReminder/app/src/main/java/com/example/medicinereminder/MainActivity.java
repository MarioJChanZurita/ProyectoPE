package com.example.medicinereminder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.view.WindowManager;

import com.example.medicinereminder.Alarma.BuscarAlarma;
import com.example.medicinereminder.Alarma.NuevaAlarma;
import com.example.medicinereminder.DataBase.AdminSQLiteOpenHelper;
import com.example.medicinereminder.MostrarAlarma.MostrarAdaptador;
import com.example.medicinereminder.MostrarAlarma.MostrarAlarmas;
import com.example.medicinereminder.MostrarAlarma.MostrarModelo;

import java.util.ArrayList;
import java.util.List;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;


public class MainActivity extends AppCompatActivity implements AIListener {

    RecyclerView recyclerViewAlarma;
    private AIService mAIService;
    private TextToSpeech textToSpeech;
    private final static int INTERNET = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        //Recycler View para mostrar la lista de las alarmas activas
        recyclerViewAlarma = (RecyclerView) findViewById(R.id.recyclerAlarmas);
        recyclerViewAlarma.setLayoutManager(new LinearLayoutManager(this));

        MostrarAdaptador mostrarAdaptador = new MostrarAdaptador(mostrarAlarmas());
        recyclerViewAlarma.setAdapter(mostrarAdaptador);

        //Asistente Inteligente
        validarOS();
        configuracionAsistente();
    }

    //Funcion para actualizar los vista de los recycler views
    @Override
    protected void onResume() {
        super.onResume();
        //Recycler View para mostrar la lista de las alarmas activas
        MostrarAdaptador mostrarAdaptador = new MostrarAdaptador(mostrarAlarmas());
        recyclerViewAlarma.setAdapter(mostrarAdaptador);
    }

    //funcion para ir al activity de buscar alarma
    public void buscarAlarma(View view) {
        Intent buscar = new Intent(this, BuscarAlarma.class);
        startActivity(buscar);
    }

    //funcion para ir al activity de agregar alarma
    public void agregarAlarma(View view) {
        Intent agregar = new Intent(this, NuevaAlarma.class);
        startActivity(agregar);
    }

    //funcion para ir al activity de mostrar todas las alarmas
    public void mostrarAlarma(View view) {
        Intent mostrar = new Intent(this, MostrarAlarmas.class);
        startActivity(mostrar);
    }

    //Funcion para obtener todas las alarmas que se van a mostrar en el recycler view
    public List<MostrarModelo> mostrarAlarmas() {
        //Abrimos la base de datos
        AdminSQLiteOpenHelper admin = new AdminSQLiteOpenHelper(this, "administracion", null, 1);

        //Seleccionamos de la base de datos unicamente las alarmas activas y las mostramos
        Cursor lista = admin.obtenerAlarmasActivas();
        //Creamos una lista y alamcenamos en ella el nombre y notas de todas las alarmas
        List<MostrarModelo> mostrar = new ArrayList<>();
        if (lista.moveToFirst()) {
            do {
                mostrar.add(new MostrarModelo(lista.getString(2), lista.getString(5)));
            } while (lista.moveToNext());
        }

        //Regresamos la lista
        return mostrar;
    }


    //-----------------Asistente Inteligente

    public void configuracionAsistente(){
        AIConfiguration config = new AIConfiguration("20a92088691b4097beaa1d6dd90ab4a2",
                AIConfiguration.SupportedLanguages.Spanish,
                AIConfiguration.RecognitionEngine.System);
        mAIService = AIService.getService(this, config);
        mAIService.setListener(this);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });
    }
    private void validarOS() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, INTERNET);
        }
    }

    @Override
    public void onResult(AIResponse response) {
        Result result = response.getResult();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            textToSpeech.speak(result.getFulfillment().getSpeech(), TextToSpeech.QUEUE_FLUSH, null, null);
        }

    }

    @Override
    public void onError(AIError error) {
    }
    @Override
    public void onAudioLevel(float level) {
    }
    @Override
    public void onListeningStarted() {
    }
    @Override
    public void onListeningCanceled() {
    }
    @Override
    public void onListeningFinished() {
    }
    public void escucharAsistente(View v) {
        mAIService.startListening();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
