package com.sayalife.avianapp;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sayalife.avianapp.adapter.StoresAdapter;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.StoresModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class StoresActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    StoresAdapter storesAdapter;
    FloatingActionButton mAddExp;
    ArrayList<StoresModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Stores");
        mAddExp = findViewById(R.id.addButton);
        recyclerView = findViewById(R.id.recyclerViewCards);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateRecyclerView();

        storesAdapter = new StoresAdapter(this, arrayList);
        recyclerView.setAdapter(storesAdapter);

        mAddExp.setOnClickListener(v -> showAddStoreDialog());
    }

    private void updateRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        arrayList = db.getAllStoresData();
        storesAdapter = new StoresAdapter(this, arrayList);
        recyclerView.setAdapter(storesAdapter);
        storesAdapter.notifyDataSetChanged();
        db.close();
    }

    private void showAddStoreDialog() {
        final Dialog dialog = new Dialog(StoresActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_store);

        final EditText et_store_name = dialog.findViewById(R.id.et_store_name);
        final EditText et_license_number = dialog.findViewById(R.id.et_license_number);
        final EditText et_contactNum = dialog.findViewById(R.id.et_contactNum);
        final EditText et_cityName = dialog.findViewById(R.id.et_cityName);
        final EditText et_pinCode = dialog.findViewById(R.id.et_pinCode);
        final EditText et_address = dialog.findViewById(R.id.et_address);
        Button submitButton = dialog.findViewById(R.id.button_store_submit);

        submitButton.setOnClickListener(v -> {
            String store_name = et_store_name.getText().toString();
            String license_number = et_license_number.getText().toString();
            String contactNum = et_contactNum.getText().toString();
            String cityName = et_cityName.getText().toString();
            String pinCode = et_pinCode.getText().toString();
            String address = et_address.getText().toString();
            addStore(store_name, license_number, contactNum, cityName, pinCode, address);
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void addStore(String store_name, String license_number, String contactNum, String cityName, String pinCode, String address) {
        if (store_name.isEmpty() || license_number.isEmpty() || contactNum.isEmpty() || cityName.isEmpty() || pinCode.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
            Date c = Calendar.getInstance().getTime();
            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);

            DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
            SQLiteDatabase database = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("UserId", 2);
            values.put("StoreName", store_name);
            values.put("LicenceNumber", license_number);
            values.put("ContactNumber", contactNum);
            values.put("City", cityName);
            values.put("PinCode", pinCode);
            values.put("Address", address);
            values.put("CreatedBy", 1);
            values.put("CreatedDate", formattedDate);

            long storeId = database.insert("StoreDetail", null, values);
            database.close();

            updateRecyclerView();
            Toast.makeText(getApplicationContext(), "Store added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}