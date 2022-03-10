package com.sayalife.avianapp.coupon.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.R;
import com.sayalife.avianapp.coupon.model.CouponModel;

import java.util.ArrayList;
import java.util.Objects;

public class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.Viewholder> {
    Context context;
    ArrayList<CouponModel> couponModel;
    private OnItemClickListener listener;

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public CouponAdapter(Context context, ArrayList<CouponModel> couponModel) {
        this.context = context;
        this.couponModel = couponModel;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.coupon_list_item, parent, false);
        return new Viewholder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {
        String code = couponModel.get(position).getCode();
        int value = couponModel.get(position).getValue();
        String valid_from = couponModel.get(position).getValid_from();
        String valid_till = couponModel.get(position).getValid_till();
        String type = couponModel.get(position).getType();

        holder.code.setText("Code: " + code);
        holder.value.setText("Value: " + value);
        holder.type.setText("Type: " + type);
        holder.valid_from.setText("Valid from: " + valid_from);
        holder.valid_till.setText("Valid till: " + valid_till);

        holder.edit_btn.setOnClickListener(view -> showEditCouponDialog(holder.getAdapterPosition(), code, value, type, valid_from, valid_till));

        holder.delete_btn.setOnClickListener((view -> {
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.coupon_delete_dialog);

            final AppCompatTextView delete_dialog = dialog.findViewById(R.id.delete_dialog);
            final AppCompatTextView delete_msg = dialog.findViewById(R.id.delete_msg);
            final AppCompatButton btn_yes = dialog.findViewById(R.id.btn_yes);
            final AppCompatButton btn_no = dialog.findViewById(R.id.btn_no);

            btn_yes.setOnClickListener(view1 -> dialog.dismiss());
            btn_no.setOnClickListener(view1 -> dialog.dismiss());

            dialog.show();
            Window window = dialog.getWindow();
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        }));
    }

    @SuppressLint("SetTextI18n")
    private void showEditCouponDialog(int adapterPosition, String code, int value, String type, String valid_from, String valid_till) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.coupon_edit_dialog);

        final AppCompatTextView c_code = dialog.findViewById(R.id.c_code);
        final AppCompatTextView c_valid_from = dialog.findViewById(R.id.c_valid_from);
        final AppCompatTextView c_valid_till = dialog.findViewById(R.id.c_valid_till);
        final AppCompatTextView c_type = dialog.findViewById(R.id.c_type);
        final AppCompatTextView c_value = dialog.findViewById(R.id.c_value);
        final AppCompatEditText et_code = dialog.findViewById(R.id.et_code);
        final AppCompatEditText et_valid_from = dialog.findViewById(R.id.et_valid_from);
        final AppCompatEditText et_valid_till = dialog.findViewById(R.id.et_valid_till);
        final AppCompatEditText et_type = dialog.findViewById(R.id.et_type);
        final AppCompatEditText et_value = dialog.findViewById(R.id.et_value);
        AppCompatButton btn_save = dialog.findViewById(R.id.btn_save);

        et_code.setText("" + code);
        et_valid_from.setText("" + valid_from);
        et_valid_till.setText("" + valid_till);
        et_type.setText("" + type);
        et_value.setText("" + value);

        btn_save.setOnClickListener(view -> {
            String code1 = Objects.requireNonNull(et_code.getText()).toString();
            String valid_from1 = Objects.requireNonNull(et_valid_from.getText()).toString();
            String valid_till1 = Objects.requireNonNull(et_valid_till.getText()).toString();
            int value1 = et_value.getScrollY();
            String type1 = Objects.requireNonNull(et_type.getText()).toString();
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public int getItemCount() {
        return couponModel.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        AppCompatTextView code, valid_from, valid_till, type, value;
        AppCompatButton edit_btn, delete_btn;

        public Viewholder(@NonNull View itemView) {
            super(itemView);
            code = itemView.findViewById(R.id.code);
            valid_from = itemView.findViewById(R.id.valid_from);
            valid_till = itemView.findViewById(R.id.valid_till);
            type = itemView.findViewById(R.id.type);
            value = itemView.findViewById(R.id.value);
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
