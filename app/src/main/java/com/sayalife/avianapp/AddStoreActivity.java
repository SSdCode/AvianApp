package com.sayalife.avianapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddStoreActivity extends AppCompatActivity {

    EditText storeName , phoneNum , city , state , pinNum , address;
    Button save, buttonStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_store);



        storeName = findViewById(R.id.storeName);
        phoneNum = findViewById(R.id.phoneNum);
        city = findViewById(R.id.city);
        state = findViewById(R.id.state);
        pinNum = findViewById(R.id.pinNum);
        address = findViewById(R.id.address);

        save = findViewById(R.id.save);
        buttonStore = findViewById(R.id.buttonStore);





        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AddStoreActivity.this, "DETAILS SAVED", Toast.LENGTH_SHORT).show();
            }
        });

        buttonStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent myIntent = new Intent(AddStoreActivity.this, MainActivity.class);
                startActivity(myIntent);

            }
        });
    }
}