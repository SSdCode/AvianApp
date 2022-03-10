package com.sayalife.avianapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.R;

import java.util.ArrayList;

public class ExpenseRecyclerAdapter extends RecyclerView.Adapter<ExpenseRecyclerAdapter.MyHolder> {
    private OnItemClickListener mListener;
    Context context;
    ArrayList<com.sayalife.avianapp.model.ExpenseModel> arrayList;
    AlertDialog.Builder builder;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ExpenseRecyclerAdapter(Context context, ArrayList<com.sayalife.avianapp.model.ExpenseModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    public String getExpenseType(int position) {
        return arrayList.get(position).getExpenseType();
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.expense_row, parent, false);
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

                builder.setMessage("Are you sure want to delete?") .setTitle("Delete");

                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                                Toast.makeText(context,"Item Deleted",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(context,"Not Deleted",Toast.LENGTH_SHORT).show();
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
        dialog.setContentView(R.layout.expense_dialog);

        final EditText et_expenseType = dialog.findViewById(R.id.et_expenseType);
        final EditText et_expenseAmount = dialog.findViewById(R.id.et_expenseAmount);
        final EditText et_description = dialog.findViewById(R.id.et_description);
        AppCompatButton submitButton = dialog.findViewById(R.id.button_expense_submit);


        et_expenseType.setText(expenseType);
        et_expenseAmount.setText(amount);
//        et_date.setText(date);
        et_description.setText(description);

        submitButton.setOnClickListener(v -> {
            String updatedExpenseType = et_expenseType.getText().toString();
            String updatedAmount = et_expenseAmount.getText().toString();
            String updatedDescription = et_description.getText().toString();
            editExpense(position, updatedExpenseType, updatedAmount, updatedDescription);
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void editExpense(int position, String expenseType, String amount, String description) {
        if (expenseType.equals("") || amount.equals("") || description.equals("")) {
            Toast.makeText(context, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
//            Write a code to Update expense
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