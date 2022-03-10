package com.sayalife.avianapp.purchaseproductlist;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sayalife.avianapp.R;
import com.sayalife.avianapp.purchaseproductlist.adapter.PurchaseProductAdapter;
import com.sayalife.avianapp.purchaseproductlist.model.PurchaseProductListModel;

import java.util.ArrayList;
import java.util.Objects;

public class PurchaseProductListActivity extends AppCompatActivity {
    FloatingActionButton add_product;
    RecyclerView recyclerView;
    SearchView search_products;
    PurchaseProductAdapter adapter;
    ArrayList<PurchaseProductListModel> productListModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_product_list);

        initUI();
        productData();
        configureRecyclerView();

        add_product.setOnClickListener(view -> addProduct());
    }

    private void addProduct() {
        final Dialog dialog = new Dialog(PurchaseProductListActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.purchase_product_add_dialog);

        final AppCompatTextView p_brand = dialog.findViewById(R.id.p_brand);
        final AppCompatTextView p_category = dialog.findViewById(R.id.p_category);
        final AppCompatTextView p_specification = dialog.findViewById(R.id.p_specification);
        final AppCompatTextView p_quantity = dialog.findViewById(R.id.p_quantity);
        final AppCompatTextView p_price = dialog.findViewById(R.id.p_price);
        final AppCompatEditText et_brand = dialog.findViewById(R.id.et_brand);
        final AppCompatEditText et_category = dialog.findViewById(R.id.et_category);
        final AppCompatEditText et_specification = dialog.findViewById(R.id.et_specification);
        final AppCompatEditText et_quantity = dialog.findViewById(R.id.et_quantity);
        final AppCompatEditText et_price = dialog.findViewById(R.id.et_price);
        AppCompatButton btn_save = dialog.findViewById(R.id.btn_save);

        btn_save.setOnClickListener(view -> {
            String brand1 = Objects.requireNonNull(et_brand.getText()).toString();
            String category1 = Objects.requireNonNull(et_category.getText()).toString();
            String specification1 = Objects.requireNonNull(et_specification.getText()).toString();
            int quantity1 = et_quantity.getScrollY();
            int price1 = et_price.getScrollY();
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void productData() {
        productListModel = new ArrayList<>();

        productListModel.add(new PurchaseProductListModel(101161, "Nike", "shoes", "IX, black", 2, 2000));
        productListModel.add(new PurchaseProductListModel(101162, "Spykar", "shirts", "XL, blue", 1, 1000));
        productListModel.add(new PurchaseProductListModel(101163, "Puma", "shoes", "IX, red", 3, 1500));
        productListModel.add(new PurchaseProductListModel(101164, "Allen Solly", "shirts", "M, teal", 2, 3000));
        productListModel.add(new PurchaseProductListModel(101165, "HRX", "t-shirts", "L, grey", 1, 1000));
    }

    private void configureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new PurchaseProductAdapter(this, productListModel);
        recyclerView.setAdapter(adapter);
    }

    private void initUI() {
        add_product = findViewById(R.id.add_product);
        recyclerView = findViewById(R.id.recyclerView);
        search_products = findViewById(R.id.search_products);
    }
}