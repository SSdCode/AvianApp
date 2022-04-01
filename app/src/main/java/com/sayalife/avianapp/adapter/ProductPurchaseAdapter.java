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
import com.sayalife.avianapp.model.ProductPurchaseModel;

import java.util.ArrayList;
import java.util.List;

public class ProductPurchaseAdapter extends RecyclerView.Adapter<ProductPurchaseAdapter.MyHolder> {

    Context context;
    ArrayList<ProductPurchaseModel> arrayList;
    AlertDialog.Builder builder;

    public ProductPurchaseAdapter(Context context, ArrayList<ProductPurchaseModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_purchase, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        ProductPurchaseModel model = arrayList.get(position);
        holder.batchId.setText(String.valueOf(model.getId()));
        holder.brandId.setText(String.valueOf(model.getBrandId()));
        holder.category.setText(model.getCategory());
        holder.specification.setText(model.getSpecification());
        holder.quantity.setText(String.valueOf(model.getQuantity()));
        holder.price.setText(String.valueOf(model.getPrice()));
        builder = new AlertDialog.Builder(context);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure want to delete?")
                        .setTitle("Delete")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                                databaseHelper.deleteProductPurchase(arrayList.get(holder.getAdapterPosition()).getId());
                                arrayList.clear();
                                arrayList.addAll(databaseHelper.getAllProductPurchase());
                                notifyDataSetChanged();
                                dialog.dismiss();
                                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                                Toast.makeText(context, "cancel", Toast.LENGTH_SHORT).show();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditProductPurchaseDialog(model.getId(), model.getBrandId(), model.getCategory(), model.getSpecification(), model.getQuantity(), model.getPrice());
            }
        });

    }

    private void showEditProductPurchaseDialog(Integer batchId, Integer brandId, String category, String specification, Integer quantity, Integer price) {
        final Dialog dialog = new Dialog(context);
        dialog.setTitle("Edit Product Purchase");
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_product_purchase);

        final Spinner sp_brandId = dialog.findViewById(R.id.brandId);
        final EditText et_category = dialog.findViewById(R.id.category);
        final EditText et_specification = dialog.findViewById(R.id.specification);
        final EditText et_product_quantity = dialog.findViewById(R.id.product_quantity);
        final EditText et_price = dialog.findViewById(R.id.price);
        Button buttonProductPurchase = dialog.findViewById(R.id.button_product_purchase_submit);

        DatabaseHelper db = new DatabaseHelper(context);
        List<String> labels = db.getAllManufacturerNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_brandId.setAdapter(dataAdapter);

        sp_brandId.setSelection(dataAdapter.getPosition(db.getManufactureName(brandId)));
        et_category.setText(category);
        et_specification.setText(specification);
        et_product_quantity.setText(quantity.toString());
        et_price.setText(price.toString());


        buttonProductPurchase.setOnClickListener(v -> {
            String brandId1 = sp_brandId.getSelectedItem().toString();
            String category1 = et_category.getText().toString();
            String specification1 = et_specification.getText().toString();
            String product_quantity1 = et_product_quantity.getText().toString();
            String price1 = et_price.getText().toString();

            if (brandId1.isEmpty() || category1.isEmpty() || specification1.isEmpty() || product_quantity1.isEmpty() || price1.isEmpty()) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                db.updateProductPurchase(new ProductPurchaseModel(batchId, db.getManufactureId(brandId1), category1, specification1, Integer.parseInt(product_quantity1), Integer.parseInt(price1)));
                arrayList.clear();
                arrayList.addAll(db.getAllProductPurchase());
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

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView batchId, brandId, category, specification, quantity, price;
        ImageButton btnEdit, btnDelete;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            batchId = itemView.findViewById(R.id.batchIdProductPurchase);
            brandId = itemView.findViewById(R.id.brandId);
            category = itemView.findViewById(R.id.category);
            specification = itemView.findViewById(R.id.specification);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}
