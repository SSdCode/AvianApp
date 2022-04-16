package com.sayalife.avianapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.sayalife.avianapp.database.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    Button btn_login, btn_register;
    TextInputLayout emailLayout, passwordLayout;
    TextInputEditText emailEditText, passwordEditText;
    String email, password;
    public static final String TAG = "LoginActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        getSupportActionBar().setTitle("Login");

        getSupportActionBar().setTitle("Login");
        btn_login = findViewById(R.id.btn_login);
        btn_register = findViewById(R.id.btn_register);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        emailEditText = findViewById(R.id.emailEditText);
        passwordEditText = findViewById(R.id.passwordEditText);

        Toast.makeText(getApplicationContext(), "Login with your credentials!", Toast.LENGTH_SHORT).show();

        final CheckBox CBViewHidePass = findViewById(R.id.checkBox);

        CBViewHidePass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    passwordEditText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passwordEditText.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = emailEditText.getText().toString().trim();
                password = passwordEditText.getText().toString().trim();

                if (email.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Email is empty", Toast.LENGTH_SHORT).show();
                    emailLayout.setError("Email is empty");
                } else if (password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "passwords is empty", Toast.LENGTH_SHORT).show();
                    passwordLayout.setError("passwords is empty");
                } else {
                    DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
                    SQLiteDatabase database = helper.getReadableDatabase();

                    String[] columns = {"_id", "RollId"};
                    String[] cValues = {email, password};
                    Cursor cursor = database.query("AllUsers", columns, "EMAIL = ? AND PASSWORD = ?", cValues, null, null, null);
                    Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
                    if (cursor.getCount() > 0) {

                        cursor.moveToFirst();

                        SharedPreferences.Editor editor = getSharedPreferences("LoginDetails", MODE_PRIVATE).edit();
                        editor.putString("email", email);
                        editor.putLong("userId", cursor.getLong(0));
                        editor.putInt("roleId", cursor.getInt(1));
                        editor.apply();

                        database.close();
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), RegistrationActivity.class));
            }
        });
    }

//    public void makeApiCallLogin(String loginDetails) {
//        Call<LoginUser> call = APIService.getService().loginUser("application/json",loginDetails);
//        call.enqueue(new Callback<LoginUser>() {
//
//            @Override
//            public void onResponse(@NonNull Call<LoginUser> call, @NonNull Response<LoginUser> response) {
//                loginUser = response.body();
//                Log.d(TAG, "onResponse: " + response.body());
////                SharedPreferences.Editor editor = getSharedPreferences("LoginDetails", MODE_PRIVATE).edit();
////                editor.putString("email", loginUser.getEmail());
////                editor.apply();
////
////                Toast.makeText(getApplicationContext(), "Login Successfully", Toast.LENGTH_SHORT).show();
////                Intent i = new Intent(getApplicationContext(), MainActivity.class);
////                startActivity(i);
////                finish();
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<LoginUser> call, @NonNull Throwable t) {
//                loginUser = null;
//                Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

}