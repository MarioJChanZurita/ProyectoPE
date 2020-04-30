package com.example.medicinereminder.User;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.example.medicinereminder.R;

public class SignUp extends AppCompatActivity {

    //Variables
    ImageView backBtn;
    Button callUserDashboard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up);

        //Hooks
        backBtn = findViewById(R.id.back_pressed_sign_up);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignUp.super.onBackPressed();
            }
        });

        callUserDashboard = findViewById(R.id.signup_to_home);
        callUserDashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this,UserDashboard.class);
                startActivity(intent);
            }
        });

    }
}
