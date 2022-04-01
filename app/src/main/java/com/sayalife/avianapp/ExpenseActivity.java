package com.sayalife.avianapp;

import android.app.Dialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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
import com.sayalife.avianapp.adapter.ExpenseAdapter;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.ExpenseModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class ExpenseActivity extends AppCompatActivity {
    ArrayList<ExpenseModel> arrayList;
    RecyclerView recyclerView;
    ExpenseAdapter expenseAdapter;
    FloatingActionButton mAddExp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Expenses");

        mAddExp = findViewById(R.id.add_fab);
        recyclerView = findViewById(R.id.recyclerViewData);
//        expensesData();

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        updateRecyclerView();

        expenseAdapter.setOnItemClickListener(position -> Toast.makeText(ExpenseActivity.this, "Item Id - " + position, Toast.LENGTH_SHORT).show());

        mAddExp.setOnClickListener(v -> showAddExpenseDialog());
    }

    private void updateRecyclerView() {
        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        arrayList = db.getAllExpenseData();
        expenseAdapter = new ExpenseAdapter(this, arrayList);
        recyclerView.setAdapter(expenseAdapter);
        expenseAdapter.notifyDataSetChanged();
        db.close();
    }

    private void showAddExpenseDialog() {
        final Dialog dialog = new Dialog(ExpenseActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_expense);

        final Spinner sp_expenseType = dialog.findViewById(R.id.sp_expenseType);
        final EditText et_expenseAmount = dialog.findViewById(R.id.et_expenseAmount);
        final EditText et_description = dialog.findViewById(R.id.et_description);
        Button submitButton = dialog.findViewById(R.id.button_expense_submit);

        DatabaseHelper db = new DatabaseHelper(getApplicationContext());
        List<String> lables = db.getAllExpensesTypes();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_expenseType.setAdapter(dataAdapter);

        submitButton.setOnClickListener(v -> {
            String type = sp_expenseType.getSelectedItem().toString();
            String amount = et_expenseAmount.getText().toString();
            String description = et_description.getText().toString();
            addExpense(type, amount, description);
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void addExpense(String type, String amount, String description) {
        if (type.equals("") || amount.equals("") || description.equals("")) {
            Toast.makeText(this, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
            DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
            SQLiteDatabase database = helper.getWritableDatabase();

            Date c = Calendar.getInstance().getTime();
            System.out.println("Current time => " + c);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
            String formattedDate = df.format(c);

            ContentValues values = new ContentValues();
            values.put("ExpenseTypeId", helper.getExpenseTypeIdByName(type));
            values.put("Amount", amount);
            values.put("ExpenseDate", formattedDate);
            values.put("Description", description);
            values.put("CreatedBy", 1);
            values.put("CreatedDate", "27/03/2022");

            long expense_id = database.insert("Expense", null, values);

            expense_id = Integer.parseInt(String.valueOf(expense_id));
            database.close();
            updateRecyclerView();
            Toast.makeText(getApplicationContext(), "Expense added successfully", Toast.LENGTH_SHORT).show();
        }
    }

}