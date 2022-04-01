package com.sayalife.avianapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sayalife.avianapp.R;
import com.sayalife.avianapp.model.GridCountModel;

import java.util.ArrayList;

public class GridCountAdapter extends BaseAdapter {

    Context context;
    ArrayList<GridCountModel> arrayList;

    public GridCountAdapter(Context context, ArrayList<GridCountModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = LayoutInflater.from(context).inflate(R.layout.item_grid_count, viewGroup, false);
        ImageView imageView = view.findViewById(R.id.icon);
        TextView title = view.findViewById(R.id.title);
        TextView count = view.findViewById(R.id.count);

        imageView.setImageResource(arrayList.get(i).getImg());
        title.setText(arrayList.get(i).getTitle());
        count.setText(String.valueOf(arrayList.get(i).getCount()));


        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
