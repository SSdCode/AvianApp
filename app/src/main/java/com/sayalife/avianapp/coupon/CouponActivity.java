package com.sayalife.avianapp.coupon;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Window;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sayalife.avianapp.R;
import com.sayalife.avianapp.coupon.adapter.CouponAdapter;
import com.sayalife.avianapp.coupon.model.CouponModel;

import java.util.ArrayList;
import java.util.Objects;

public class CouponActivity extends AppCompatActivity {
    FloatingActionButton add_coupon;
    RecyclerView recyclerView;
    SearchView search_coupons;
    CouponAdapter adapter;
    ArrayList<CouponModel> couponModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        initUI();
        productData();
        configureRecyclerView();

        add_coupon.setOnClickListener(view -> addCoupon());
    }

    private void addCoupon() {
        final Dialog dialog = new Dialog(CouponActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.coupon_add_dialog);

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


        btn_save.setOnClickListener(view -> {
            String code1 = Objects.requireNonNull(et_code.getText()).toString();
            String valid_from1 = Objects.requireNonNull(et_valid_from.getText()).toString();
            String valid_till1 = Objects.requireNonNull(et_valid_till.getText()).toString();
            int value1 = et_value.getScrollY();
            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    private void configureRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CouponAdapter(this, couponModel);
        recyclerView.setAdapter(adapter);
    }

    private void productData() {
        couponModel = new ArrayList<>();

        couponModel.add(new CouponModel("ABC01", 10, "Amount", "01/01/2022", "4-4-2022"));
        couponModel.add(new CouponModel("ZAC01", 5, "Amount", "01/01/2022", "4-4-2022"));
        couponModel.add(new CouponModel("FGC01", 3, "Amount", "01/01/2022", "4-4-2022"));
        couponModel.add(new CouponModel("KTC01", 1, "Amount", "01/01/2022", "4-4-2022"));
    }

    private void initUI() {
        search_coupons = findViewById(R.id.search_coupons);
        recyclerView = findViewById(R.id.recyclerView);
        add_coupon = findViewById(R.id.add_coupon);
    }
}