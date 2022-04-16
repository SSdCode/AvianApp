package com.sayalife.avianapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;


public class MainActivity extends AppCompatActivity {
    CardView btn_dashboard, btn_stores, btn_bank_details, btn_expenses_types,
            btn_users, btn_manufacturers, btn_expenses, btn_products,
            btn_product_purchase, btn_revised_purchase_products, btn_dead_stock,
            btn_product_transfer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_users = findViewById(R.id.btn_users);
        btn_dashboard = findViewById(R.id.btn_dashboard);
        btn_stores = findViewById(R.id.btn_stores);
        btn_manufacturers = findViewById(R.id.btn_manufacturers);
        btn_expenses_types = findViewById(R.id.btn_expenses_types);
        btn_expenses = findViewById(R.id.btn_expenses);
        btn_products = findViewById(R.id.btn_products);
        btn_product_purchase = findViewById(R.id.btn_product_purchase);
        btn_product_transfer = findViewById(R.id.btn_product_transfer);
        btn_bank_details = findViewById(R.id.btn_bank_details);
        btn_revised_purchase_products = findViewById(R.id.btn_revised_purchase_products);
        btn_dead_stock = findViewById(R.id.btn_dead_stock);

        SharedPreferences prefs = getSharedPreferences("LoginDetails", MODE_PRIVATE);
        int roleId = prefs.getInt("roleId", 0);
        Log.d("MainActivity", "onCreate: role Id: " + roleId);
        switch (roleId) {
            case 2:
                btn_users.setVisibility(View.GONE);
                break;
            case 3:
                btn_users.setVisibility(View.GONE);
                btn_stores.setVisibility(View.GONE);
                btn_expenses_types.setVisibility(View.GONE);
                btn_bank_details.setVisibility(View.GONE);
                break;
            case 4:
                btn_users.setVisibility(View.GONE);
                btn_dashboard.setVisibility(View.GONE);
                btn_stores.setVisibility(View.GONE);
                btn_manufacturers.setVisibility(View.GONE);
                btn_expenses_types.setVisibility(View.GONE);
                btn_expenses.setVisibility(View.GONE);
                btn_bank_details.setVisibility(View.GONE);
                btn_dead_stock.setVisibility(View.GONE);
        }

        btn_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DashboardActivity.class));
            }
        });

        btn_stores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StoresActivity.class));
            }
        });

        btn_bank_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BankDetailsActivity.class));
            }
        });

        btn_expenses_types.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ExpenseTypesActivity.class));
            }
        });

        btn_users.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UsersActivity.class));
            }
        });
        btn_manufacturers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ManufactureActivity.class));
            }
        });

        btn_expenses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ExpenseActivity.class));
            }
        });
        btn_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProductActivity.class));
            }
        });

        btn_product_purchase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProductPurchaseActivity.class));
            }
        });
        btn_revised_purchase_products.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RevisedPurchaseActivity.class));
            }
        });

        btn_dead_stock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, DeadStockActivity.class));
            }
        });

        btn_product_transfer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProductTransferActivity.class));
            }
        });
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences prefs = getSharedPreferences("LoginDetails", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.clear();
                editor.apply();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.menu_logout:
                logout();
                break;
            case R.id.menu_account:
                startActivity(new Intent(MainActivity.this, AccountActivity.class));
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }
}