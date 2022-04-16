package com.sayalife.avianapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
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
                logout();
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
            int role = cursor.getInt(7);
            switch (role) {
                case 1:
                    roleTV.setText("Super Admin");
                    break;
                case 2:
                    roleTV.setText("Store Admin");
                    break;
                case 3:
                    roleTV.setText("Store Manager");
                    break;
                case 4:
                    roleTV.setText("Store Staff");
                    break;
            }
            Log.v("User Found", DatabaseUtils.dumpCursorToString(cursor));
        } else {
            Log.v("User Not Found", DatabaseUtils.dumpCursorToString(cursor));
        }
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences prefs = getSharedPreferences("LoginDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(AccountActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }
}