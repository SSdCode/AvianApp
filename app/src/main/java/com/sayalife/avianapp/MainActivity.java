package com.sayalife.avianapp;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.adapter.CardAdapter;
import com.sayalife.avianapp.model.StoresModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private CardAdapter cardAdapter;
    private ArrayList<StoresModel>storesModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeCardView();
    }

    private void initializeCardView() {
        recyclerView = findViewById(R.id.recyclerViewCards);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        storesModelArrayList = new ArrayList<>();
        cardAdapter = new CardAdapter(this, storesModelArrayList);
        recyclerView.setAdapter(cardAdapter);

        createStoreData();
    }

    private void createStoreData() {

        StoresModel storesModel = new StoresModel("VijaySales", "023343553", "New Delhi", "Delhi", "110059", "Store no. - 46, Nagar" );
        storesModelArrayList.add(storesModel);
        storesModel = new StoresModel("Sargam Sales", "122372873", "Ahmedabad", "Gujarat", "390012", "Store no. - 50, Kankaria" );
        storesModelArrayList.add(storesModel);
        storesModel = new StoresModel("Kochi Sales", "455843434", "Trivandrum", "Kerala", "603445", "Store no. - 48, Tirulli" );
        storesModelArrayList.add(storesModel);
        storesModel = new StoresModel("Banerjee Sales", "23434343", "Kolkata", "West Bengal", "839404", "Store no. - 59, Howrah" );
        storesModelArrayList.add(storesModel);
        storesModel = new StoresModel("Gupta Sales", "23723724", "New Delhi", "Delhi", "110043", "Store no. - 80, Vikas Nagar" );
        storesModelArrayList.add(storesModel);
        cardAdapter.notifyDataSetChanged();


    }

}