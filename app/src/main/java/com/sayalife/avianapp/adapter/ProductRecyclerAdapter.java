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
import com.sayalife.avianapp.model.ProductModel;

import java.util.ArrayList;

public class ProductRecyclerAdapter extends RecyclerView.Adapter<ProductRecyclerAdapter.MyHolder> {
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

    public ProductRecyclerAdapter(Context context, ArrayList<ProductModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_row, parent, false);
        MyHolder myHolder = new MyHolder(view, mListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String product_code = arrayList.get(position).getProduct_code();
        String product_name = arrayList.get(position).getProduct_name();
        String product_category = arrayList.get(position).getProduct_category();
        String product_size = arrayList.get(position).getProduct_size();
        String product_color = arrayList.get(position).getProduct_color();
        String product_transferable = arrayList.get(position).getProduct_transferable();
        String product_returnable = arrayList.get(position).getProduct_returnable();
        String product_description = arrayList.get(position).getProduct_description();
        String product_quantity = arrayList.get(position).getProduct_quantity();


        holder.product_code.setText(product_code);
        holder.product_name.setText(product_name);
        holder.product_category.setText(product_category);
        holder.product_size.setText(product_size);
        holder.product_color.setText(product_color);
        holder.product_transferable.setText(product_transferable);
        holder.product_returnable.setText(product_returnable);
        holder.product_description.setText(product_description);
        holder.product_quantity.setText(product_quantity);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "item no - " + holder.getAdapterPosition() + " Edited", Toast.LENGTH_SHORT).show();
                showEditProductDialog(holder.getAdapterPosition(),
                        product_code,
                        product_name,
                        product_category,
                        product_size,
                        product_color,
                        product_transferable,
                        product_returnable,
                        product_description,
                        product_quantity);
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
                                dialog.dismiss();
                                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(context, "Not Deleted", Toast.LENGTH_SHORT).show();
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

    private void showEditProductDialog(int position,
                                       String product_codeSt,
                                       String product_nameSt,
                                       String product_categorySt,
                                       String product_sizeSt,
                                       String product_colorSt,
                                       String product_transferableSt,
                                       String product_returnableSt,
                                       String product_descriptionSt,
                                       String product_quantitySt) {
        final Dialog dialog = new Dialog(context);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setTitle("Edit Product");
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.product_dialog);

        final EditText product_code = dialog.findViewById(R.id.product_code);
        final EditText product_name = dialog.findViewById(R.id.product_name);
        final EditText product_category = dialog.findViewById(R.id.product_category);
        final EditText product_size = dialog.findViewById(R.id.product_size);
        final EditText product_color = dialog.findViewById(R.id.product_color);
        final EditText product_transferable = dialog.findViewById(R.id.product_transferable);
        final EditText product_returnable = dialog.findViewById(R.id.product_returnable);
        final EditText product_description = dialog.findViewById(R.id.product_description);
        final EditText product_quantity = dialog.findViewById(R.id.product_quantity);
        AppCompatButton addProductButton = dialog.findViewById(R.id.btnAddProduct);
        product_code.setText(product_codeSt);
        product_name.setText(product_nameSt);
        product_category.setText(product_categorySt);
        product_size.setText(product_sizeSt);
        product_color.setText(product_colorSt);
        product_transferable.setText(product_transferableSt);
        product_returnable.setText(product_returnableSt);
        product_description.setText(product_descriptionSt);
        product_quantity.setText(product_quantitySt);


        addProductButton.setOnClickListener(v -> {
            String Updated_product_codeSt = product_code.getText().toString();
            String Updated_product_nameSt = product_name.getText().toString();
            String Updated_product_categorySt = product_category.getText().toString();
            String Updated_product_sizeSt = product_size.getText().toString();
            String Updated_product_colorSt = product_color.getText().toString();
            String Updated_product_transferableSt = product_transferable.getText().toString();
            String Updated_product_returnableSt = product_returnable.getText().toString();
            String Updated_product_descriptionSt = product_description.getText().toString();
            String Updated_product_quantitySt = product_quantity.getText().toString();

            editProduct(position, Updated_product_codeSt,
                    Updated_product_nameSt,
                    Updated_product_categorySt,
                    Updated_product_sizeSt,
                    Updated_product_colorSt,
                    Updated_product_transferableSt,
                    Updated_product_returnableSt,
                    Updated_product_descriptionSt,
                    Updated_product_quantitySt);
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void editProduct(int position, String product_code, String product_name, String product_category, String product_size, String product_color, String product_transferable, String product_returnable, String product_description, String product_quantity) {
        if (product_code.equals("") || product_name.equals("") || product_category.equals("") || product_size.equals("") || product_color.equals("") || product_transferable.equals("") || product_returnable.equals("") || product_description.equals("") || product_quantity.equals("")) {
            Toast.makeText(context, "Fields are empty", Toast.LENGTH_SHORT).show();
        } else {
//            Write a code to add Product
            Toast.makeText(context, "item no - " + position + " Updated", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView product_code, product_name, product_category, product_size, product_color, product_transferable, product_returnable, product_description, product_quantity;
        ImageButton btnEdit, btnDelete;

        public MyHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            product_code = itemView.findViewById(R.id.product_code);
            product_name = itemView.findViewById(R.id.product_name);
            product_category = itemView.findViewById(R.id.product_category);
            product_size = itemView.findViewById(R.id.product_size);
            product_color = itemView.findViewById(R.id.product_color);
            product_transferable = itemView.findViewById(R.id.product_transferable);
            product_returnable = itemView.findViewById(R.id.product_returnable);
            product_description = itemView.findViewById(R.id.product_description);
            product_quantity = itemView.findViewById(R.id.product_quantity);

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