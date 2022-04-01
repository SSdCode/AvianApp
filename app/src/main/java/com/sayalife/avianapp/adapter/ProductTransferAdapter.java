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
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.R;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.ProductTransferModel;

import java.util.ArrayList;
import java.util.List;

public class ProductTransferAdapter extends RecyclerView.Adapter<ProductTransferAdapter.MyHolder> {
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

    public ProductTransferAdapter(Context context, ArrayList<ProductTransferModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_product_transfer, parent, false);
        MyHolder myHolder = new MyHolder(view, mListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        String product_from = databaseHelper.getStoreNameById(arrayList.get(position).getFrom_store_id());
        String product_to = databaseHelper.getStoreNameById(arrayList.get(position).getTo_store_id());
        String product_name = databaseHelper.getProductNameById(arrayList.get(position).getProduct_id());
//        int product_id = arrayList.get(position).getProduct_id();
        String product_req_date = arrayList.get(position).getReq_date();
        int product_quantity = arrayList.get(position).getProduct_quantity();
        int product_status = arrayList.get(position).getProduct_status();


        holder.product_from.setText(product_from);
        holder.product_to.setText(product_to);
        holder.product_name.setText(String.valueOf(product_name));
        holder.product_req_date.setText(product_req_date);
        holder.product_quantity.setText(String.valueOf(product_quantity));
        holder.product_status.setText(getStatus(product_status));

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditProductTransferDialog(arrayList.get(holder.getAdapterPosition()).getId(),
                        arrayList.get(holder.getAdapterPosition()).getFrom_store_id(),
                        arrayList.get(holder.getAdapterPosition()).getTo_store_id(),
                        arrayList.get(holder.getAdapterPosition()).getProduct_id(),
                        product_req_date, product_quantity, product_status);
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
                                databaseHelper.deleteProductTransfer(arrayList.get(holder.getAdapterPosition()).getId());
                                arrayList.clear();
                                arrayList.addAll(databaseHelper.getAllProductTransfer());
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

    private String getStatus(int product_status) {
        if (product_status == 0) {
            return "Pending";
        }
        if (product_status == 1) {
            return "Approved";
        }
        return "Rejected";
    }

    private void showEditProductTransferDialog(int id, int product_from, int product_to, int product_id, String product_req_date, int product_quantity, int product_status) {

        final Dialog dialog = new Dialog(context);
        dialog.setTitle("Edit Product");
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_product_transfer);

        final Spinner sp_product_from = dialog.findViewById(R.id.product_from);
        final Spinner sp_product_to = dialog.findViewById(R.id.product_to);
        final Spinner product_name = dialog.findViewById(R.id.product_name);
        final AppCompatSpinner sp_product_status = dialog.findViewById(R.id.product_status);
        final EditText et_product_quantity = dialog.findViewById(R.id.product_quantity);
        Button btnProductTransferDialog = dialog.findViewById(R.id.btn_productTransferDialog);

        DatabaseHelper db = new DatabaseHelper(context);
        List<String> labels = db.getAllStoreNames();
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, labels);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp_product_from.setAdapter(dataAdapter);
        sp_product_to.setAdapter(dataAdapter);

        List<String> products = db.getAllProductNames();
        ArrayAdapter<String> dataAdapterProduct = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_item, products);
        dataAdapterProduct.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        product_name.setAdapter(dataAdapterProduct);


        sp_product_from.setSelection(dataAdapter.getPosition(db.getStoreName(product_from)));
        sp_product_to.setSelection(dataAdapter.getPosition(db.getStoreName(product_to)));
        product_name.setSelection(dataAdapterProduct.getPosition(db.getProductNameById(product_id)));

        et_product_quantity.setText(String.valueOf(product_quantity));
        sp_product_status.setSelection(product_status);

        Log.d("Data", "showEditProductTransferDialog: " + product_status);
        btnProductTransferDialog.setOnClickListener(v -> {
            int product_from_ = db.getStoreId(sp_product_from.getSelectedItem().toString());
            int product_to_ = db.getStoreId(sp_product_to.getSelectedItem().toString());
            int product_id_ = db.getProductId(product_name.getSelectedItem().toString());
            int product_quantity_ = Integer.parseInt(et_product_quantity.getText().toString());
            int product_status_ = sp_product_status.getSelectedItemPosition();

            if (product_from_ == 0 || product_to_ == 0 || product_id_ == 0 || product_quantity_ == 0) {
                Toast.makeText(context, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else {
                DatabaseHelper databaseHelper = new DatabaseHelper(context);
                databaseHelper.updateProductTransfer(new ProductTransferModel(id, product_from_, product_to_, product_id_, product_req_date, product_status_, product_quantity_));
                arrayList.clear();
                arrayList.addAll(databaseHelper.getAllProductTransfer());
                notifyDataSetChanged();
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
        TextView product_name, product_req_date, product_quantity;
        TextView product_from, product_to;
        TextView product_status;
        ImageButton btnEdit, btnDelete;

        public MyHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            product_from = itemView.findViewById(R.id.product_from);
            product_to = itemView.findViewById(R.id.product_to);
            product_req_date = itemView.findViewById(R.id.product_req_date);
            product_quantity = itemView.findViewById(R.id.product_quantity);
            product_name = itemView.findViewById(R.id.product_name);
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