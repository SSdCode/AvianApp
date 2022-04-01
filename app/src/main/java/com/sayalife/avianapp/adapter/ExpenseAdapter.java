package com.sayalife.avianapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.R;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.ExpenseModel;

import java.util.ArrayList;
import java.util.List;

public class ExpenseAdapter extends RecyclerView.Adapter<ExpenseAdapter.MyHolder> {
    private OnItemClickListener mListener;
    Context context;
    ArrayList<ExpenseModel> arrayList;
    AlertDialog.Builder builder;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ExpenseAdapter(Context context, ArrayList<ExpenseModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public String getExpenseType(int position) {
        return arrayList.get(position).getExpenseType();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_expense, parent, false);
        MyHolder myHolder = new MyHolder(view, mListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String expenseType = arrayList.get(position).getExpenseType();
        String amount = arrayList.get(position).getAmount();
        String date = arrayList.get(position).getDate();
        String description = arrayList.get(position).getDescription();

        holder.expenseType.setText(expenseType);
        holder.amount.setText(amount);
        holder.date.setText(date);
        holder.description.setText(description);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditExpenseDialog(holder.getAdapterPosition(), expenseType, amount, date, description);
            }
        });
        builder = new AlertDialog.Builder(context);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setMessage("Are you sure want to delete?").setTitle("Delete");

                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

//                                arrayList.remove(holder.getAdapterPosition());
//                                notifyItemRemoved(holder.getAdapterPosition());

                                try {
                                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                                    databaseHelper.deleteExpense(arrayList.get(holder.getAdapterPosition()).getExpenseId());

                                    ArrayList<ExpenseModel> items = databaseHelper.getAllExpenseData();
                                    arrayList.clear();
                                    arrayList.addAll(items);
                                    notifyDataSetChanged();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(context, "Delete cancel", Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete");
                alert.show();
            }
        });
    }

    private void showEditExpenseDialog(int position, String expenseType, String amount, String date, String description) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_expense);

        Spinner sp_expenseType = dialog.findViewById(R.id.sp_expenseType);
        EditText et_expenseAmount = dialog.findViewById(R.id.et_expenseAmount);
        EditText et_description = dialog.findViewById(R.id.et_description);
        Button submitButton = dialog.findViewById(R.id.button_expense_submit);

        DatabaseHelper db = new DatabaseHelper(context);
        List<String> lables = db.getAllExpensesTypes();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, lables);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_expenseType.setAdapter(dataAdapter);

        sp_expenseType.setSelection(dataAdapter.getPosition(expenseType));
//        sp_expenseType.setSelection(1);
        et_expenseAmount.setText(amount);
//        et_date.setText(date);
        et_description.setText(description);

        submitButton.setOnClickListener(v -> {
//            String updatedExpenseType = sp_expenseType.getSelectedItem().toString();
            int updatedExpenseType = db.getExpenseTypeIdByName(sp_expenseType.getSelectedItem().toString());
            String updatedAmount = et_expenseAmount.getText().toString();
            String updatedDescription = et_description.getText().toString();
            editExpense(position, updatedExpenseType, updatedAmount, updatedDescription);
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void editExpense(int position, int expenseType, String amount, String description) {
        if (amount.equals("") || description.equals("")) {
            Toast.makeText(context, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
//            arrayList.get(position).setExpenseType(expenseType);
//            arrayList.get(position).setAmount(amount);
//            arrayList.get(position).setDescription(description);
//            notifyItemChanged(position);
            try {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                databaseHelper.updateExpense(arrayList.get(position).getExpenseId(), expenseType, amount, arrayList.get(position).getDate(), description);

                ArrayList<ExpenseModel> items = databaseHelper.getAllExpenseData();
                arrayList.clear();
                arrayList.addAll(items);
                notifyDataSetChanged();

            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(context, "item no - " + position + " Updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView expenseType, amount, date, description;
        ImageButton btnEdit, btnDelete;

        public MyHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            expenseType = itemView.findViewById(R.id.expense_type);
            amount = itemView.findViewById(R.id.amount);
            date = itemView.findViewById(R.id.date);
            description = itemView.findViewById(R.id.description);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}