package com.sayalife.avianapp;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sayalife.avianapp.database.DatabaseHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegistrationActivity extends AppCompatActivity {

    TextInputLayout firstNameLayout, lastNameLayout;
    TextInputEditText firstNameEditText, lastNameEditText, etPassword, etConfirmPassword;
    AppCompatSpinner genderAppCompatSpinner, userAppCompatSpinner;
    EditText editTextPhone, editTextEmailAddress;
    Button signup_btn;
    int roleId;
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
        userAppCompatSpinner = findViewById(R.id.userTypeAppCompatSpinner);
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
//                Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$");


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
//
//                        Matcher passwordMatcher = passwordPattern.matcher(password);
//                        boolean passwordMatches = passwordMatcher.matches();

//                        if (!passwordMatches) {
                        if (false) {
                            Toast.makeText(getApplicationContext(), "Password Error - Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character!", Toast.LENGTH_SHORT).show();
                        } else {
                            boolean ans = checkUser(email, phone);

                            if (ans) {
                                DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                                SQLiteDatabase database = helper.getWritableDatabase();
//"CREATE TABLE AllUsers(_id INTEGER PRIMARY KEY AUTOINCREMENT,FNAME VARCHAR(50),LNAME VARCHAR(50),GENDER VARCHAR(10), EMAIL VARCHAR(255),PHONE VARCHAR(255),PASSWORD VARCHAR(50))";
                                ContentValues values = new ContentValues();
                                values.put("FNAME", fName);
                                values.put("LNAME", lName);
                                values.put("GENDER", gender);
                                values.put("EMAIL", email);
                                values.put("PHONE", phone);
                                values.put("PASSWORD", password);

                                if (userAppCompatSpinner.getSelectedItemId() == 0) {
                                    roleId = 4;
                                } else if (userAppCompatSpinner.getSelectedItemId() == 1) {
                                    roleId = 3;
                                } else if (userAppCompatSpinner.getSelectedItemId() == 2) {
                                    roleId = 2;
                                }

                                values.put("ROLE_ID", roleId);

                                long user_id = database.insert("AllUsers", null, values);
                                if (user_id != -1) {
                                    SharedPreferences.Editor editor = getSharedPreferences("LoginDetails", MODE_PRIVATE).edit();
                                    editor.putString("email", email);
                                    editor.putInt("roleId", roleId);
                                    editor.putLong("userId", user_id);
                                    editor.apply();

                                    database.close();
                                    Toast.makeText(getApplicationContext(), "Congratulations, You are a new Member", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(getApplicationContext(), "Error Occurred", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getApplicationContext(), "Email or Phone already exists", Toast.LENGTH_SHORT).show();

                            }
                        }
                    }
                }
            }
        });
    }

    private boolean checkUser(String email, String mono) {
        DatabaseHelper helper = new DatabaseHelper(this);
        final SQLiteDatabase database = helper.getReadableDatabase();

        String readData = "SELECT * FROM AllUsers";
        Cursor cursor = database.rawQuery(readData, null);

        boolean ans = true;
        if (!(cursor.getCount() == 0)) {
            cursor.moveToFirst();
            do {
                String getEmail = cursor.getString(4);
                String mobNo = cursor.getString(5);
                if (email.equals(getEmail) || mono.equals(mobNo)) {
                    ans = false;
                    return ans;
                }
            } while (cursor.moveToNext());
        }
        database.close();
        return ans;
    }
}