package com.sayalife.avianapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.sayalife.avianapp.adapter.UserRVAdapter;
import com.sayalife.avianapp.database.DatabaseHelper;

public class UsersActivity extends AppCompatActivity {
    UserRVAdapter userRVAdapter;
    RecyclerView recyclerView;
    Spinner userTypeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        recyclerView = findViewById(R.id.usersRecyclerView);
        userTypeSpinner = findViewById(R.id.userTypeSpinner);
        DatabaseHelper helper = new DatabaseHelper(getApplicationContext());
        userRVAdapter = new UserRVAdapter(helper.getAllUsersData(), getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(userRVAdapter);

        userRVAdapter.setOnItemClickListener(position -> {
            Integer userID = userRVAdapter.getUserId(position);
            Toast.makeText(getApplicationContext(), "user id = " + userID, Toast.LENGTH_SHORT).show();
        });

        userTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String userType = adapterView.getItemAtPosition(i).toString();
                switch (userType) {
                    case "All Users":
                        userRVAdapter.setUserList(helper.getAllUsersData());
                        break;
                    case "Super Admin":
                        userRVAdapter.setUserList(helper.getUsersByRoleId(1));
                        break;
                    case "Store Admin":
                        userRVAdapter.setUserList(helper.getUsersByRoleId(2));
                        break;
                    case "Store Manager":
                        userRVAdapter.setUserList(helper.getUsersByRoleId(3));
                        break;
                    case "Store Staff":
                        userRVAdapter.setUserList(helper.getUsersByRoleId(4));
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

    }

}