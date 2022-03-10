package com.sayalife.avianapp.purchaseproductlist.adapter;


import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.R;
import com.sayalife.avianapp.purchaseproductlist.model.PurchaseProductListModel;

import java.util.ArrayList;
import java.util.Objects;

public class PurchaseProductAdapter extends RecyclerView.Adapter<PurchaseProductAdapter.Viewholder> {
    Context context;
    ArrayList<PurchaseProductListModel> productListModel;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public PurchaseProductAdapter(Context context, ArrayList<PurchaseProductListModel> productListModel) {
        this.context = context;
        this.productListModel = productListModel;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.product_list_item, parent, false);
        return new Viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        int batchID = productListModel.get(position).getBatchID();
        String brand = productListModel.get(position).getBrand();
        String category = productListModel.get(position).getCategory();
        String specification = productListModel.get(position).getSpecification();
        int quantity = productListModel.get(position).getQuantity();
        int price = productListModel.get(position).getPrice();

        holder.batchID.setText("Batch ID: " + batchID);
        holder.brand.setText("Brand: " + brand);
        holder.category.setText("Brand: " + category);
        holder.specification.setText("Brand: " + specification);
        holder.quantity.setText("Brand: " + quantity);
        holder.price.setText("Brand: " + price);

        holder.edit_btn.setOnClickListener(view -> showEditProductDialog(holder.getAdapterPosition(), batchID, brand, category, specification, quantity, price));

        holder.delete_btn.setOnClickListener((view -> Toast.makeText(context, "Batch ID - " + holder.getAdapterPosition() + " deleted", Toast.LENGTH_SHORT).show()));
    }

    @SuppressLint("SetTextI18n")
    private void showEditProductDialog(int adapterPosition, int batchID, String brand, String category, String specification, int quantity, int price) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.product_edit_dialog);

        final AppCompatTextView p_brand = dialog.findViewById(R.id.p_brand);
        final AppCompatTextView p_category = dialog.findViewById(R.id.p_category);
        final AppCompatTextView p_specification = dialog.findViewById(R.id.p_specification);
        final AppCompatTextView p_quantity = dialog.findViewById(R.id.p_quantity);
        final AppCompatTextView p_price = dialog.findViewById(R.id.p_price);
        final AppCompatEditText et_brand = dialog.findViewById(R.id.et_brand);
        final AppCompatEditText et_category = dialog.findViewById(R.id.et_category);
        final AppCompatEditText et_specification = dialog.findViewById(R.id.et_specification);
        final AppCompatEditText et_quantity = dialog.findViewById(R.id.et_quantity);
        final AppCompatEditText et_price = dialog.findViewById(R.id.et_price);
        AppCompatButton btn_save = dialog.findViewById(R.id.btn_save);

        et_brand.setText("" + brand);
        et_category.setText("" + category);
        et_specification.setText("" + specification);
        et_quantity.setText("" + quantity);
        et_price.setText("" + price);

        btn_save.setOnClickListener(view -> {
            String brand1 = Objects.requireNonNull(et_brand.getText()).toString();
            String category1 = Objects.requireNonNull(et_category.getText()).toString();
            String specification1 = Objects.requireNonNull(et_specification.getText()).toString();
            int quantity1 = et_quantity.getScrollY();
            int price1 = et_price.getScrollY();
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int getItemCount() {
        return productListModel.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        AppCompatTextView batchID, brand, category, specification, quantity, price;
        AppCompatButton edit_btn, delete_btn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            batchID = itemView.findViewById(R.id.batchID);
            brand = itemView.findViewById(R.id.brand);
            category = itemView.findViewById(R.id.category);
            specification = itemView.findViewById(R.id.specification);
            quantity = itemView.findViewById(R.id.quantity);
            price = itemView.findViewById(R.id.price);
            edit_btn = itemView.findViewById(R.id.edit_btn);
            delete_btn = itemView.findViewById(R.id.delete_btn);

            itemView.setOnClickListener(view -> {
                int position = getAdapterPosition();
                if (listener != null) {
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            });
        }
    }
}
