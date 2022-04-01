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
import com.sayalife.avianapp.adapter.ProductPurchaseAdapter;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.ProductPurchaseModel;

import java.util.ArrayList;
import java.util.List;

public class ProductPurchaseActivity extends AppCompatActivity {
    ArrayList<ProductPurchaseModel> arrayList;
    RecyclerView recyclerView;
    ProductPurchaseAdapter productPurchaseAdapter;
    FloatingActionButton mAddExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_purchase);

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
        arrayList = db.getAllProductPurchase();
        productPurchaseAdapter = new ProductPurchaseAdapter(this, arrayList);
        recyclerView.setAdapter(productPurchaseAdapter);
        productPurchaseAdapter.notifyDataSetChanged();
        db.close();
    }

    private void showAddProductPurchaseDialog() {
        final Dialog dialog = new Dialog(ProductPurchaseActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_product_purchase);

//        final Spinner batchId = dialog.findViewById(R.id.batchId);
        final Spinner brandId = dialog.findViewById(R.id.brandId);
        final EditText category = dialog.findViewById(R.id.category);
        final EditText specification = dialog.findViewById(R.id.specification);
        final EditText product_quantity = dialog.findViewById(R.id.product_quantity);
        final EditText price = dialog.findViewById(R.id.price);
        Button buttonProductPurchase = dialog.findViewById(R.id.button_product_purchase_submit);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<String> labels = db.getAllManufacturerNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        brandId.setAdapter(dataAdapter);

        buttonProductPurchase.setOnClickListener(v -> {
            String batchId = brandId.getSelectedItem().toString();
            String category1 = category.getText().toString();
            String specification1 = specification.getText().toString();
            String product_quantity1 = product_quantity.getText().toString();
            String price1 = price.getText().toString();

            if (batchId.isEmpty() || category1.isEmpty() || specification1.isEmpty() || product_quantity1.isEmpty() || price1.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                db.insertProductPurchase(db.getManufactureId(batchId), category1, specification1, Integer.parseInt(product_quantity1), Integer.parseInt(price1));
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