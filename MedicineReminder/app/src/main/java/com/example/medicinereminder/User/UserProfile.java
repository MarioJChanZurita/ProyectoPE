package com.example.medicinereminder.User;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.medicinereminder.R;

public class UserProfile extends AppCompatActivity {

    //Variables
    ImageView backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //Hooks
        backBtn = findViewById(R.id.back_pressed_user_profile);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserProfile.super.onBackPressed();
            }
        });

    }
}
