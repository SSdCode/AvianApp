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
import com.sayalife.avianapp.adapter.ProductAdapter;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.ProductModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProductActivity extends AppCompatActivity {
    ArrayList<ProductModel> arrayList;
    RecyclerView recyclerView;
    ProductAdapter productAdapter;
    FloatingActionButton mAddExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Products");

        mAddExp = findViewById(R.id.add_fab);
        recyclerView = findViewById(R.id.recyclerViewData);


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        updateRecyclerView();
        mAddExp.setOnClickListener(v -> showAddProductDialog());
    }

    private void updateRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        arrayList = db.getAllProducts();
        productAdapter = new ProductAdapter(this, arrayList);
        recyclerView.setAdapter(productAdapter);
        productAdapter.notifyDataSetChanged();
        db.close();
    }

    private void showAddProductDialog() {
        final Dialog dialog = new Dialog(ProductActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_product);

        final EditText product_name = dialog.findViewById(R.id.product_name);
        final EditText product_category = dialog.findViewById(R.id.product_category);
        final EditText product_size = dialog.findViewById(R.id.product_size);
        final EditText product_color = dialog.findViewById(R.id.product_color);
        final Spinner product_transferable = dialog.findViewById(R.id.product_transferable);
        final Spinner product_returnable = dialog.findViewById(R.id.product_returnable);
        final Spinner manufacturer = dialog.findViewById(R.id.manufacturer);
        final EditText product_description = dialog.findViewById(R.id.product_description);
        final EditText product_quantity = dialog.findViewById(R.id.product_quantity);
        final EditText product_price = dialog.findViewById(R.id.product_price);
        Button addProductButton = dialog.findViewById(R.id.btnAddProduct);
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<String> labels = db.getAllManufacturerNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        manufacturer.setAdapter(dataAdapter);
        addProductButton.setOnClickListener(v -> {
            String product_nameSt = product_name.getText().toString();
            String product_categorySt = product_category.getText().toString();
            String product_sizeSt = product_size.getText().toString();
            String product_colorSt = product_color.getText().toString();
            String product_transferableSt = product_transferable.getSelectedItem().toString();
            String product_returnableSt = product_returnable.getSelectedItem().toString();
            String product_manufacturer = manufacturer.getSelectedItem().toString();
            String product_descriptionSt = product_description.getText().toString();
            String product_quantitySt = product_quantity.getText().toString();
            String product_priceSt = product_price.getText().toString();

            int transferable = 0;
            int returnable = 0;
            if (product_transferableSt.equals("Yes")) {
                transferable = 1;
            }
            if (product_returnableSt.equals("Yes")) {
                returnable = 1;
            }

            addProduct(product_nameSt, product_categorySt, product_sizeSt, product_colorSt, transferable, returnable, product_manufacturer, product_descriptionSt, product_quantitySt, product_priceSt);
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void addProduct(String product_name, String product_category, String product_size, String product_color, int product_transferable, int product_returnable, String product_manufacturer, String product_description, String product_quantity, String product_price) {
        if (product_name.equals("") || product_category.equals("") || product_size.equals("") || product_color.equals("") || product_manufacturer.equals("") || product_description.equals("") || product_quantity.equals("") || product_price.equals("")) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
//            Write a code to add Product
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            db.addProduct(product_name, product_category, product_size, product_color, product_transferable, product_returnable, db.getManufactureId(product_manufacturer), product_description, product_quantity, product_price);
            db.close();
            updateRecyclerView();
            Toast.makeText(getApplicationContext(), "Product added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}