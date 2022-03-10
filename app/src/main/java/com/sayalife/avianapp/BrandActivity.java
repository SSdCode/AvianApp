package com.sayalife.avianapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sayalife.avianapp.adapter.BrandAdapter;
import com.sayalife.avianapp.model.BrandModel;

import java.util.ArrayList;

public class BrandActivity extends AppCompatActivity {
     RecyclerView recyclerView;
    ArrayList<BrandModel> list;
    BrandAdapter adapter;
    ImageButton edit , delete ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brand);




        // Recycler view using Brand Model class and BrandAdapter class
       recyclerView = findViewById(R.id.brandRecyclerView);

              brandData();
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        BrandAdapter brandAdapter = new BrandAdapter(this,list);
        recyclerView.setAdapter(brandAdapter);

        // fab onclick listener , when clicked will navigate to form filling page.
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BrandActivity.this , com.sayalife.avianapp.FormActivity.class);
                startActivity(intent);
            }
        });











    }






    private void brandData() {
        // Dummy data
        list = new ArrayList<>();

        list.add(new BrandModel("Ashwin", "Pillai", "9998654660", "Siddhart Apartment" , "Vadodara" , "Gujarat" , "390019" , "Maxlink" ));
        list.add(new BrandModel("Tarun", "Patel", "90201837483", "Vinayak Residency" , "Kutch" , "Gujarat" , "390010" , "Minlink"));
        list.add(new BrandModel("Somesh", "Joshi", "102838492", "Sunshine Complex" , "Morbi" , "Gujarat" , "390090" , "Softlink"));
        list.add(new BrandModel("Mohit", "Bhat", "102839201", "Apex complex" , "Anand" , "Gujarat" , "390826" , "Hardlink"));
        list.add(new BrandModel("Sameer", "Dubey", "1902901000", "Gidc colony" , "Kalol" , "Gujarat" , "380991" , "Bigling"));
        list.add(new BrandModel("Nayan", "Jalela", "9102039100", "Sahyog Buildings" , "Bhavnagar" , "Gujarat" , "365700" , "Smalling"));
        list.add(new BrandModel("Pratik", "Champaneri", "109902001", "Maruti Nandan" , "Bhuj" , "Gujarat" , "356800" , "Calling"));
    }
}