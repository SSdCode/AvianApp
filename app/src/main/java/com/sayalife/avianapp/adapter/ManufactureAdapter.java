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
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.R;
import com.sayalife.avianapp.database.DatabaseHelper;
import com.sayalife.avianapp.model.ManufactureModel;

import java.util.ArrayList;

public class ManufactureAdapter extends RecyclerView.Adapter<ManufactureAdapter.MyHolder> {
    private ManufactureAdapter.OnItemClickListener mListener;
    Context context;
    ArrayList<ManufactureModel> arrayList;
    AlertDialog.Builder builder;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(ManufactureAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public ManufactureAdapter(Context context, ArrayList<ManufactureModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ManufactureAdapter.MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row_manufacture, parent, false);
        ManufactureAdapter.MyHolder myHolder = new ManufactureAdapter.MyHolder(view, mListener);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ManufactureAdapter.MyHolder holder, int position) {
        ManufactureModel manufactureModel = arrayList.get(position);
        holder.fName.setText(manufactureModel.getFirstName());
        holder.lName.setText(manufactureModel.getLastName());
        holder.company_name.setText(manufactureModel.getCompany());
        holder.mNo.setText(manufactureModel.getMobile());
        holder.city.setText(manufactureModel.getCity());
        holder.state.setText(manufactureModel.getState());
        holder.pinCode.setText(manufactureModel.getPin_code());
        holder.address.setText(manufactureModel.getAddress());

        holder.btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Edit Manufacture
                editManufacture(manufactureModel.getId(), manufactureModel.getFirstName(), manufactureModel.getLastName(), manufactureModel.getCompany(), manufactureModel.getMobile(), manufactureModel.getCity(), manufactureModel.getState(), manufactureModel.getPin_code(), manufactureModel.getAddress());
            }
        });
        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Open Delete Dialog Box
                deleteManufacture(manufactureModel.getId());
            }
        });
    }

    private void deleteManufacture(int manufactureId) {
        builder = new AlertDialog.Builder(context);
        builder.setMessage("Are you sure want to delete?").setTitle("Delete");

        builder.setMessage("Are you sure want to delete?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        try {
                        DatabaseHelper databaseHelper = new DatabaseHelper(context);
                        databaseHelper.deleteManufacturer(manufactureId);

                        ArrayList<ManufactureModel> items = databaseHelper.getAllManufacturer();
                        arrayList.clear();
                        arrayList.addAll(items);
                        notifyDataSetChanged();
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
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

    private void editManufacture(int id, String firstName, String lastName, String
            company, String mobile, String city, String state, String pin_code, String address) {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_manufacture);

        EditText et_fName = dialog.findViewById(R.id.fName);
        EditText et_lName = dialog.findViewById(R.id.lName);
        EditText et_company_name = dialog.findViewById(R.id.company_name);
        EditText et_mNo = dialog.findViewById(R.id.mNo);
        EditText et_city = dialog.findViewById(R.id.city);
        EditText et_state = dialog.findViewById(R.id.state);
        EditText et_pinCode = dialog.findViewById(R.id.pinCode);
        EditText et_address = dialog.findViewById(R.id.address);
        Button submitButton = dialog.findViewById(R.id.button_manufacture_dialog);

        et_fName.setText(firstName);
        et_lName.setText(lastName);
        et_company_name.setText(company);
        et_mNo.setText(mobile);
        et_city.setText(city);
        et_state.setText(state);
        et_pinCode.setText(pin_code);
        et_address.setText(address);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper db = new DatabaseHelper(context);
                db.updateManufacturer(String.valueOf(id), et_fName.getText().toString(), et_lName.getText().toString(), et_company_name.getText().toString(), et_mNo.getText().toString(), et_city.getText().toString(), et_state.getText().toString(), et_pinCode.getText().toString(), et_address.getText().toString());
                arrayList.clear();
                arrayList.addAll(db.getAllManufacturer());
                notifyDataSetChanged();
                dialog.dismiss();
            }
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

    public static class MyHolder extends RecyclerView.ViewHolder {
        TextView fName, lName, company_name, mNo, city, state, pinCode, address;
        ImageButton btnEdit, btnDelete;

        public MyHolder(@NonNull View itemView, final ManufactureAdapter.OnItemClickListener listener) {
            super(itemView);

            fName = itemView.findViewById(R.id.fName);
            lName = itemView.findViewById(R.id.lName);
            company_name = itemView.findViewById(R.id.company_name);
            mNo = itemView.findViewById(R.id.mNo);
            city = itemView.findViewById(R.id.city);
            state = itemView.findViewById(R.id.state);
            pinCode = itemView.findViewById(R.id.pinCode);
            address = itemView.findViewById(R.id.address);

            btnEdit = itemView.findViewById(R.id.btnEdit);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if (listener != null) {
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
    }
}