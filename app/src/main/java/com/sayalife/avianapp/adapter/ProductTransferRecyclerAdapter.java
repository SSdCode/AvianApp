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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.R;
import com.sayalife.avianapp.model.ProductTransferModel;

import java.util.ArrayList;

public class ProductTransferRecyclerAdapter extends RecyclerView.Adapter<ProductTransferRecyclerAdapter.MyHolder> {
    private OnItemClickListener mListener;
    Context context;
    ArrayList<ProductTransferModel> arrayList;
    AlertDialog.Builder builder;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public ProductTransferRecyclerAdapter(Context context, ArrayList<ProductTransferModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_transfer_row, parent, false);
        MyHolder myHolder = new MyHolder(view, mListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        String product_from = arrayList.get(position).getFrom();
        String product_to = arrayList.get(position).getTo();
        String product_code = arrayList.get(position).getProduct_code();
        String product_req_date = arrayList.get(position).getReq_date();
        String product_quantity = arrayList.get(position).getProduct_quantity();

        holder.product_from.setText(product_from);
        holder.product_to.setText(product_to);
        holder.product_code.setText(product_code);
        holder.product_req_date.setText(product_req_date);
        holder.product_quantity.setText(product_quantity);

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "item no - " + holder.getAdapterPosition() + " Edited", Toast.LENGTH_SHORT).show();
                showEditProductTransferDialog(holder.getAdapterPosition(), product_from, product_code, product_quantity);
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

    private void showEditProductTransferDialog(int position, String product_fromSt, String product_codeSt, String product_quantitySt) {

        final Dialog dialog = new Dialog(context);
        dialog.setTitle("Edit Product");
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.product_transfer_dialog);

        final EditText product_from = dialog.findViewById(R.id.product_from);
        final EditText product_code = dialog.findViewById(R.id.product_code);
        final EditText product_quantity = dialog.findViewById(R.id.product_quantity);
        AppCompatButton btnProductTransferDialog = dialog.findViewById(R.id.btn_productTransferDialog);

        product_from.setText(product_fromSt);
        product_code.setText(product_codeSt);
        product_quantity.setText(product_quantitySt);


        btnProductTransferDialog.setOnClickListener(v -> {
            String Updated_product_fromSt = product_from.getText().toString();
            String Updated_product_codeSt = product_code.getText().toString();
            String Updated_product_quantitySt = product_quantity.getText().toString();

            editTransferProduct(position, Updated_product_fromSt, Updated_product_codeSt, Updated_product_quantitySt);
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }


    private void editTransferProduct(int position, String updated_product_fromSt, String updated_product_codeSt, String updated_product_quantitySt) {
        if (updated_product_fromSt.equals("") || updated_product_codeSt.equals("") || updated_product_quantitySt.equals("")) {
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
        TextView product_from, product_to, product_code, product_req_date, product_quantity;
        Spinner product_status;
        ImageButton btnEdit, btnDelete;

        public MyHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            product_from = itemView.findViewById(R.id.product_from);
            product_to = itemView.findViewById(R.id.product_to);
            product_req_date = itemView.findViewById(R.id.product_req_date);
            product_quantity = itemView.findViewById(R.id.product_quantity);
            product_code = itemView.findViewById(R.id.product_code);
            product_status = itemView.findViewById(R.id.product_status);

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