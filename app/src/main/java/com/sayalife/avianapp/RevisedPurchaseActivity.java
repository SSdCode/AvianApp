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
import com.sayalife.avianapp.adapter.RevisedPurchaseAdapter;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.RevisedPurchaseModel;

import java.util.ArrayList;
import java.util.List;

public class RevisedPurchaseActivity extends AppCompatActivity {
    ArrayList<RevisedPurchaseModel> arrayList;
    RecyclerView recyclerView;
    RevisedPurchaseAdapter revisedPurchaseAdapter;
    FloatingActionButton mAddExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revised_purchase);


        mAddExp = findViewById(R.id.add_fab);
        recyclerView = findViewById(R.id.recyclerViewData);


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        updateRecyclerView();
        mAddExp.setOnClickListener(v -> showAddProductPurchaseDialog());
    }

    private void updateRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        arrayList = db.getAllRevisedPurchase();
        revisedPurchaseAdapter = new RevisedPurchaseAdapter(this, arrayList);
        recyclerView.setAdapter(revisedPurchaseAdapter);
        revisedPurchaseAdapter.notifyDataSetChanged();
        db.close();
    }

    private void showAddProductPurchaseDialog() {
        final Dialog dialog = new Dialog(RevisedPurchaseActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_revised_purchase);


        final Spinner brandId = dialog.findViewById(R.id.brandId);
        final EditText description = dialog.findViewById(R.id.description);
        final EditText quantity = dialog.findViewById(R.id.quantity);
        final EditText purchasePrice = dialog.findViewById(R.id.purchasePrice);
        final EditText revisedPrice = dialog.findViewById(R.id.revisedPrice);

        Button buttonProductPurchase = dialog.findViewById(R.id.button_revised_purchase_submit);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<String> labels = db.getAllManufacturerNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandId.setAdapter(dataAdapter);

        buttonProductPurchase.setOnClickListener(v -> {
            String brandId_ = brandId.getSelectedItem().toString();
            String description_ = description.getText().toString();
            String quantity_ = quantity.getText().toString();
            String purchasePrice_ = purchasePrice.getText().toString();
            String revisedPrice_ = revisedPrice.getText().toString();

            if (brandId_.isEmpty() || description_.isEmpty() || quantity_.isEmpty() || purchasePrice_.isEmpty() || revisedPrice_.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                db.insertRevisedPurchase(db.getManufactureId(brandId_), description_.trim(), Integer.parseInt(quantity_.trim()), Integer.parseInt(purchasePrice_.trim()), Integer.parseInt(revisedPrice_.trim()));
                updateRecyclerView();
                db.close();
            }

            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }
}
