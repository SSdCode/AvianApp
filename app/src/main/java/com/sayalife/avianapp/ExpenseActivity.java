package com.sayalife.avianapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sayalife.avianapp.adapter.ExpenseRecyclerAdapter;
import com.sayalife.avianapp.model.ExpenseModel;

import java.util.ArrayList;
import java.util.Objects;

public class ExpenseActivity extends AppCompatActivity {
    ArrayList<ExpenseModel> arrayList;
    RecyclerView recyclerView;
    ExpenseRecyclerAdapter expenseRecyclerAdapter;
    FloatingActionButton mAddExp;
    AppCompatButton btn_product, btn_productTransfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Expense Types");

        mAddExp = findViewById(R.id.add_fab);
        recyclerView = findViewById(R.id.recyclerViewData);
        btn_product = findViewById(R.id.btn_product);
        btn_productTransfer = findViewById(R.id.btn_productTransfer);

        btn_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProductActivity.class));
            }
        });

        btn_productTransfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), com.sayalife.avianapp.ProductTransferActivity.class));
            }
        });

        expensesData();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        expenseRecyclerAdapter = new ExpenseRecyclerAdapter(this, arrayList);
        recyclerView.setAdapter(expenseRecyclerAdapter);

        expenseRecyclerAdapter.setOnItemClickListener(position -> Toast.makeText(ExpenseActivity.this, "Item Id - " + position, Toast.LENGTH_SHORT).show());

        mAddExp.setOnClickListener(v -> showAddExpenseDialog());
    }

    private void showAddExpenseDialog() {
        final Dialog dialog = new Dialog(ExpenseActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.expense_dialog);

        final EditText et_expenseType = dialog.findViewById(R.id.et_expenseType);
        final EditText et_expenseAmount = dialog.findViewById(R.id.et_expenseAmount);
        final EditText et_description = dialog.findViewById(R.id.et_description);
        AppCompatButton submitButton = dialog.findViewById(R.id.button_expense_submit);

        submitButton.setOnClickListener(v -> {
            String type = et_expenseType.getText().toString();
            String amount = et_expenseAmount.getText().toString();
            String description = et_description.getText().toString();
            addExpense(type, amount, description);
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void addExpense(String type, String amount, String description) {
        if (type.equals("") || amount.equals("") || description.equals("")) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
//            Write a code to add expense
            Toast.makeText(getApplicationContext(), "Expense added successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void expensesData() {
        arrayList = new ArrayList<>();

        arrayList.add(new ExpenseModel("Supplier Expenses", "5200", "2/3/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Operating Expenses", "5000", "2/4/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Financial Expenses", "6541", "2/5/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Extraordinary Expenses", "8800", "3/3/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Non-Operating Expenses", "3800", "4/5/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Non-Cash Expenses", "4850", "8/1/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("More Resources", "4960", "12/3/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Office supplies", "7500", "22/5/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Advertising and marketing", "63000", "21/6/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Website and software expenses", "32100", "2/3/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Entertainment", "8754", "2/12/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Vehicle expenses", "2348", "22/11/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Payroll", "7895", "29/8/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Utilities", "9875", "27/9/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Taxes", "9865", "12/5/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Business insurance", "7845", "13/3/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Membership fees", "9650", "15/5/2022", "This is the description for the product."));
        arrayList.add(new ExpenseModel("Training and education", "4800", "8/4/2022", "This is the description for the product."));
    }
}