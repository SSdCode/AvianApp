package com.sayalife.avianapp;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;

public class FormActivity extends AppCompatActivity {
    AppCompatTextView firstName , lastName , company , address , pin , mobile , city , state ;
    AppCompatButton save , cancel ;
    AppCompatEditText editFirstName , editLastName , editCompany , editAddress , editPin , editMobile , editCity , editState;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        init();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FormActivity.this, "SAVED", Toast.LENGTH_SHORT).show();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(FormActivity.this, "Cancel", Toast.LENGTH_SHORT).show();
            }
        });


    }
    public void init()
    {   // textView
        firstName = findViewById(R.id.formFirstName);
        lastName = findViewById(R.id.formLastName);
        company = findViewById(R.id.formCompany);
        address = findViewById(R.id.formAddress);
        pin = findViewById(R.id.formPin);
        mobile = findViewById(R.id.formMobile);
        city = findViewById(R.id.formCity);
        state = findViewById(R.id.formState);

        //Edit Text
        editFirstName = findViewById(R.id.editFormFirstName);
        editLastName = findViewById(R.id.editFormLastName);
        editCompany = findViewById(R.id.supplyEditCompany);
       editAddress = findViewById(R.id.editFormAddress);
       editPin = findViewById(R.id.editFormPin);
        editMobile = findViewById(R.id.editFormMobile);
        editCity = findViewById(R.id.editFormCity);
       editState = findViewById(R.id.editFormState);

       // Button
        save = findViewById(R.id.save);
        cancel = findViewById(R.id.cancel);

    }
}