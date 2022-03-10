package com.sayalife.avianapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.R;
import com.sayalife.avianapp.model.StoresModel;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.StoreHolder> {

    private Context context;
    private ArrayList<StoresModel> storesModels;

    public CardAdapter(Context context, ArrayList<StoresModel> storesModels) {
        this.context = context;
        this.storesModels = storesModels;
    }

    @NonNull
    @Override
    public StoreHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_card_view, parent, false);
        return new StoreHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreHolder holder, int position) {
        StoresModel storesModel = storesModels.get(position);
        holder.setDetails(storesModel);
    }

    @Override
    public int getItemCount() {
        return storesModels.size();
    }

    //ViewHolder

    class StoreHolder extends RecyclerView.ViewHolder{

        private TextView store, contact, city, state, pin, add;


        StoreHolder(View itemView){
            super(itemView);

            store = itemView.findViewById(R.id.store);
            contact = itemView.findViewById(R.id.contact);
            city = itemView.findViewById(R.id.supplyCity);
            state = itemView.findViewById(R.id.supplyState);
            pin = itemView.findViewById(R.id.pin);
            add = itemView.findViewById(R.id.add);

        }

        void setDetails(StoresModel storesModel){
            store.setText("Store : " + storesModel.getStoreName());
            contact.setText("Contact No. : " + storesModel.getContactNum());
            city.setText("City : " + storesModel.getCityName());
            state.setText("State : " + storesModel.getStateName());
            pin.setText("PinCode : " + storesModel.getPinCode());
            add.setText("Address : " + storesModel.getAddress());

        }
    }
}
