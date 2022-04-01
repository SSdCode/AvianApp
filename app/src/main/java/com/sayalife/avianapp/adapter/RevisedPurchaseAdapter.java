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
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.R;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.RevisedPurchaseModel;

import java.util.ArrayList;
import java.util.List;

public class RevisedPurchaseAdapter extends RecyclerView.Adapter<RevisedPurchaseAdapter.ViewHolder> {

    Context context;
    ArrayList<RevisedPurchaseModel> arrayList;
    AlertDialog.Builder builder;

    public RevisedPurchaseAdapter(Context context, ArrayList<RevisedPurchaseModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_revised_purchase, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        RevisedPurchaseModel model = arrayList.get(position);
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        holder.batchId.setText(String.valueOf(model.getBatchId()));
        holder.brandId.setText(databaseHelper.getManufactureName(model.getBrandId()));
        holder.description.setText(model.getDescription());
        holder.quantity.setText(String.valueOf(model.getQuantity()));
        holder.purchasePrice.setText(String.valueOf(model.getPurchasePrice()));
        holder.revisedPrice.setText(String.valueOf(model.getRevisedPrice()));
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
                                databaseHelper.deleteRevisedPurchase(arrayList.get(holder.getAdapterPosition()).getBatchId());
                                arrayList.clear();
                                arrayList.addAll(databaseHelper.getAllRevisedPurchase());
                                notifyDataSetChanged();
                                dialog.dismiss();
                                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.setTitle("Delete");
                alert.show();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditDialog(model.getBatchId(), model.getBrandId(), model.getDescription(), model.getQuantity(), model.getPurchasePrice(), model.getRevisedPrice());
            }
        });
    }

    private void showEditDialog(Integer batchId, Integer brandId, String description, Integer quantity, Integer purchasePrice, Integer revisedPrice) {
        final Dialog dialog = new Dialog(context);
        dialog.setTitle("Edit Revised Product Purchase");
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_revised_purchase);

        final Spinner v_brandId = dialog.findViewById(R.id.brandId);
        final EditText v_description = dialog.findViewById(R.id.description);
        final EditText v_quantity = dialog.findViewById(R.id.quantity);
        final EditText v_purchasePrice = dialog.findViewById(R.id.purchasePrice);
        final EditText v_revisedPrice = dialog.findViewById(R.id.revisedPrice);

        Button buttonProductPurchase = dialog.findViewById(R.id.button_revised_purchase_submit);

        DatabaseHelper db = new DatabaseHelper(context);
        List<String> labels = db.getAllManufacturerNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        v_brandId.setAdapter(dataAdapter);

        v_brandId.setSelection(dataAdapter.getPosition(db.getManufactureName(brandId)));
        v_description.setText(description);
        v_quantity.setText(String.valueOf(quantity));
        v_purchasePrice.setText(String.valueOf(purchasePrice));
        v_revisedPrice.setText(String.valueOf(revisedPrice));


        buttonProductPurchase.setOnClickListener(v -> {
            String companyName = v_brandId.getSelectedItem().toString();
            String description1 = v_description.getText().toString();
            String quantity1 = v_quantity.getText().toString();
            String purchasePrice1 = v_purchasePrice.getText().toString();
            String revisedPrice1 = v_revisedPrice.getText().toString();

            if (v_description.getText().toString().isEmpty() || v_quantity.getText().toString().isEmpty() || v_purchasePrice.getText().toString().isEmpty() || v_revisedPrice.getText().toString().isEmpty()) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                db.updateRevisedPurchase(new RevisedPurchaseModel(batchId, db.getManufactureId(companyName), description1, Integer.parseInt(quantity1), Integer.parseInt(purchasePrice1), Integer.parseInt(revisedPrice1)));
                arrayList.clear();
                arrayList.addAll(db.getAllRevisedPurchase());
                notifyDataSetChanged();
                db.close();
            }

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
        TextView batchId, brandId, description, quantity, purchasePrice, revisedPrice;
        ImageButton btnEdit, btnDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            batchId = itemView.findViewById(R.id.batchId);
            brandId = itemView.findViewById(R.id.brandId);
            description = itemView.findViewById(R.id.description);
            quantity = itemView.findViewById(R.id.quantity);
            purchasePrice = itemView.findViewById(R.id.purchasePrice);
            revisedPrice = itemView.findViewById(R.id.revisedPrice);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}
