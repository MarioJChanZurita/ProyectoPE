package com.example.asistente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.JsonElement;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class MainActivity extends AppCompatActivity implements AIListener, View.OnClickListener {
    private AIService mAIService;
    private ImageView btnVoice;
    private TextView tvResult, hour, minutes, day, month, year;


    private final static int INTERNET = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnVoice = (ImageView) findViewById(R.id.ActivarAsistente);
        tvResult = (TextView) findViewById(R.id.resultMain);


        hour = (TextView) findViewById(R.id.horas);
        minutes = (TextView) findViewById(R.id.minutos);
        day = (TextView) findViewById(R.id.dia);
        month = (TextView) findViewById(R.id.mes);
        year = (TextView) findViewById(R.id.año);


        validateOS();
        final AIConfiguration config = new AIConfiguration("957edb37bd1c4fd2ba3a6c6e5085dade",
                AIConfiguration.SupportedLanguages.Spanish,
                AIConfiguration.RecognitionEngine.System);
        mAIService = AIService.getService(this, config);
        mAIService.setListener(this);
        btnVoice.setOnClickListener(this);
    }

    private void validateOS() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.RECORD_AUDIO}, INTERNET);
        }
    }

    private int convertirAEntero(int a, int b, String textA) {
        int resultado = Integer.parseInt(textA.substring(a, b));
        return resultado;
    }

    @Override
    public void onResult(AIResponse response) {
        Result result = response.getResult();
        String parameterString = "";
        String auxText = "";
        String auxiliar = "";
        if (result.getParameters() != null && !result.getParameters().isEmpty()) {
            for (final Map.Entry<String, JsonElement> entry : result.getParameters().entrySet()) {
                auxiliar += entry.getKey();
                parameterString += "(" + entry.getKey() + ", " + entry.getValue();
                if (auxiliar == "time") {
                    auxText = entry.getValue().toString();
                    int periodoHoras = convertirAEntero(0, 2, auxText);
                    int periodoMinutos = convertirAEntero(3, 5, auxText);

                    hour.setText(periodoHoras);
                    minutes.setText(periodoMinutos);

                }
                if (auxiliar == "date") {
                    auxText = entry.getValue().toString();
                    int userDay = convertirAEntero(8, 10, auxText);
                    int userMonth = convertirAEntero(5, 7, auxText);
                    int userYear = convertirAEntero(0, 4, auxText);

                    day.setText(userDay);
                    month.setText(userMonth);
                    year.setText(userYear);

                }
                tvResult.setText("Resultados: " + result.getResolvedQuery() + "\nAction: " + result.getAction() + "\nParametros: " + parameterString);

            }
        }
    }

    @Override
    public void onError(AIError error) {
        tvResult.setText(error.toString());
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

    @Override
    public void onClick(View v) {
        mAIService.startListening();
    }

}