package com.example.dialogflow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.JsonElement;

import java.util.Map;

import ai.api.AIListener;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;

public class MainActivity extends AppCompatActivity implements AIListener, View.OnClickListener {

    private AIService mAIService;
    private Button btnVoice;
    private TextView tvResult;
    private final static int INTERNET = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnVoice = (Button) findViewById(R.id.btn_voice);
        tvResult = (TextView) findViewById(R.id.tv_result_main);

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


    @Override
    public void onResult(AIResponse response) {
        Result result = response.getResult();

        String parameterString = "";
        if (result.getParameters() != null && !result.getParameters().isEmpty()){
            for (final Map.Entry<String, JsonElement> entry: result.getParameters().entrySet())
                parameterString += "(" + entry.getKey() + ", " + entry.getValue();
        }

        tvResult.setText("Resultados: " + result.getResolvedQuery() + "\nAction: " + result.getAction() + "\nParametros: " + parameterString);
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
