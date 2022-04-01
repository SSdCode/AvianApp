package com.sayalife.avianapp.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.R;
import com.sayalife.avianapp.model.UsersModel;

import java.util.ArrayList;

public class UserRVAdapter extends RecyclerView.Adapter<UserRVAdapter.UsersRVViewHolder> {

    private OnItemClickListener mListener;

    ArrayList<UsersModel> modelClassList;
    private Context context;

    public void setUserList(ArrayList<UsersModel> allUsersData) {
        modelClassList = allUsersData;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public Integer getUserId(int position) {
        return modelClassList.get(position).getId();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public UserRVAdapter(ArrayList<UsersModel> objectModelClassList, Context context) {
        modelClassList = objectModelClassList;
        this.context = context;
    }

    @NonNull
    @Override
    public UsersRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_user, parent, false);
        return new UsersRVViewHolder(view, mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersRVViewHolder holder, int position) {

        UsersModel modelClass = modelClassList.get(position);
        holder.fName.setText(modelClass.getfName());
        holder.lName.setText(modelClass.getlName());
        holder.userEmail.setText(modelClass.getEmail());
        holder.phone.setText(modelClass.getPhone());
        /*
         * Roll Id
         * 1 = Super Admin
         * 2 = Store Admin
         * 3 = Store Manager
         * 4 = Store Staff
         * */
        switch (modelClass.getRoll()) {
            case "1":
                holder.roll.setText("Super Admin");
                break;
            case "2":
                holder.roll.setText("Store Admin");
                break;
            case "3":
                holder.roll.setText("Store Manager");
                break;
            case "4":
                holder.roll.setText("Store Staff");
                break;
        }

        if (modelClass.getGender().equals("Male")) {
            holder.avatarImage.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_male));
        } else {
            holder.avatarImage.setImageDrawable(context.getResources().getDrawable(R.drawable.avatar_female));
        }
    }

    @Override
    public int getItemCount() {
        try {
            return modelClassList.size();
        } catch (Exception e) {
            Log.d("RecyclerViewAdapter", "Exception");
        }
        return 0;
    }

    public static class UsersRVViewHolder extends RecyclerView.ViewHolder {
        AppCompatImageView avatarImage;
        TextView fName, lName, userEmail, phone, roll;

        public UsersRVViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);
            avatarImage = itemView.findViewById(R.id.rvAvatar);
            fName = itemView.findViewById(R.id.rvFirstName);
            lName = itemView.findViewById(R.id.rvLastName);
            userEmail = itemView.findViewById(R.id.rvEmail);
            phone = itemView.findViewById(R.id.rvPhone);
            roll = itemView.findViewById(R.id.rvRoll);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
//                    Messagee.message(context,userName.getText().toString());
                }
            });
        }
    }
}
