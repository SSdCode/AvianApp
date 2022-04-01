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
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sayalife.avianapp.adapter.ProductTransferAdapter;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.ProductTransferModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ProductTransferActivity extends AppCompatActivity {
    ArrayList<ProductTransferModel> arrayList;
    RecyclerView recyclerView;
    ProductTransferAdapter productTransferAdapter;
    FloatingActionButton mAddExp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_transfer);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Product Transfer");

        mAddExp = findViewById(R.id.add_fab);
        recyclerView = findViewById(R.id.recyclerViewData);


        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        updateRecyclerView();
        mAddExp.setOnClickListener(v -> showAddProductTransferDialog());
    }

    private void updateRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        arrayList = db.getAllProductTransfer();
        productTransferAdapter = new ProductTransferAdapter(this, arrayList);
        recyclerView.setAdapter(productTransferAdapter);
        productTransferAdapter.notifyDataSetChanged();
        db.close();
    }

    private void showAddProductTransferDialog() {
        final Dialog dialog = new Dialog(ProductTransferActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_product_transfer);

        final Spinner sp_product_from = dialog.findViewById(R.id.product_from);
        final Spinner sp_product_to = dialog.findViewById(R.id.product_to);
        final Spinner product_name = dialog.findViewById(R.id.product_name);
        final AppCompatSpinner product_status = dialog.findViewById(R.id.product_status);
        final EditText product_quantity = dialog.findViewById(R.id.product_quantity);
        Button btnProductTransferDialog = dialog.findViewById(R.id.btn_productTransferDialog);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<String> lables = db.getAllStoreNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_product_from.setAdapter(dataAdapter);
        sp_product_to.setAdapter(dataAdapter);

        List<String> products = db.getAllProductNames();
        ArrayAdapter<String> dataAdapterProduct = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_spinner_item, products);
        dataAdapterProduct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product_name.setAdapter(dataAdapterProduct);

        btnProductTransferDialog.setOnClickListener(v -> {
            int product_from_ = db.getStoreId(sp_product_from.getSelectedItem().toString());
            int product_to_ = db.getStoreId(sp_product_to.getSelectedItem().toString());
            int product_id_ = db.getProductId(product_name.getSelectedItem().toString());
            int product_statusSt = product_status.getSelectedItemPosition();
            int product_quantitySt = Integer.parseInt(product_quantity.getText().toString());

            addProduct(product_from_, product_to_, product_id_, product_statusSt, product_quantitySt);
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void addProduct(int product_fromSt, int product_toSt, int product_idSt, int product_statusSt, int product_quantitySt) {
        if (product_fromSt == 0 || product_toSt == 0 || product_idSt == 0 || product_quantitySt == 0) {
            Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseHelper db = new DatabaseHelper(getApplicationContext());
            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);
            db.insertProductTransfer(product_fromSt, product_toSt, product_idSt, formattedDate, product_statusSt, product_quantitySt);
            updateRecyclerView();
            Toast.makeText(getApplicationContext(), "Product Transfer Added", Toast.LENGTH_SHORT).show();
        }
    }
}