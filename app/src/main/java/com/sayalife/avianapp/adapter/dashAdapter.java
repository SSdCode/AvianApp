package com.sayalife.avianapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.R;
import com.sayalife.avianapp.model.stockModel;

import java.util.List;

public class dashAdapter extends RecyclerView.Adapter<dashAdapter.ViewHolder> {

    Context context;
    List<stockModel> stock_list;

    public dashAdapter(Context context, List<stockModel> stock_list) {
        this.context = context;
        this.stock_list = stock_list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(stock_list != null && stock_list.size()>0)
        {
            stockModel model = stock_list.get(position);
            holder.code.setText(model.getCode());
            holder.category.setText(model.getCategory());
            holder.name.setText(model.getName());
            holder.specification.setText(model.getSpecification());
            holder.remains.setText(model.getRemains());
            holder.brand.setText(model.getBrand());
        }


    }

    @Override
    public int getItemCount() {
        return stock_list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView code, category, name, specification, remains, brand;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            code = itemView.findViewById(R.id.code);
            category = itemView.findViewById(R.id.category);
            name = itemView.findViewById(R.id.name);
            specification = itemView.findViewById(R.id.specification);
            remains = itemView.findViewById(R.id.ramains);
            brand = itemView.findViewById(R.id.brand);
        }
    }
}
