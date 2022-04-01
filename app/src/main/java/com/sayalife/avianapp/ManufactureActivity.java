package com.sayalife.avianapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sayalife.avianapp.adapter.ManufactureAdapter;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.ManufactureModel;

import java.util.ArrayList;

public class ManufactureActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<ManufactureModel> arrayList;
    ManufactureAdapter adapter;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manufacture);

        fab = findViewById(R.id.floatingActionButton);
        recyclerView = findViewById(R.id.brandRecyclerView);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        updateRecyclerView();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addManufacture();
            }
        });
    }

    private void addManufacture() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_manufacture);

        final EditText fName = dialog.findViewById(R.id.fName);
        final EditText lName = dialog.findViewById(R.id.lName);
        final EditText company_name = dialog.findViewById(R.id.company_name);
        final EditText mNo = dialog.findViewById(R.id.mNo);
        final EditText city = dialog.findViewById(R.id.city);
        final EditText state = dialog.findViewById(R.id.state);
        final EditText pinCode = dialog.findViewById(R.id.pinCode);
        final EditText address = dialog.findViewById(R.id.address);
        Button submitButton = dialog.findViewById(R.id.button_manufacture_dialog);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fname = fName.getText().toString();
                String lname = lName.getText().toString();
                String company_name1 = company_name.getText().toString();
                String mno = mNo.getText().toString();
                String city1 = city.getText().toString();
                String state1 = state.getText().toString();
                String pincode = pinCode.getText().toString();
                String address1 = address.getText().toString();

                if (fname.isEmpty() || lname.isEmpty() || company_name1.isEmpty() || mno.isEmpty() || city1.isEmpty() || state1.isEmpty() || pincode.isEmpty() || address1.isEmpty()) {
                    if (fname.isEmpty()) {
                        fName.setError("First name is required");
                    }
                    if (lname.isEmpty()) {
                        lName.setError("Last name is required");
                    }
                    if (company_name1.isEmpty()) {
                        company_name.setError("Company name is required");
                    }
                    if (mno.isEmpty()) {
                        mNo.setError("Mobile number is required");
                    }
                    if (city1.isEmpty()) {
                        city.setError("City is required");
                    }
                    if (state1.isEmpty()) {
                        state.setError("State is required");
                    }
                    if (pincode.isEmpty()) {
                        pinCode.setError("Pin code is required");
                    }
                    if (address1.isEmpty()) {
                        address.setError("Address is required");
                    }
                } else {
                    DatabaseHelper databaseHelper = new DatabaseHelper(ManufactureActivity.this);
                    databaseHelper.addManufacturer(fname, lname, company_name1, mno, city1, state1, pincode, address1);
                    updateRecyclerView();
                    dialog.dismiss();
                }
            }
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void updateRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        arrayList = db.getAllManufacturer();
        adapter = new ManufactureAdapter(this, arrayList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        db.close();
    }

}