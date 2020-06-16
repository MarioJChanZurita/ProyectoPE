package com.example.asistenteinteligente;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.gson.JsonElement;

import org.w3c.dom.Text;

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
    private TextToSpeech textToSpeech;

    private ImageView btnVoice;
    private TextView tvResult, hour, minutes, day, month, year;

    private TextView periodo, hora;
    private EditText fecha, nombre, notas;

    private final static int INTERNET = 200;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnVoice = (ImageView) findViewById(R.id.ActivarAsistente);
        tvResult = (TextView) findViewById(R.id.resultMain);

        nombre = (EditText)findViewById(R.id.nombreMedicina);
        hora = (TextView) findViewById(R.id.horaMedicina);
        periodo = (TextView)findViewById(R.id.periodoMedicina);
        fecha = (EditText)findViewById(R.id.fechaFinalSeleccionada);
        notas = (EditText)findViewById(R.id.notasMedicina);


        validateOS();
        final AIConfiguration config = new AIConfiguration("20a92088691b4097beaa1d6dd90ab4a2",
                AIConfiguration.SupportedLanguages.Spanish,
                AIConfiguration.RecognitionEngine.System);


        mAIService = AIService.getService(this, config);
        mAIService.setListener(this);
        btnVoice.setOnClickListener(this);

        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

            }
        });

    }
    private void validateOS() {
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


    //-------------CONVERSIONES DE VALORES

    //conversion de periodo
    public void conversionPeriodoFormato(int hora, int minuto) {
        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, hora);
        c.set(Calendar.MINUTE, minuto);
        c.set(Calendar.SECOND, 0);

        periodo.setText(String.format("%02d:%02d", hora, minuto));
    }

    //conversion de fecha
    public void conversionFechaFormato(int dia, int mes, int anio){
        Calendar c = Calendar.getInstance();

        c.set(Calendar.HOUR_OF_DAY, dia);
        c.set(Calendar.MONTH, mes);
        c.set(Calendar.YEAR, anio);

        updateFinalDateText(c);

    }
    //Funcion para colocar la fecha seleccionada en la fecha final de la alarma
    private void updateFinalDateText(Calendar c) {
        String dateText = DateFormat.getDateInstance(DateFormat.FULL).format(c.getTime());
        fecha.setText(dateText);
    }


}


