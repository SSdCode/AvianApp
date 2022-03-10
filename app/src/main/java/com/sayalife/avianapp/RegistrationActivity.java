package com.sayalife.avianapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    TextInputLayout firstNameLayout, lastNameLayout;
    TextInputEditText firstNameEditText, lastNameEditText, etPassword, etConfirmPassword;
    AppCompatSpinner genderAppCompatSpinner;
    EditText editTextPhone, editTextEmailAddress;
    Button signup_btn;

    String fName, lName, gender, phone, email, password, confirmPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        getSupportActionBar().setTitle("Registration");

        getSupportActionBar().setTitle("Registration");
        firstNameLayout = findViewById(R.id.firstNameLayout);
        lastNameLayout = findViewById(R.id.lastNameLayout);
        firstNameEditText = findViewById(R.id.firstNameEditText);
        lastNameEditText = findViewById(R.id.lastNameEditText);
        genderAppCompatSpinner = findViewById(R.id.genderAppCompatSpinner);
        editTextPhone = findViewById(R.id.editTextPhone);
        editTextEmailAddress = findViewById(R.id.editTextEmailAddress);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        signup_btn = findViewById(R.id.signup_btn);


        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                fName = firstNameEditText.getText().toString().trim();
                lName = lastNameEditText.getText().toString().trim();
                gender = genderAppCompatSpinner.getSelectedItem().toString().trim();
                phone = editTextPhone.getText().toString().trim();
                email = editTextEmailAddress.getText().toString().trim();
                password = etPassword.getText().toString().trim();
                confirmPassword = etConfirmPassword.getText().toString().trim();

                Pattern textPattern = Pattern.compile("^[a-zA-Z]+$");
                Pattern phonePattern = Pattern.compile("^[0-9]{10}+$");
                Pattern emailPattern = Pattern.compile("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9]+.+[a-zA-Z]+$");
//Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character:
                Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");


                Matcher fNameMatcher = textPattern.matcher(fName);
                boolean fNameMatches = fNameMatcher.matches();

                Matcher lNameMatcher = textPattern.matcher(lName);
                boolean lNameMatches = lNameMatcher.matches();

                Matcher phoneMatcher = phonePattern.matcher(phone);
                boolean phoneMatches = phoneMatcher.matches();

                Matcher emailMatcher = emailPattern.matcher(email);
                boolean emailMatches = emailMatcher.matches();

                if (fName.isEmpty() || lName.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill all Details", Toast.LENGTH_SHORT).show();
                } else if (!fNameMatches || !lNameMatches || !phoneMatches || !emailMatches) {
                    if (!fNameMatches) {
                        Toast.makeText(getApplicationContext(), "Please Enter First Name properly", Toast.LENGTH_SHORT).show();
                        firstNameLayout.setError("Please Enter First Name properly");
                    } else if (!lNameMatches) {
                        Toast.makeText(getApplicationContext(), "Please Enter Last Name properly", Toast.LENGTH_SHORT).show();
                        lastNameLayout.setError("Please Enter Last Name properly");
                    } else if (!phoneMatches) {
                        Toast.makeText(getApplicationContext(), "Please Enter Phone No properly", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Please Enter Email properly", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    if (!password.equals(confirmPassword)) {
                        Toast.makeText(getApplicationContext(), "Password Not Match", Toast.LENGTH_SHORT).show();
                    } else {

                        Matcher passwordMatcher = passwordPattern.matcher(password);
                        boolean passwordMatches = passwordMatcher.matches();

                        if (!passwordMatches) {
                            Toast.makeText(getApplicationContext(), "Password Error - Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character!", Toast.LENGTH_SHORT).show();
                        } else {
                            SharedPreferences.Editor editor = getSharedPreferences("LoginDetails", MODE_PRIVATE).edit();
                            editor.putString("email", email);
                            editor.apply();

                            Toast.makeText(getApplicationContext(), "Congratulations, You are a new Member", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                            finish();
                        }
                    }
                }
            }
        });
    }
}