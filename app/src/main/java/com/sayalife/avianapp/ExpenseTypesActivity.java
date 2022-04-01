package com.sayalife.avianapp;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sayalife.avianapp.adapter.ExpenseTypeAdapter;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.ExpenseTypeModel;

import java.util.ArrayList;
import java.util.Objects;

public class ExpenseTypesActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ExpenseTypeAdapter expenseTypesRecyclerAdapter;
    FloatingActionButton mAddExp;
    ArrayList<ExpenseTypeModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_types);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Expense Types");

        mAddExp = findViewById(R.id.add_fab);
        recyclerView = findViewById(R.id.recyclerViewData);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        updateRecyclerView();

        expenseTypesRecyclerAdapter.setOnItemClickListener(position -> Toast.makeText(ExpenseTypesActivity.this, "Item Id - " + position, Toast.LENGTH_SHORT).show());

        mAddExp.setOnClickListener(v -> showAddExpenseDialog());
    }

    private void updateRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        arrayList = db.getAllExpensesTypesModels();
        expenseTypesRecyclerAdapter = new ExpenseTypeAdapter(this, arrayList);
        recyclerView.setAdapter(expenseTypesRecyclerAdapter);
        expenseTypesRecyclerAdapter.notifyDataSetChanged();
        db.close();
    }

    private void showAddExpenseDialog() {
        final Dialog dialog = new Dialog(ExpenseTypesActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_expense_type);

        final EditText et_expenseType = dialog.findViewById(R.id.et_expenseType);
        Button submitButton = dialog.findViewById(R.id.button_expense_submit);

        submitButton.setOnClickListener(v -> {
            String type = et_expenseType.getText().toString();
            addExpense(type);
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void addExpense(String type) {
        if (type.equals("")) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {

            DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
            SQLiteDatabase database = helper.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put("Name", type);
            values.put("CreatedBy", 1);
            values.put("CreatedDate", "2020-01-01");

            long expense_id = database.insert("ExpenseType", null, values);

            expense_id = Integer.parseInt(String.valueOf(expense_id));
            database.close();

            updateRecyclerView();
            Toast.makeText(getApplicationContext(), "Expense Type added successfully", Toast.LENGTH_SHORT).show();
        }
    }
}