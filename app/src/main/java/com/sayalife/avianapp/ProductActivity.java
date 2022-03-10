package com.sayalife.avianapp;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sayalife.avianapp.adapter.ProductRecyclerAdapter;
import com.sayalife.avianapp.model.ProductModel;


import java.util.ArrayList;
import java.util.Objects;

public class ProductActivity extends AppCompatActivity {
    ArrayList<ProductModel> arrayList;
    RecyclerView recyclerView;
    ProductRecyclerAdapter productRecyclerAdapter;
    FloatingActionButton mAddExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Products");

        mAddExp = findViewById(R.id.add_fab);
        recyclerView = findViewById(R.id.recyclerViewData);

        ProductData();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        productRecyclerAdapter = new ProductRecyclerAdapter(this, arrayList);
        recyclerView.setAdapter(productRecyclerAdapter);

        productRecyclerAdapter.setOnItemClickListener(position -> Toast.makeText(ProductActivity.this, "Item Id - " + position, Toast.LENGTH_SHORT).show());

        mAddExp.setOnClickListener(v -> showAddProductDialog());
    }

    private void showAddProductDialog() {
        final Dialog dialog = new Dialog(ProductActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.product_dialog);

        final EditText product_code = dialog.findViewById(R.id.product_code);
        final EditText product_name = dialog.findViewById(R.id.product_name);
        final EditText product_category = dialog.findViewById(R.id.product_category);
        final EditText product_size = dialog.findViewById(R.id.product_size);
        final EditText product_color = dialog.findViewById(R.id.product_color);
        final EditText product_transferable = dialog.findViewById(R.id.product_transferable);
        final EditText product_returnable = dialog.findViewById(R.id.product_returnable);
        final EditText product_description = dialog.findViewById(R.id.product_description);
        final EditText product_quantity = dialog.findViewById(R.id.product_quantity);
        AppCompatButton addProductButton = dialog.findViewById(R.id.btnAddProduct);

        addProductButton.setOnClickListener(v -> {
            String product_codeSt = product_code.getText().toString();
            String product_nameSt = product_name.getText().toString();
            String product_categorySt = product_category.getText().toString();
            String product_sizeSt = product_size.getText().toString();
            String product_colorSt = product_color.getText().toString();
            String product_transferableSt = product_transferable.getText().toString();
            String product_returnableSt = product_returnable.getText().toString();
            String product_descriptionSt = product_description.getText().toString();
            String product_quantitySt = product_quantity.getText().toString();

            addProduct(product_codeSt, product_nameSt, product_categorySt, product_sizeSt, product_colorSt, product_transferableSt, product_returnableSt, product_descriptionSt, product_quantitySt);
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void addProduct(String product_code, String product_name, String product_category, String product_size, String product_color, String product_transferable, String product_returnable, String product_description, String product_quantity) {
        if (product_code.equals("") || product_name.equals("") || product_category.equals("") || product_size.equals("") || product_color.equals("") || product_transferable.equals("") || product_returnable.equals("") || product_description.equals("") || product_quantity.equals("")) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
//            Write a code to add Product
            Toast.makeText(getApplicationContext(), "Product added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void ProductData() {
        arrayList = new ArrayList<>();

        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
        arrayList.add(new ProductModel("111101", "Redbull Shirt", "Man", "M", "Blue", "Yes", "Yes", "This is the description for the product.", "48"));
    }
}