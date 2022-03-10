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
import com.sayalife.avianapp.adapter.ProductTransferRecyclerAdapter;
import com.sayalife.avianapp.model.ProductTransferModel;

import java.util.ArrayList;
import java.util.Objects;

public class ProductTransferActivity extends AppCompatActivity {
    ArrayList<ProductTransferModel> arrayList;
    RecyclerView recyclerView;
    ProductTransferRecyclerAdapter productTransferRecyclerAdapter;
    FloatingActionButton mAddExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_transfer);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Product Transfer");

        mAddExp = findViewById(R.id.add_fab);
        recyclerView = findViewById(R.id.recyclerViewData);

        ProductData();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        productTransferRecyclerAdapter = new ProductTransferRecyclerAdapter(this, arrayList);
        recyclerView.setAdapter(productTransferRecyclerAdapter);

        productTransferRecyclerAdapter.setOnItemClickListener(position -> Toast.makeText(ProductTransferActivity.this, "Item Id - " + position, Toast.LENGTH_SHORT).show());

        mAddExp.setOnClickListener(v -> showAddProductTransferDialog());
    }

    private void showAddProductTransferDialog() {
        final Dialog dialog = new Dialog(ProductTransferActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.product_transfer_dialog);

        final EditText product_from = dialog.findViewById(R.id.product_from);
        final EditText product_code = dialog.findViewById(R.id.product_code);
        final EditText product_quantity = dialog.findViewById(R.id.product_quantity);
        AppCompatButton btnProductTransferDialog = dialog.findViewById(R.id.btn_productTransferDialog);

        btnProductTransferDialog.setOnClickListener(v -> {
            String product_codeSt = product_code.getText().toString();
            String product_fromSt = product_from.getText().toString();
            String product_quantitySt = product_quantity.getText().toString();

            addProduct(product_fromSt, product_codeSt, product_quantitySt);
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void addProduct(String product_fromSt, String product_codeSt, String product_quantitySt) {
        if (product_fromSt.equals("") || product_codeSt.equals("") || product_quantitySt.equals("")) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
//            Write a code to add Product
            Toast.makeText(getApplicationContext(), "Product added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void ProductData() {
        arrayList = new ArrayList<>();

        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
        arrayList.add(new ProductTransferModel("Avian store padra", "Avian store vadodara", "110254", "22-09-2020", "pending", "50"));
    }
}