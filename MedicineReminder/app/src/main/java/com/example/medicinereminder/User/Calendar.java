package com.example.medicinereminder.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.medicinereminder.R;

public class Calendar extends AppCompatActivity {

    //Variables
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        //Hooks
        backBtn = findViewById(R.id.back_pressed_calendar);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar.super.onBackPressed();
            }
        });
    }
}
