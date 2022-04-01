package com.sayalife.avianapp;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sayalife.avianapp.adapter.BankDetailsAdapter;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.BankDetailsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BankDetailsActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    BankDetailsAdapter bankDetailsAdapter;
    FloatingActionButton mAddExp;
    ArrayList<BankDetailsModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_details);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Bank Details");
        mAddExp = findViewById(R.id.addButton);
        recyclerView = findViewById(R.id.recyclerViewCards);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateRecyclerView();

        bankDetailsAdapter = new BankDetailsAdapter(this, arrayList);
        recyclerView.setAdapter(bankDetailsAdapter);

        mAddExp.setOnClickListener(v -> showAddStoreDialog());
    }

    private void updateRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        arrayList = db.getAllBankDetails();
        bankDetailsAdapter = new BankDetailsAdapter(this, arrayList);
        recyclerView.setAdapter(bankDetailsAdapter);
        bankDetailsAdapter.notifyDataSetChanged();
        db.close();
    }

    private void showAddStoreDialog() {
        final Dialog dialog = new Dialog(BankDetailsActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_bank_details);

        final Spinner storeId = dialog.findViewById(R.id.storeId);
        final EditText accountHolderName = dialog.findViewById(R.id.accountHolderName);
        final EditText accountNumber = dialog.findViewById(R.id.accountNumber);
        final EditText bankName = dialog.findViewById(R.id.bankName);
        final EditText branchName = dialog.findViewById(R.id.branchName);
        Button submitButton = dialog.findViewById(R.id.buttonBankDetails);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<String> products = db.getAllStoreNames();
        ArrayAdapter<String> dataAdapterProduct = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, products);
        dataAdapterProduct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeId.setAdapter(dataAdapterProduct);

        submitButton.setOnClickListener(v -> {
            db.insertBankDetails(new BankDetailsModel(db.getStoreId(storeId.getSelectedItem().toString()), accountHolderName.getText().toString(), accountNumber.getText().toString(), bankName.getText().toString(), branchName.getText().toString()));
            arrayList.clear();
            arrayList.addAll(db.getAllBankDetails());
            updateRecyclerView();
            Toast.makeText(getApplicationContext(), "Bank Details Added successfully", Toast.LENGTH_SHORT).show();
            db.close();
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        window.getDecorView().getBackground().setColorFilter(new LightingColorFilter(0xFF000000, getResources().getColor(R.color.red)));
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}