package com.sayalife.avianapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sayalife.avianapp.adapter.SupplyAdapter;
import com.sayalife.avianapp.model.SupplyModel;

import java.util.ArrayList;

public class SupplierActivity extends AppCompatActivity {

    RecyclerView supplyRecyclerView;
    ArrayList<SupplyModel> supplyList;
    SupplyAdapter supplyAdapter;
    ImageButton supplyEdit , supplyDelete ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supplier);

            // Recycler view using Supply Model class and SupplyAdapter class
            supplyRecyclerView = findViewById(R.id.supplyRecyclerView);

            supplyData();
            supplyRecyclerView.setHasFixedSize(true);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        supplyRecyclerView.setLayoutManager(linearLayoutManager);

            supplyAdapter = new SupplyAdapter(this,supplyList);
        supplyRecyclerView.setAdapter(supplyAdapter);

            // fab onclick listener , when clicked will navigate to form filling page.
            FloatingActionButton fab = findViewById(R.id.supplyFloatingActionButton);
            fab.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(SupplierActivity.this , com.sayalife.avianapp.FormActivity.class);
                    startActivity(intent);
                }
            });

        }
    private void supplyData() {
        // Dummy data
        supplyList = new ArrayList<>();

        supplyList.add(new SupplyModel("Ashwin", "Pillai", "9998654660", "Siddhart Apartment" , "Vadodara" , "Gujarat" , "390019" , "Maxlink" ));
        supplyList.add(new SupplyModel("Tarun", "Patel", "90201837483", "Vinayak Residency" , "Kutch" , "Gujarat" , "390010" , "Minlink"));
        supplyList.add(new SupplyModel("Somesh", "Joshi", "102838492", "Sunshine Complex" , "Morbi" , "Gujarat" , "390090" , "Softlink"));
        supplyList.add(new SupplyModel("Mohit", "Bhat", "102839201", "Apex complex" , "Anand" , "Gujarat" , "390826" , "Hardlink"));
        supplyList.add(new SupplyModel("Sameer", "Dubey", "1902901000", "Gidc colony" , "Kalol" , "Gujarat" , "380991" , "Bigling"));
        supplyList.add(new SupplyModel("Nayan", "Jalela", "9102039100", "Sahyog Buildings" , "Bhavnagar" , "Gujarat" , "365700" , "Smalling"));
        supplyList.add(new SupplyModel("Pratik", "Champaneri", "109902001", "Maruti Nandan" , "Bhuj" , "Gujarat" , "356800" , "Calling"));
    }
}