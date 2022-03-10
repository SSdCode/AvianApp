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
import com.sayalife.avianapp.model.SupplyModel;
import com.sayalife.avianapp.R;

import java.util.ArrayList;

public class SupplyAdapter extends RecyclerView.Adapter<SupplyAdapter.ViewHolder> {

    Context context;
    ArrayList<SupplyModel> list;

    public SupplyAdapter(Context context, ArrayList<SupplyModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.supply_items,parent,false);
        ViewHolder SupplyViewHolder = new ViewHolder(view);
        return SupplyViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SupplyModel supplyModel = list.get(position);
        holder.SfName.setText(supplyModel.getFirstName());
        holder.SlName.setText(supplyModel.getLastName());
        holder.Smobile.setText(supplyModel.getMobile());
        holder.Saddress.setText(supplyModel.getAddress());
        holder.Scity.setText(supplyModel.getCity());
        holder.Sstate.setText(supplyModel.getState());
        holder.Spin_code.setText(supplyModel.getPin_code());
        holder.Scompany.setText(supplyModel.getCompany());

        holder.Sdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilderLabelDelete = new AlertDialog.Builder(context);

                list.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyItemRangeChanged(holder.getAdapterPosition(), list.size());
            }
        });
        holder.Sedit.setOnClickListener(new View.OnClickListener() {
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView SfName , SlName , Smobile ,Saddress , Scity , Sstate , Spin_code , Scompany;
        ImageButton Sdelete , Sedit ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            SfName = itemView.findViewById(R.id.supplyEditFirstName);
            SlName = itemView.findViewById(R.id.supplyEditLastName);
            Smobile = itemView.findViewById(R.id.supplyEditMobile);
            Saddress = itemView.findViewById(R.id.supplyEditAddress);
            Scity = itemView.findViewById(R.id.supplyEditCity);
            Sstate = itemView.findViewById(R.id.supplyEditState);
            Spin_code = itemView.findViewById(R.id.supplyEditPin);
            Scompany = itemView.findViewById(R.id.supplyEditCompany);
            Sdelete = itemView.findViewById(R.id.supplyDelete);
            Sedit = itemView.findViewById(R.id.supplyBtn);


        }
    }
}
