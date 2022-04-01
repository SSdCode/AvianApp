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
import android.widget.Button;
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
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.ExpenseTypeModel;

import java.util.ArrayList;

public class ExpenseTypeAdapter extends RecyclerView.Adapter<ExpenseTypeAdapter.MyHolder> {

    private OnItemClickListener mListener;
    Context context;
    ArrayList<ExpenseTypeModel> arrayList;
    AlertDialog.Builder builder;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ExpenseTypeAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public ExpenseTypeAdapter(Context context, ArrayList<ExpenseTypeModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_expense_type, parent, false);
        ExpenseTypeAdapter.MyHolder myHolder = new ExpenseTypeAdapter.MyHolder(view, mListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ExpenseTypeAdapter.MyHolder holder, int position) {
        ExpenseTypeModel expenseType = arrayList.get(position);
        holder.expenseType.setText(expenseType.getExpenseType());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditExpenseTypeDialog(holder.getAdapterPosition(), expenseType.getExpenseType());
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
                                    databaseHelper.deleteExpenseType(expenseType.getExpenseTypeId());

                                    ArrayList<ExpenseTypeModel> items = databaseHelper.getAllExpensesTypesModels();
                                    arrayList.clear();
                                    arrayList.addAll(items);
                                    notifyDataSetChanged();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                                notifyItemRemoved(holder.getAdapterPosition());
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

    private void showEditExpenseTypeDialog(int position, String expenseType) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_expense_type);

        final EditText et_expenseType = dialog.findViewById(R.id.et_expenseType);
        Button submitButton = dialog.findViewById(R.id.button_expense_submit);

        et_expenseType.setText(expenseType);

        submitButton.setOnClickListener(v -> {
            String updatedExpenseType = et_expenseType.getText().toString();
            editExpenseType(position, updatedExpenseType);
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void editExpenseType(int position, String expenseType) {
        if (expenseType.equals("")) {
            Toast.makeText(context, "Field is empty", Toast.LENGTH_SHORT).show();
        } else {
//            arrayList.get(position).setExpenseType(expenseType);
//            notifyItemChanged(position);
            try {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                databaseHelper.updateExpenseType(arrayList.get(position).getExpenseTypeId(), expenseType);

                ArrayList<ExpenseTypeModel> items = databaseHelper.getAllExpensesTypesModels();
                arrayList.clear();
                arrayList.addAll(items);
                notifyDataSetChanged();

            }catch (Exception e){
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
        TextView expenseType;
        ImageButton btnEdit, btnDelete;

        public MyHolder(@NonNull View itemView, final ExpenseTypeAdapter.OnItemClickListener listener) {
            super(itemView);
            expenseType = itemView.findViewById(R.id.expense_type);

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
