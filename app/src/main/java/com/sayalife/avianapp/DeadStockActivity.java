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
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sayalife.avianapp.adapter.DeadStockAdapter;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.DeadStockModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DeadStockActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DeadStockAdapter deadStockAdapter;
    FloatingActionButton mAddExp;
    ArrayList<DeadStockModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dead_stock);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Dead Stock");
        mAddExp = findViewById(R.id.addButton);
        recyclerView = findViewById(R.id.recyclerViewCards);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        updateRecyclerView();

        deadStockAdapter = new DeadStockAdapter(this, arrayList);
        recyclerView.setAdapter(deadStockAdapter);

        mAddExp.setOnClickListener(v -> showAddStoreDialog());
    }

    private void updateRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        arrayList = db.getAllDeadStock();
        deadStockAdapter = new DeadStockAdapter(this, arrayList);
        recyclerView.setAdapter(deadStockAdapter);
        deadStockAdapter.notifyDataSetChanged();
        db.close();
    }

    private void showAddStoreDialog() {
        final Dialog dialog = new Dialog(DeadStockActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_dead_stock);

        final Spinner product_code = dialog.findViewById(R.id.product_code);
        final EditText quantity = dialog.findViewById(R.id.quantity);
        Button submitButton = dialog.findViewById(R.id.button_deadStock);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<String> products = db.getAllProductNames();
        ArrayAdapter<String> dataAdapterProduct = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, products);
        dataAdapterProduct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product_code.setAdapter(dataAdapterProduct);

        submitButton.setOnClickListener(v -> {
            String productName = product_code.getSelectedItem().toString();
            String quantity1 = quantity.getText().toString();
            addDeadStock(productName, Integer.parseInt(quantity1));
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void addDeadStock(String productName, int quantity) {
        if (productName.isEmpty() || quantity == 0) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
            helper.insertDeadStock(helper.getProductId(productName), quantity);
            updateRecyclerView();
            Toast.makeText(getApplicationContext(), "Dead Stock added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}