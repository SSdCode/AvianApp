package com.sayalife.avianapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
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
import com.sayalife.avianapp.model.ProductModel;

import java.util.ArrayList;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.MyHolder> {
    private OnItemClickListener mListener;
    Context context;
    ArrayList<ProductModel> arrayList;
    AlertDialog.Builder builder;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ProductAdapter(Context context, ArrayList<ProductModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product, parent, false);
        MyHolder myHolder = new MyHolder(view, mListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        int product_id = arrayList.get(holder.getAdapterPosition()).getProduct_id();
        String product_name = arrayList.get(holder.getAdapterPosition()).getProduct_name();
        String product_category = arrayList.get(holder.getAdapterPosition()).getProduct_category();
        String product_size = arrayList.get(holder.getAdapterPosition()).getProduct_size();
        String product_color = arrayList.get(holder.getAdapterPosition()).getProduct_color();
        int product_transferable = arrayList.get(holder.getAdapterPosition()).getProduct_transferable();
        int product_returnable = arrayList.get(holder.getAdapterPosition()).getProduct_returnable();
        int manufacturerId = arrayList.get(holder.getAdapterPosition()).getManufacturer_id();
        String product_description = arrayList.get(holder.getAdapterPosition()).getProduct_description();
        String product_quantity = arrayList.get(holder.getAdapterPosition()).getProduct_quantity();
        String product_price = arrayList.get(holder.getAdapterPosition()).getProduct_price();

        Log.d("Data", "onBindViewHolder: " + arrayList.get(holder.getAdapterPosition()).getProduct_id());
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        holder.product_code.setText(String.valueOf(product_id));
        holder.product_name.setText(product_name);
        holder.product_category.setText(product_category);
        holder.product_size.setText(product_size);
        holder.product_color.setText(product_color);
        holder.product_transferable.setText(product_transferable == 0 ? "No" : "Yes");
        holder.product_returnable.setText(product_returnable == 0 ? "No" : "Yes");
        holder.manufacturer.setText(databaseHelper.getManufactureName(manufacturerId));
        holder.product_description.setText(product_description);
        holder.product_quantity.setText(product_quantity);
        holder.product_price.setText(product_price);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditProductDialog(product_id,
                        product_name,
                        product_category,
                        product_size,
                        product_color,
                        product_transferable,
                        product_returnable,
                        manufacturerId,
                        product_description,
                        product_quantity,
                        product_price);
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

                                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                                databaseHelper.deleteProduct(product_id);
                                arrayList.clear();
                                arrayList.addAll(databaseHelper.getAllProducts());
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
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete");
                alert.show();

            }
        });
    }

    private void showEditProductDialog(int product_id,
                                       String product_nameSt,
                                       String product_categorySt,
                                       String product_sizeSt,
                                       String product_colorSt,
                                       int product_transferableSt,
                                       int product_returnableSt,
                                       int manufacturerId,
                                       String product_descriptionSt,
                                       String product_quantitySt,
                                       String product_priceSt) {
        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Edit Product");
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_product);

        final EditText product_name = dialog.findViewById(R.id.product_name);
        final EditText product_category = dialog.findViewById(R.id.product_category);
        final EditText product_size = dialog.findViewById(R.id.product_size);
        final EditText product_color = dialog.findViewById(R.id.product_color);
        final Spinner product_transferable = dialog.findViewById(R.id.product_transferable);
        final Spinner product_returnable = dialog.findViewById(R.id.product_returnable);
        final Spinner manufacturer = dialog.findViewById(R.id.manufacturer);
        final EditText product_description = dialog.findViewById(R.id.product_description);
        final EditText product_quantity = dialog.findViewById(R.id.product_quantity);
        final EditText product_price = dialog.findViewById(R.id.product_price);
        Button submitButton = dialog.findViewById(R.id.btnAddProduct);

        product_name.setText(product_nameSt);
        product_category.setText(product_categorySt);
        product_size.setText(product_sizeSt);
        product_color.setText(product_colorSt);
        product_transferable.setSelection(product_transferableSt == 1 ? 0 : 1);
        product_returnable.setSelection(product_returnableSt == 1 ? 0 : 1);

        DatabaseHelper db = new DatabaseHelper(context);
        List<String> labels = db.getAllManufacturerNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        manufacturer.setAdapter(dataAdapter);
        manufacturer.setSelection(dataAdapter.getPosition(db.getManufactureName(manufacturerId)));
        product_description.setText(product_descriptionSt);
        product_quantity.setText(product_quantitySt);
        product_price.setText(product_priceSt);


        submitButton.setOnClickListener(v -> {
            String Updated_product_nameSt = product_name.getText().toString();
            String Updated_product_categorySt = product_category.getText().toString();
            String Updated_product_sizeSt = product_size.getText().toString();
            String Updated_product_colorSt = product_color.getText().toString();
            int Updated_product_transferableSt = product_transferable.getSelectedItem().toString().equals("Yes") ? 1 : 0;
            int Updated_product_returnableSt = product_returnable.getSelectedItem().toString().equals("Yes") ? 1 : 0;
            int Updated_manufacturerSt = db.getManufactureId(manufacturer.getSelectedItem().toString());
            String Updated_product_descriptionSt = product_description.getText().toString();
            String Updated_product_quantitySt = product_quantity.getText().toString();
            String Updated_product_priceSt = product_price.getText().toString();

            editProduct(product_id,
                    Updated_product_nameSt,
                    Updated_product_categorySt,
                    Updated_product_sizeSt,
                    Updated_product_colorSt,
                    Updated_product_transferableSt,
                    Updated_product_returnableSt,
                    Updated_manufacturerSt,
                    Updated_product_descriptionSt,
                    Updated_product_quantitySt,
                    Updated_product_priceSt);
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    private void editProduct(int product_id, String product_name, String product_category, String product_size, String product_color, int product_transferable, int product_returnable, int manufacturerId, String product_description, String product_quantity, String product_price) {
        if (product_name.equals("") || product_category.equals("") || product_size.equals("") || product_color.equals("") || product_description.equals("") || product_quantity.equals("")) {
            Toast.makeText(context, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
//            Write a code to add Product
            try {
                DatabaseHelper db = new DatabaseHelper(context);
                db.updateProduct(product_id, product_name, product_category, product_size, product_color, product_transferable, product_returnable, manufacturerId, product_description, product_quantity, product_price);
                arrayList.clear();
                arrayList.addAll(db.getAllProducts());
                notifyDataSetChanged();
            } catch (Exception e) {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView product_code, product_name, product_category, product_size, product_color, product_transferable, product_returnable, manufacturer, product_description, product_quantity, product_price;
        ImageButton btnEdit, btnDelete;

        public MyHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            product_code = itemView.findViewById(R.id.product_code);
            product_name = itemView.findViewById(R.id.product_name);
            product_category = itemView.findViewById(R.id.product_category);
            product_size = itemView.findViewById(R.id.product_size);
            product_color = itemView.findViewById(R.id.product_color);
            product_transferable = itemView.findViewById(R.id.product_transferable_tv);
            product_returnable = itemView.findViewById(R.id.product_returnable_tv);
            manufacturer = itemView.findViewById(R.id.manufacturer);
            product_description = itemView.findViewById(R.id.product_description);
            product_quantity = itemView.findViewById(R.id.product_quantity);
            product_price = itemView.findViewById(R.id.product_price);

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