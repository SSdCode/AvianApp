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
import com.sayalife.avianapp.model.DeadStockModel;

import java.util.ArrayList;
import java.util.List;

public class DeadStockAdapter extends RecyclerView.Adapter<DeadStockAdapter.MyHolder> {
    Context context;
    ArrayList<DeadStockModel> arrayList;
    AlertDialog.Builder builder;

    public DeadStockAdapter(Context context, ArrayList<DeadStockModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_dead_stock, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeadStockAdapter.MyHolder holder, int position) {
        DeadStockModel model = arrayList.get(position);
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        holder.dead_stock_id.setText(String.valueOf(model.getId()));
        holder.product_code.setText(databaseHelper.getProductNameById(model.getProductId()));
        holder.quantity.setText(String.valueOf(model.getQuantity()));
        builder = new AlertDialog.Builder(context);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Delete");
                builder.setMessage("Are you sure you want to delete this item?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        DatabaseHelper db = new DatabaseHelper(context);
                        db.deleteDeadStock(model.getId());
                        arrayList.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        dialog.dismiss();
                        Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        dialog.cancel();
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

    private void showEditDialog(DeadStockModel model) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_dead_stock);

        final Spinner product_code = dialog.findViewById(R.id.product_code);
        final EditText quantity = dialog.findViewById(R.id.quantity);
        Button submitButton = dialog.findViewById(R.id.button_deadStock);

        DatabaseHelper db = new DatabaseHelper(context);
        List<String> products = db.getAllProductNames();
        ArrayAdapter<String> dataAdapterProduct = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, products);
        dataAdapterProduct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product_code.setAdapter(dataAdapterProduct);
        product_code.setSelection(dataAdapterProduct.getPosition(db.getProductNameById(model.getProductId())));
        quantity.setText(String.valueOf(model.getQuantity()));

        submitButton.setOnClickListener(v -> {
            String productName = product_code.getSelectedItem().toString();
            String quantity1 = quantity.getText().toString();
            db.updateDeadStock(new DeadStockModel(model.getId(), db.getProductId(productName), Integer.parseInt(quantity1)));
            arrayList.clear();
            arrayList.addAll(db.getAllDeadStock());
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

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView dead_stock_id, product_code, quantity;
        ImageButton btnEdit, btnDelete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            dead_stock_id = itemView.findViewById(R.id.dead_stock_id);
            product_code = itemView.findViewById(R.id.product_code);
            quantity = itemView.findViewById(R.id.quantity);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}
