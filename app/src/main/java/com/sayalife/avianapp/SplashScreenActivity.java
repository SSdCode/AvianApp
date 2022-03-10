package com.sayalife.avianapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreenActivity extends AppCompatActivity {
    protected String email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferences prefs = getSharedPreferences("LoginDetails", MODE_PRIVATE);
        email = prefs.getString("email", "No email defined");

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(500);
                    if (!email.equals("No email defined")) {
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    } else {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Interrupted Exception!", Toast.LENGTH_SHORT).show();
                }
            }
        };
        thread.start();
    }
}