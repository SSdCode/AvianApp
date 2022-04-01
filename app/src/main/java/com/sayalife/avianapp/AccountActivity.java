package com.sayalife.avianapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.sayalife.avianapp.database.DatabaseHelper;

public class AccountActivity extends AppCompatActivity {
    TextView emailTV, fNameTV, lNameTV, genderTV, phoneTV, roleTV;
    MaterialButton btn_logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        emailTV = findViewById(R.id.emailTV);
        fNameTV = findViewById(R.id.fNameTV);
        lNameTV = findViewById(R.id.lNameTV);
        genderTV = findViewById(R.id.genderTV);
        phoneTV = findViewById(R.id.phoneTV);
        roleTV = findViewById(R.id.roleTV);
        btn_logout = findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("LoginDetails", MODE_PRIVATE);
                pref.edit().clear().apply();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        SharedPreferences prefs = getSharedPreferences("LoginDetails", MODE_PRIVATE);
        String email = prefs.getString("email", "No email defined");
        long userId = prefs.getLong("userId", 0);


//        String[] columns = {"_id", "EMAIL", "PASSWORD"};
        String[] columns = {"*"};
        String[] cValues = {String.valueOf(userId)};

        Cursor cursor = db.query("AllUsers", columns, "_id = ?", cValues, null, null, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            fNameTV.setText(cursor.getString(1));
            lNameTV.setText(cursor.getString(2));
            genderTV.setText(cursor.getString(3));
            emailTV.setText(cursor.getString(4));
            phoneTV.setText(cursor.getString(5));
            roleTV.setText(cursor.getString(6));
            Log.v("User Found", DatabaseUtils.dumpCursorToString(cursor));
        } else {
            Log.v("User Not Found", DatabaseUtils.dumpCursorToString(cursor));
        }
    }
}