package com.sayalife.avianapp;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.sayalife.avianapp.adapter.GridCountAdapter;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.GridCountModel;

import java.util.ArrayList;

public class DashboardActivity extends AppCompatActivity {
    ArrayList<GridCountModel> arrayList;
    GridView gridView;
    GridCountAdapter adapterGridView;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        gridView = findViewById(R.id.grid_list);
        gridItemShow();
        adapterGridView = new GridCountAdapter(this, arrayList);
        gridView.setAdapter(adapterGridView);

    }

    private void gridItemShow() {
        db = new DatabaseHelper(this);

        int userCount = db.getUserCount();
        int storesCount = db.getStoresCount();
        int manufacturesCount = db.getManufacturesCount();
        int expensesTypesCount = db.getExpensesTypesCount();
        int expensesCount = db.getExpensesCount();
        int productsCount = db.getProductsCount();
        int productPurchaseCount = db.getProductPurchaseCount();
        int revisedPurchasesCount = db.getRevisedPurchasesCount();
        int productsTransfersCount = db.getProductsTransfersCount();
        int deadStockCount = db.getDeadStockCount();
        int bankDetailsCount = db.getBankDetailsCount();

        arrayList = new ArrayList<GridCountModel>();
        arrayList.add(new GridCountModel(R.drawable.ic_user, "Current Users", userCount));
        arrayList.add(new GridCountModel(R.drawable.ic_store, "Current Stores", storesCount));
        arrayList.add(new GridCountModel(R.drawable.ic_manufactures, "Current Manufactures", manufacturesCount));
        arrayList.add(new GridCountModel(R.drawable.ic_products, "Current Products", productsCount));
        arrayList.add(new GridCountModel(R.drawable.ic_expense_types, "Expense Types", expensesTypesCount));
        arrayList.add(new GridCountModel(R.drawable.ic_expense, "Current Expenses", expensesCount));
        arrayList.add(new GridCountModel(R.drawable.ic_purchase, "Product Purchases", productPurchaseCount));
        arrayList.add(new GridCountModel(R.drawable.ic_transfer, "Product Transfers", productsTransfersCount));
        arrayList.add(new GridCountModel(R.drawable.ic_revised_purchase, "Revised Purchases", revisedPurchasesCount));
        arrayList.add(new GridCountModel(R.drawable.ic_dead_stock, "Dead Stock", deadStockCount));
        arrayList.add(new GridCountModel(R.drawable.ic_bank_details, "Bank Details", bankDetailsCount));
    }
}