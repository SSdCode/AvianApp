package com.sayalife.avianapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.adapter.dashAdapter;
import com.sayalife.avianapp.model.stockModel;

import java.util.ArrayList;
import java.util.List;

public class DashboardActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    dashAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        recyclerView = findViewById(R.id.recyclerView);
        setRecyclerView();
    }

    private void setRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new dashAdapter(this, getList());
        recyclerView.setAdapter(adapter);
    }

    private List<stockModel> getList() {
        List<stockModel> payment_list = new ArrayList<>();
        payment_list.add(new stockModel("A100","MOBILE","S22","Android 12","3","Samsung"));
        payment_list.add(new stockModel("A200","MOBILE","12 PRO","Ios 12","4","Apple"));
        payment_list.add(new stockModel("A300","MOBILE","V32","12GB RAM","2","VIVO"));
        payment_list.add(new stockModel("A400","MOBILE","13 PRO","Ios 13","2","APPLE"));
        payment_list.add(new stockModel("A500","TABLET","IPAD","Ios 10","1","Apple"));
        payment_list.add(new stockModel("A600","LAPTOP","ROG","GTX 3040","1","ASUS"));
        payment_list.add(new stockModel("A700","LAPTOP","MACBOOK","Ios","3","Apple"));

        return payment_list;


    }
}