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
import com.sayalife.avianapp.model.BankDetailsModel;

import java.util.ArrayList;
import java.util.List;

public class BankDetailsAdapter extends RecyclerView.Adapter<BankDetailsAdapter.ViewHolder> {

    Context context;
    ArrayList<BankDetailsModel> arrayList;
    AlertDialog.Builder builder;

    public BankDetailsAdapter(Context context, ArrayList<BankDetailsModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_bank_details, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BankDetailsAdapter.ViewHolder holder, int position) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        BankDetailsModel model = arrayList.get(position);
        holder.storeId.setText(databaseHelper.getStoreNameById(model.getStoreId()));
        databaseHelper.close();
        holder.accountHolderName.setText(model.getAccountHolderName());
        holder.accountNumber.setText(model.getAccountNumber());
        holder.bankName.setText(model.getBankName());
        holder.branchName.setText(model.getBranchName());
        builder = new AlertDialog.Builder(context);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Delete Bank Details")
                        .setMessage("Are you sure you want to delete this bank details?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                DatabaseHelper db = new DatabaseHelper(context);
                                db.deleteBankDetails(model.getStoreId());
                                arrayList.remove(holder.getAdapterPosition());
                                notifyItemRemoved(holder.getAdapterPosition());
                                dialogInterface.dismiss();
                                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                                Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog(model);
            }
        });
    }

    private void showEditDialog(BankDetailsModel model) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_bank_details);

        final Spinner storeId = dialog.findViewById(R.id.storeId);
        final EditText accountHolderName = dialog.findViewById(R.id.accountHolderName);
        final EditText accountNumber = dialog.findViewById(R.id.accountNumber);
        final EditText bankName = dialog.findViewById(R.id.bankName);
        final EditText branchName = dialog.findViewById(R.id.branchName);
        Button submitButton = dialog.findViewById(R.id.buttonBankDetails);

        DatabaseHelper db = new DatabaseHelper(context);
        List<String> products = db.getAllStoreNames();
        ArrayAdapter<String> dataAdapterProduct = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, products);
        dataAdapterProduct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        storeId.setAdapter(dataAdapterProduct);

        storeId.setSelection(dataAdapterProduct.getPosition(db.getStoreName(model.getStoreId())));
        storeId.setEnabled(false);
        accountHolderName.setText(model.getAccountHolderName());
        accountNumber.setText(model.getAccountNumber());
        bankName.setText(model.getBankName());
        branchName.setText(model.getBranchName());

        submitButton.setOnClickListener(v -> {
            db.updateBankDetails(new BankDetailsModel(model.getStoreId(),
                    accountHolderName.getText().toString().trim(),
                    accountNumber.getText().toString().trim(),
                    bankName.getText().toString().trim(),
                    branchName.getText().toString().trim()));
            arrayList.clear();
            arrayList.addAll(db.getAllBankDetails());
            notifyDataSetChanged();
            db.close();
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView storeId, accountHolderName, accountNumber, bankName, branchName;
        ImageButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            storeId = itemView.findViewById(R.id.storeId);
            accountHolderName = itemView.findViewById(R.id.accountHolderName);
            accountNumber = itemView.findViewById(R.id.accountNumber);
            bankName = itemView.findViewById(R.id.bankName);
            branchName = itemView.findViewById(R.id.branchName);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}
