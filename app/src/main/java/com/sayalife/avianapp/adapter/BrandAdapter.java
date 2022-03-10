package com.sayalife.avianapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.sayalife.avianapp.FormActivity;
import com.sayalife.avianapp.model.BrandModel;
import com.sayalife.avianapp.R;

import java.util.ArrayList;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.ViewHolder>
{

    Context context;
    ArrayList<BrandModel> list;


    public BrandAdapter(Context context,ArrayList<BrandModel> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.brand_items,parent,false);
        ViewHolder viewholder = new ViewHolder(view);
        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        BrandModel brandModel = list.get(position);
        holder.fName.setText(brandModel.getFirstName());
        holder.lName.setText(brandModel.getLastName());
        holder.mobile.setText(brandModel.getMobile());
        holder.address.setText(brandModel.getAddress());
        holder.city.setText(brandModel.getCity());
        holder.state.setText(brandModel.getState());
        holder.pin_code.setText(brandModel.getPin_code());
        holder.company.setText(brandModel.getCompany());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilderLabelDelete = new AlertDialog.Builder(context);

                list.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), list.size());
            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context , FormActivity.class);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return list.size();
    }



    public static class ViewHolder  extends RecyclerView.ViewHolder {
        TextView fName , lName , mobile ,address , city , state , pin_code , company;
         ImageButton delete , edit ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            fName = itemView.findViewById(R.id.brandEditFirstName);
            lName = itemView.findViewById(R.id.brandEditLastName);
            mobile = itemView.findViewById(R.id.brandEditMobile);
            address = itemView.findViewById(R.id.brandEditAddress);
            city = itemView.findViewById(R.id.brandEditCity);
            state = itemView.findViewById(R.id.brandEditState);
            pin_code = itemView.findViewById(R.id.brandEditPin);
            company = itemView.findViewById(R.id.brandEditCompany);
            delete = itemView.findViewById(R.id.brandDelete);
            edit = itemView.findViewById(R.id.brandBtn);
        }






    }


}
