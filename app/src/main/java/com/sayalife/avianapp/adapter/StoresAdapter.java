package com.sayalife.avianapp.adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.R;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.StoresModel;

import java.util.ArrayList;

public class StoresAdapter extends RecyclerView.Adapter<StoresAdapter.ViewHolder> {

    private Context context;
    private ArrayList<StoresModel> arrayList;
    AlertDialog.Builder builder;

    public StoresAdapter(Context context, ArrayList<StoresModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_store, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        StoresModel storesModel = arrayList.get(position);
        holder.store_name.setText(storesModel.getStoreName());
        holder.store_name.setText(storesModel.getStoreName());
        holder.licenceNo.setText(storesModel.getLicenceNo());
        holder.contactNum.setText(storesModel.getContactNum());
        holder.cityName.setText(storesModel.getCityName());
        holder.pinCode.setText(storesModel.getPinCode());
        holder.address.setText(storesModel.getAddress());

        builder = new AlertDialog.Builder(context);
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setMessage("Are you sure want to delete?").setTitle("Delete");

                //Setting message manually and performing action on button click
                builder.setMessage("Are you sure want to delete?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

//                                arrayList.remove(holder.getAdapterPosition());
//                                notifyItemRemoved(holder.getAdapterPosition());

                                try {
                                    DatabaseHelper databaseHelper = new DatabaseHelper(context);
                                    databaseHelper.deleteStore(arrayList.get(holder.getAdapterPosition()).getStoreId());

                                    ArrayList<StoresModel> items = databaseHelper.getAllStoresData();
                                    arrayList.clear();
                                    arrayList.addAll(items);
                                    notifyDataSetChanged();

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                dialog.dismiss();
                                Toast.makeText(context, "Item Deleted", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(context, "Delete cancel", Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete");
                alert.show();
            }
        });
        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showEditStoreDialog(holder.getAdapterPosition(),
                        storesModel.getStoreName(),
                        storesModel.getLicenceNo(),
                        storesModel.getContactNum(),
                        storesModel.getCityName(),
                        storesModel.getPinCode(),
                        storesModel.getAddress());
            }
        });
    }

    private void showEditStoreDialog(int adapterPosition, String storeName, String licenceNo, String contactNum, String cityName, String pinCode, String address) {
        final Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_store);

        final EditText et_store_name = dialog.findViewById(R.id.et_store_name);
        final EditText et_license_number = dialog.findViewById(R.id.et_license_number);
        final EditText et_contactNum = dialog.findViewById(R.id.et_contactNum);
        final EditText et_cityName = dialog.findViewById(R.id.et_cityName);
        final EditText et_pinCode = dialog.findViewById(R.id.et_pinCode);
        final EditText et_address = dialog.findViewById(R.id.et_address);
        Button submitButton = dialog.findViewById(R.id.button_store_submit);

        et_store_name.setText(storeName);
        et_license_number.setText(licenceNo);
        et_contactNum.setText(contactNum);
        et_cityName.setText(cityName);
        et_pinCode.setText(pinCode);
        et_address.setText(address);

        submitButton.setOnClickListener(v -> {
            DatabaseHelper db = new DatabaseHelper(context);
//            int id, int userId, String storeName, String licenceNumber, String contactNumber, String city, int pinCode, String address
            db.updateStore(arrayList.get(adapterPosition).getStoreId(),
                    arrayList.get(adapterPosition).getUserId(),
                    et_store_name.getText().toString(),
                    et_license_number.getText().toString(),
                    et_contactNum.getText().toString(),
                    et_cityName.getText().toString(),
                    et_pinCode.getText().toString(),
                    et_address.getText().toString());

            ArrayList<StoresModel> items = db.getAllStoresData();
            arrayList.clear();
            arrayList.addAll(items);
            notifyDataSetChanged();

            dialog.dismiss();
        });

        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView store_name, licenceNo, contactNum, cityName, pinCode, address;
        ImageButton btnEdit, btnDelete;

        ViewHolder(View itemView) {
            super(itemView);

            store_name = itemView.findViewById(R.id.store_name);
            licenceNo = itemView.findViewById(R.id.licenceNo);
            contactNum = itemView.findViewById(R.id.contactNum);
            cityName = itemView.findViewById(R.id.cityName);
            pinCode = itemView.findViewById(R.id.pinCode);
            address = itemView.findViewById(R.id.address);
            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

        }
    }
}
