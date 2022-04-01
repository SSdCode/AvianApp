package com.sayalife.avianapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.sayalife.avianapp.model.BankDetailsModel;
import com.sayalife.avianapp.model.DeadStockModel;
import com.sayalife.avianapp.model.ExpenseModel;
import com.sayalife.avianapp.model.ExpenseTypeModel;
import com.sayalife.avianapp.model.ManufactureModel;
import com.sayalife.avianapp.model.ProductModel;
import com.sayalife.avianapp.model.ProductPurchaseModel;
import com.sayalife.avianapp.model.ProductTransferModel;
import com.sayalife.avianapp.model.RevisedPurchaseModel;
import com.sayalife.avianapp.model.StoresModel;
import com.sayalife.avianapp.model.UsersModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    Context context;
    public static final String DATABASE_NAME = "avian_db";
    public static final int DATABASE_VERSION = 1;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate(SQLiteDatabase db) {

        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);

//        Table Roll
        String createTableRole = "CREATE TABLE Role(_id INTEGER PRIMARY KEY AUTOINCREMENT, Name nvarchar(50) NOT NULL)";
        db.execSQL(createTableRole);

        String insertRole1 = "INSERT INTO Role VALUES(1,\"super admin\")";
        db.execSQL(insertRole1);

        String insertRole2 = "INSERT INTO Role VALUES(2,\"store admin\")";
        db.execSQL(insertRole2);

        String insertRole3 = "INSERT INTO Role VALUES(3,\"store manager\")";
        db.execSQL(insertRole3);

        String insertRole4 = "INSERT INTO Role VALUES(4,\"store staff\")";
        db.execSQL(insertRole4);

//        Table AllUsers
        String createTableAllUsers = "CREATE TABLE AllUsers(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "FNAME VARCHAR(50)," +
                "LNAME VARCHAR(50)," +
                "GENDER VARCHAR(10)," +
                "EMAIL VARCHAR(255)," +
                "PHONE VARCHAR(255)," +
                "PASSWORD VARCHAR(50)," +
                "RollId INTEGER NOT NULL," +
                "FOREIGN KEY (RollId) REFERENCES Role(_id))";
        db.execSQL(createTableAllUsers);
        /*
         * Roll Id
         * 1 = Super Admin
         * 2 = Store Admin
         * 3 = Store Manager
         * 4 = Store Staff
         * */
        db.execSQL("INSERT INTO AllUsers VALUES(1,'Samir','Dubey','Male','sd@g.com','7226064677','sd@g.com',1)");
        db.execSQL("INSERT INTO AllUsers VALUES(2,'Dhruv','patel','Male','dp@g.com','7568791230','dp@g.com',2)");
        db.execSQL("INSERT INTO AllUsers VALUES(3,'Jay','Shah','Male','js@g.com','8745991230','js@g.com',3)");
        db.execSQL("INSERT INTO AllUsers VALUES(4,'Ritik','patel','Male','rp@g.com','9875691230','rp@g.com',4)");
        db.execSQL("INSERT INTO AllUsers VALUES(5,'harish','patel','Female','sd@g.com','9856791230','sd@g.com',2)");


//        Table ExpensesTypes
        String createTableExpenseType = "CREATE TABLE ExpenseType(\n" +
                "\t _id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t Name nvarchar(50) NOT NULL,\n" +
                "\t CreatedBy INTEGER NOT NULL,\n" +
                "\t CreatedDate datetime NOT NULL)";
        db.execSQL(createTableExpenseType);

        db.execSQL("INSERT INTO ExpenseType VALUES(1,'Rent',1,'" + formattedDate + "')");
        db.execSQL("INSERT INTO ExpenseType VALUES(2,'Electricity',1,'" + formattedDate + "')");
        db.execSQL("INSERT INTO ExpenseType VALUES(3,'Water',1,'" + formattedDate + "')");
        db.execSQL("INSERT INTO ExpenseType VALUES(4,'Advertising',1,'" + formattedDate + "')");
        db.execSQL("INSERT INTO ExpenseType VALUES(5,'Salary',1,'" + formattedDate + "')");


//        Table Expenses
        String createTableExpense = "CREATE TABLE Expense(\n" +
                "\t _id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t ExpenseTypeId INTEGER NOT NULL,\n" +
                "\t Amount float NOT NULL,\n" +
                "\t ExpenseDate datetime NOT NULL,\n" +
                "\t Description nvarchar(500) NOT NULL,\n" +
                "\t CreatedBy INTEGER NOT NULL,\n" +
                "\t CreatedDate datetime NOT NULL," +
                " FOREIGN KEY (ExpenseTypeId) REFERENCES ExpenseType(_id))";
        db.execSQL(createTableExpense);

        db.execSQL("INSERT INTO Expense VALUES(1,1,8000.00,'" + formattedDate + "','Rent',1,'" + formattedDate + "')");
        db.execSQL("INSERT INTO Expense VALUES(2,2,3500.00,'" + formattedDate + "','Electricity',1,'" + formattedDate + "')");
        db.execSQL("INSERT INTO Expense VALUES(3,3,1500.00,'" + formattedDate + "','Water',1,'" + formattedDate + "')");
        db.execSQL("INSERT INTO Expense VALUES(4,4,500.00,'" + formattedDate + "','Advertising',1,'" + formattedDate + "')");
        db.execSQL("INSERT INTO Expense VALUES(5,5,10000.00,'" + formattedDate + "','Salary',1,'" + formattedDate + "')");

        String createStoreDetail = "CREATE TABLE StoreDetail(\n" +
                "\t _id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t UserId INTEGER NOT NULL,\n" +
                "\t StoreName nvarchar(100) NOT NULL,\n" +
                "\t LicenceNumber nvarchar(50),\n" +
                "\t ContactNumber nvarchar(13) NOT NULL,\n" +
                "\t City nvarchar NOT NULL,\n" +
                "\t PinCode int NOT NULL,\n" +
                "\t Address nvarchar(500) NOT NULL,\n" +
                "\t CreatedBy INTEGER NOT NULL,\n" +
                "\t CreatedDate datetime NOT NULL,\n" +
                " FOREIGN KEY (UserId) REFERENCES AllUsers(_id))";
        db.execSQL(createStoreDetail);
        db.execSQL("INSERT INTO StoreDetail VALUES (1, 2, 'cloths center', '12456', '9234567891', 'Vadodara', 396300, 'diwalipura', 1, \'" + formattedDate + "\')");
        db.execSQL("INSERT INTO StoreDetail VALUES (2, 5, 'zalak Cloths', '98768', '7234567891', 'Ahmedabad', 396301, 'asarva', 1, \'" + formattedDate + "\')");
        db.execSQL("INSERT INTO StoreDetail VALUES (3, 5, 'palak Cloths', '75486', '8759667891', 'Surat', 87458, 'chandkheda', 1, \'" + formattedDate + "\')");


        String createTableManufacturerDetail = "CREATE TABLE ManufacturerDetail(\n" +
                "\t _id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t FirstName nvarchar(15) NOT NULL,\n" +
                "\t LastName nvarchar(15) NOT NULL,\n" +
                "\t CompanyName nvarchar(100) NOT NULL,\n" +
                "\t MobileNo nvarchar(12) NOT NULL,\n" +
                "\t City nvarchar(50) NOT NULL,\n" +
                "\t State nvarchar(50) NOT NULL,\n" +
                "\t PinCode int NOT NULL,\n" +
                "\t Address varchar(500) NOT NULL,\n" +
                "\t CreatedBy nvarchar(50) NOT NULL,\n" +
                "\t CreatedDate datetime NOT NULL)";
        db.execSQL(createTableManufacturerDetail);
        db.execSQL("INSERT INTO ManufacturerDetail VALUES (1, 'Rajesh', 'Kumar', 'Rajesh Cloths', '9234567891', 'Vadodara', 'Gujarat', 396300, 'diwalipura', 1, \'" + formattedDate + "\')");
        db.execSQL("INSERT INTO ManufacturerDetail VALUES (2, 'Ramesh', 'Gupta', 'Ramesh Cloths', '7845987891', 'Ahemdabad', 'Gujarat', 266300, 'Asarva', 1, \'" + formattedDate + "\')");
        db.execSQL("INSERT INTO ManufacturerDetail VALUES (3, 'Ritesh', 'Parmar', 'Ritesh Cloths', '8745896191', 'Surat', 'Gujarat', 658498, 'Nava Surat', 1, \'" + formattedDate + "\')");

        String createTableProducts = "CREATE TABLE Products(\n" +
                "\t _id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t Name nvarchar(50) NOT NULL,\n" +
                "\t Category nvarchar(50) NOT NULL,\n" +
                "\t Size nvarchar(50) NOT NULL,\n" +
                "\t Color nvarchar(50) NOT NULL,\n" +
                "\t Transferable INTEGER NOT NULL,\n" +
                "\t Returnable INTEGER NOT NULL,\n" +
                "\t ManufacturerId INTEGER NOT NULL,\n" +
                "\t Description nvarchar(500) NOT NULL,\n" +
                "\t Quantity int NOT NULL,\n" +
                "\t Price INTEGER NOT NULL,\n" +
                "\t CreatedBy INTEGER NOT NULL,\n" +
                "\t CreatedDate datetime NOT NULL,\n" +
                " FOREIGN KEY (ManufacturerId) REFERENCES ManufacturerDetail(_id))";
        db.execSQL(createTableProducts);
        db.execSQL("INSERT INTO Products VALUES (1, 'T-Shirt', 'Cloths', 'S', 'Red', 1, 1, 1, 'This is a T-Shirt', 75, 400, 1, \'" + formattedDate + "\')");
        db.execSQL("INSERT INTO Products VALUES (2, 'Shirt', 'Cloths', 'M', 'Blue', 1, 1, 1, 'This is a Shirt', 57, 200, 1, \'" + formattedDate + "\')");
        db.execSQL("INSERT INTO Products VALUES (3, 'Pant', 'Cloths', 'L', 'Black', 1, 1, 2, 'This is a Pant', 68, 600, 1, \'" + formattedDate + "\')");
        db.execSQL("INSERT INTO Products VALUES (4, 'Shoes', 'Cloths', 'S', 'Red', 1, 1, 3, 'This is a Shoes', 40, 2000, 1, \'" + formattedDate + "\')");
        db.execSQL("INSERT INTO Products VALUES (5, 'Bag', 'Cloths', 'M', 'Blue', 1, 1, 3, 'This is a Bag', 60, 750, 1, \'" + formattedDate + "\')");
        db.execSQL("INSERT INTO Products VALUES (6, 'T-Shirt', 'Cloths', 'L', 'Black', 1, 1, 2, 'This is a T-Shirt', 250, 100, 1, \'" + formattedDate + "\')");
        db.execSQL("INSERT INTO Products VALUES (7, 'Shirt', 'Cloths', 'S', 'Red', 1, 1, 2, 'This is a Shirt', 50, 600, 1, \'" + formattedDate + "\')");
        db.execSQL("INSERT INTO Products VALUES (8, 'Pant', 'Cloths', 'M', 'Blue', 1, 1, 1, 'This is a Pant', 100, 800, 1, \'" + formattedDate + "\')");
        db.execSQL("INSERT INTO Products VALUES (9, 'Shoes', 'Cloths', 'L', 'Black', 1, 1, 2, 'This is a Shoes', 1200, 100, 1, \'" + formattedDate + "\')");
        db.execSQL("INSERT INTO Products VALUES (10, 'Bag', 'Cloths', 'S', 'Red', 1, 1, 3, 'This is a Bag', 10, 800, 1, \'" + formattedDate + "\')");


        String createTableProductTransfer = "CREATE TABLE ProductTransfer(\n" +
                "\t _id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t FromStoreId INTEGER NOT NULL,\n" +
                "\t ToStoreId INTEGER NOT NULL,\n" +
                "\t ProductId INTEGER NOT NULL,\n" +
                "\t RequestDate datetime NOT NULL,\n" +
                "\t ProductStatus INTEGER NOT NULL,\n" +
                "\t ProductQuantity INTEGER NOT NULL,\n" +
                " FOREIGN KEY (FromStoreId) REFERENCES StoreDetail(_id),\n" +
                " FOREIGN KEY (ToStoreId) REFERENCES StoreDetail(_id),\n" +
                " FOREIGN KEY (ProductId) REFERENCES Products(_id))";
        db.execSQL(createTableProductTransfer);
        db.execSQL("INSERT INTO ProductTransfer VALUES (1, 1, 2, 1, \'" + formattedDate + "\', 1, 10)");
        db.execSQL("INSERT INTO ProductTransfer VALUES (2, 1, 3, 2, \'" + formattedDate + "\', 1, 10)");
        db.execSQL("INSERT INTO ProductTransfer VALUES (3, 2, 1, 3, \'" + formattedDate + "\', 1, 10)");
        db.execSQL("INSERT INTO ProductTransfer VALUES (4, 2, 3, 4, \'" + formattedDate + "\', 1, 10)");
        db.execSQL("INSERT INTO ProductTransfer VALUES (5, 3, 1, 5, \'" + formattedDate + "\', 1, 10)");

        String createTableProductPurchase = "CREATE TABLE ProductPurchase(\n" +
                "\t _id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t BrandId INTEGER NOT NULL,\n" +
                "\t Category nvarchar(50) NOT NULL,\n" +
                "\t Specification nvarchar(50) NOT NULL,\n" +
                "\t Quantity INTEGER NOT NULL,\n" +
                "\t Price INTEGER NOT NULL,\n" +    // productPrice * Quantity
                "FOREIGN KEY (BrandId) REFERENCES ManufacturerDetail(_id))";
        db.execSQL(createTableProductPurchase);
        db.execSQL("INSERT INTO ProductPurchase VALUES (1, 1, 'Mans Shirts', 'S', 15, 1005)");
        db.execSQL("INSERT INTO ProductPurchase VALUES (2, 1, 'Mans Shirts', 'M', 20, 1457)");
        db.execSQL("INSERT INTO ProductPurchase VALUES (3, 1, 'Womans Cloths', 'L', 50, 6549)");
        db.execSQL("INSERT INTO ProductPurchase VALUES (4, 1, 'Womans Cloths', 'S', 70, 1457)");
        db.execSQL("INSERT INTO ProductPurchase VALUES (5, 1, 'Womans Cloths', 'M', 30, 2456)");
        db.execSQL("INSERT INTO ProductPurchase VALUES (6, 1, 'Mans Cloths', 'L', 18, 3657)");

        String createTableRevisedPurchase = "CREATE TABLE RevisedPurchase(\n" +
                "\t _id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t brandId INTEGER NOT NULL,\n" +
                "\t description nvarchar(50) NOT NULL,\n" +
                "\t quantity INTEGER NOT NULL,\n" +
                "\t purchasePrice INTEGER NOT NULL,\n" +
                "\t revisedPrice INTEGER NOT NULL,\n" +
                "FOREIGN KEY (BrandId) REFERENCES ManufacturerDetail(_id))";
        db.execSQL(createTableRevisedPurchase);
        db.execSQL("INSERT INTO RevisedPurchase VALUES (1, 1, 'Mans Shirts', 15, 1005, 1457)");
        db.execSQL("INSERT INTO RevisedPurchase VALUES (2, 1, 'Mans Shirts', 20, 1457, 1457)");
        db.execSQL("INSERT INTO RevisedPurchase VALUES (3, 1, 'Womans Cloths', 50, 6549, 6549)");

        String createTableDeadStock = "CREATE TABLE DeadStock(\n" +
                "\t _id INTEGER PRIMARY KEY AUTOINCREMENT,\n" +
                "\t productId INTEGER NOT NULL,\n" +
                "\t quantity INTEGER NOT NULL,\n" +
                "FOREIGN KEY (productId) REFERENCES Products(_id))";
        db.execSQL(createTableDeadStock);
        db.execSQL("INSERT INTO DeadStock VALUES (1, 1, 10)");
        db.execSQL("INSERT INTO DeadStock VALUES (2, 6, 15)");
        db.execSQL("INSERT INTO DeadStock VALUES (3, 3, 4)");

        String createTableBankDetails = "CREATE TABLE BankDetails(\n" +
                "\t storeId INTEGER PRIMARY KEY,\n" +
                "\t accountHolderName nvarchar(50) NOT NULL,\n" +
                "\t accountNumber nvarchar(50) NOT NULL,\n" +
                "\t bankName nvarchar(50) NOT NULL,\n" +
                "\t branchName nvarchar(50) NOT NULL,\n" +
                "FOREIGN KEY (storeId) REFERENCES StoreDetail(_id))";
        db.execSQL(createTableBankDetails);
        db.execSQL("INSERT INTO BankDetails VALUES (1, 'Dhruv', '1245789635', 'ICICI', 'Koramangala')");
        db.execSQL("INSERT INTO BankDetails VALUES (2, 'harish', '9874578965', 'BOI', 'Vadodara')");
        db.execSQL("INSERT INTO BankDetails VALUES (3, 'Rajesh', '6542310879', 'SBI', 'Vadodara')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //    getAllBankDetails()
    public ArrayList<BankDetailsModel> getAllBankDetails() {
        ArrayList<BankDetailsModel> bankDetailsModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM BankDetails", null);
        if (cursor.getCount() > 0) {
            if (cursor.moveToFirst()) {
                do {
                    BankDetailsModel bankDetailsModel = new BankDetailsModel(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4));
                    bankDetailsModels.add(bankDetailsModel);
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No Bank Details Found", Toast.LENGTH_SHORT).show();
        }
        return bankDetailsModels;
    }

    //    insertBankDetails()
    public void insertBankDetails(BankDetailsModel bankDetailsModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("storeId", bankDetailsModel.getStoreId());
        values.put("accountHolderName", bankDetailsModel.getAccountHolderName());
        values.put("accountNumber", bankDetailsModel.getAccountNumber());
        values.put("bankName", bankDetailsModel.getBankName());
        values.put("branchName", bankDetailsModel.getBranchName());
        db.insert("BankDetails", null, values);
    }

    //    updateBankDetails()
    public void updateBankDetails(BankDetailsModel bankDetailsModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("storeId", bankDetailsModel.getStoreId());
        values.put("accountHolderName", bankDetailsModel.getAccountHolderName());
        values.put("accountNumber", bankDetailsModel.getAccountNumber());
        values.put("bankName", bankDetailsModel.getBankName());
        values.put("branchName", bankDetailsModel.getBranchName());
        db.update("BankDetails", values, "storeId = ?", new String[]{String.valueOf(bankDetailsModel.getStoreId())});
    }

    //    deleteBankDetails()
    public void deleteBankDetails(int storeId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("BankDetails", "storeId = ?", new String[]{String.valueOf(storeId)});
        db.close();
    }


    //    getAllDeadStock()
    public ArrayList<DeadStockModel> getAllDeadStock() {
        ArrayList<DeadStockModel> deadStockModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM DeadStock";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                DeadStockModel deadStockModel = new DeadStockModel(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2));
                deadStockModels.add(deadStockModel);
            }
        } else {
            Toast.makeText(context, "No Dead Stock", Toast.LENGTH_SHORT).show();
        }
        return deadStockModels;
    }

    //    insertDeadStock()
    public void insertDeadStock(int productId, int quantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("productId", productId);
        contentValues.put("quantity", quantity);
        db.insert("DeadStock", null, contentValues);
    }

    //    deleteDeadStock()
    public void deleteDeadStock(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("DeadStock", "_id = ?", new String[]{String.valueOf(id)});
    }

    //    updateDeadStock()
    public void updateDeadStock(DeadStockModel deadStockModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity", deadStockModel.getQuantity());
        db.update("DeadStock", contentValues, "_id = ?", new String[]{String.valueOf(deadStockModel.getId())});
    }


    //    insert RevisedPurchase
    public void insertRevisedPurchase(int brandId, String description, int quantity, int purchasePrice, int revisedPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("brandId", brandId);
        contentValues.put("description", description);
        contentValues.put("quantity", quantity);
        contentValues.put("purchasePrice", purchasePrice);
        contentValues.put("revisedPrice", revisedPrice);
        db.insert("RevisedPurchase", null, contentValues);
    }

    //    get all RevisedPurchase
    public ArrayList<RevisedPurchaseModel> getAllRevisedPurchase() {
        ArrayList<RevisedPurchaseModel> revisedPurchaseModelArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM RevisedPurchase", null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    RevisedPurchaseModel item = new RevisedPurchaseModel(
                            cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getInt(3),
                            cursor.getInt(4),
                            cursor.getInt(5));
                    revisedPurchaseModelArrayList.add(item);
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
        }
        return revisedPurchaseModelArrayList;
    }

    //    update RevisedPurchase
    public void updateRevisedPurchase(RevisedPurchaseModel revisedPurchaseModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("brandId", revisedPurchaseModel.getBrandId());
        contentValues.put("description", revisedPurchaseModel.getDescription());
        contentValues.put("quantity", revisedPurchaseModel.getQuantity());
        contentValues.put("purchasePrice", revisedPurchaseModel.getPurchasePrice());
        contentValues.put("revisedPrice", revisedPurchaseModel.getRevisedPrice());
        db.update("RevisedPurchase", contentValues, "_id = ?", new String[]{String.valueOf(revisedPurchaseModel.getBatchId())});
    }

    //    delete RevisedPurchase
    public void deleteRevisedPurchase(int batchId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("RevisedPurchase", "_id = ?", new String[]{String.valueOf(batchId)});
        db.close();
    }

    public ArrayList<UsersModel> getAllUsersData() {
        ArrayList<UsersModel> usersModelArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM AllUsers", null);

//        check cursor is null or not
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    UsersModel usersModel = new UsersModel(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(7)
                    );
                    usersModelArrayList.add(usersModel);
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
        }
        return usersModelArrayList;
    }

    public ArrayList<UsersModel> getUsersByRoleId(Integer roleId) {
        ArrayList<UsersModel> usersModelArrayList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from AllUsers where RollId like '" + roleId + "'", null);
//        Cursor cursor = db.rawQuery("SELECT * FROM AllUsers WHERE RollId = ?", new String[]{String.valueOf(roleId)});

//        check cursor is null or not
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    UsersModel usersModel = new UsersModel(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(7)
                    );
                    usersModelArrayList.add(usersModel);
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No Users found", Toast.LENGTH_SHORT).show();
        }
        return usersModelArrayList;
    }

    public ArrayList<ProductPurchaseModel> getAllProductPurchase() {
        ArrayList<ProductPurchaseModel> productPurchases = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM ProductPurchase";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    ProductPurchaseModel productPurchase = new ProductPurchaseModel(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getInt(4),
                            cursor.getInt(5));
                    productPurchases.add(productPurchase);
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
        }
        return productPurchases;
    }

    //    insert Product Purchase
    public void insertProductPurchase(int brandId, String category, String specification, int quantity, int price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BrandId", brandId);
        values.put("Category", category);
        values.put("Specification", specification);
        values.put("Quantity", quantity);
        values.put("Price", price);
        db.insert("ProductPurchase", null, values);
    }

    //    Update product purchase
    public void updateProductPurchase(ProductPurchaseModel productPurchase) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("BrandId", productPurchase.getBrandId());
        values.put("Category", productPurchase.getCategory());
        values.put("Specification", productPurchase.getSpecification());
        values.put("Quantity", productPurchase.getQuantity());
        values.put("Price", productPurchase.getPrice());
        db.update("ProductPurchase", values, "_id = ?", new String[]{String.valueOf(productPurchase.getId())});
    }

    //    deleteProductPurchase
    public void deleteProductPurchase(int productPurchaseId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ProductPurchase", "_id = ?", new String[]{String.valueOf(productPurchaseId)});
        db.close();
    }

    //    getAllProductTransfer
    public ArrayList<ProductTransferModel> getAllProductTransfer() {
        ArrayList<ProductTransferModel> productTransfers = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM ProductTransfer";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    ProductTransferModel productTransfer = new ProductTransferModel(cursor.getInt(0),
                            cursor.getInt(1),
                            cursor.getInt(2),
                            cursor.getInt(3),
                            cursor.getString(4),
                            cursor.getInt(5),
                            cursor.getInt(6));
                    productTransfers.add(productTransfer);
                    Log.d("Data", "getAllProductTransfer: " + cursor.getInt(0) + " " + cursor.getInt(1) + " " + cursor.getInt(2) + " " + cursor.getInt(3) + " " + cursor.getString(4) + " " + cursor.getInt(5) + " " + cursor.getInt(6));
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
        }
        return productTransfers;
    }

    //    insert product transfer
    public void insertProductTransfer(int fromStoreId, int toStoreId, int productId, String
            requestDate, int productStatus, int productQuantity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FromStoreId", fromStoreId);
        values.put("ToStoreId", toStoreId);
        values.put("ProductId", productId);
        values.put("RequestDate", requestDate);
        values.put("ProductStatus", productStatus);
        values.put("ProductQuantity", productQuantity);
        db.insert("ProductTransfer", null, values);
    }

    //    update product transfer
    public void updateProductTransfer(ProductTransferModel productTransfer) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("FromStoreId", productTransfer.getFrom_store_id());
        values.put("ToStoreId", productTransfer.getTo_store_id());
        values.put("ProductId", productTransfer.getProduct_id());
        values.put("RequestDate", productTransfer.getReq_date());
        values.put("ProductStatus", productTransfer.getProduct_status());
        values.put("ProductQuantity", productTransfer.getProduct_quantity());
        db.update("ProductTransfer", values, "_id = ?", new String[]{String.valueOf(productTransfer.getId())});
    }

    //    delete product transfer
    public void deleteProductTransfer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ProductTransfer", "_id = ?", new String[]{String.valueOf(id)});
    }

    //    getStoreNameById
    public String getStoreNameById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT StoreName FROM StoreDetail WHERE _id = " + id;
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                return cursor.getString(0);
            }
        } else {
            Toast.makeText(context, "Store Name Not Found!", Toast.LENGTH_SHORT).show();
        }
        return "";
    }

    public ArrayList<ProductModel> getAllProducts() {
        ArrayList<ProductModel> productModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Products", null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    ProductModel productModel = new ProductModel(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getInt(5),
                            cursor.getInt(6),
                            cursor.getInt(7),
                            cursor.getString(8),
                            cursor.getString(9),
                            cursor.getString(10)
                    );
                    Log.d("GetAllProductsData", "getAllProducts: " + cursor.getInt(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + " " + cursor.getString(5) + " " + cursor.getInt(6) + " " + cursor.getInt(7) + " " + cursor.getString(8) + " " + cursor.getString(9) + " " + cursor.getString(10));
                    productModels.add(productModel);
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
        }
        return productModels;
    }

    //    Add Products
    public void addProduct(String product_name, String product_category, String
            product_size, String product_color, int product_transferable, int product_returnable, int manufacturerId, String
                                   product_description, String product_quantity, String product_price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", product_name);
        values.put("Category", product_category);
        values.put("Size", product_size);
        values.put("Color", product_color);
        values.put("Transferable", product_transferable);
        values.put("Returnable", product_returnable);
        values.put("ManufacturerId", manufacturerId);
        values.put("Description", product_description);
        values.put("Quantity", product_quantity);
        values.put("Price", product_price);
        values.put("CreatedBy", 1);
        values.put("CreatedDate", "2018-01-01");
        db.insert("Products", null, values);
    }

    //    Update Products
    public void updateProduct(int product_id, String product_name, String
            product_category, String product_size, String product_color, int product_transferable,
                              int product_returnable, int manufacturerId, String product_description, String product_quantity, String
                                      product_price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("Name", product_name);
        values.put("Category", product_category);
        values.put("Size", product_size);
        values.put("Color", product_color);
        values.put("Transferable", product_transferable);
        values.put("Returnable", product_returnable);
        values.put("ManufacturerId", manufacturerId);
        values.put("Description", product_description);
        values.put("Quantity", product_quantity);
        values.put("Price", product_price);
        values.put("CreatedBy", 1);
        values.put("CreatedDate", "2018-01-01");
        db.update("Products", values, "_id = ?", new String[]{String.valueOf(product_id)});
        db.close();
    }

    //    Delete Products
    public void deleteProduct(int product_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Products", "_id = ?", new String[]{String.valueOf(product_id)});
        db.close();
    }

    public ArrayList<ManufactureModel> getAllManufacturer() {
        ArrayList<ManufactureModel> manufactureModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ManufacturerDetail", null);

        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    ManufactureModel manufactureModel = new ManufactureModel(cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7),
                            cursor.getString(8));

                    manufactureModels.add(manufactureModel);
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No data found", Toast.LENGTH_SHORT).show();
        }
        return manufactureModels;
    }

    //    Function to add Manufacturer
    public void addManufacturer(String firstName, String lastName, String companyName, String
            mobileNo, String city, String state, String pinCode, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FirstName", firstName);
        contentValues.put("LastName", lastName);
        contentValues.put("CompanyName", companyName);
        contentValues.put("MobileNo", mobileNo);
        contentValues.put("City", city);
        contentValues.put("State", state);
        contentValues.put("PinCode", pinCode);
        contentValues.put("Address", address);
        contentValues.put("CreatedBy", 1);
        contentValues.put("CreatedDate", "2018-01-01");
        db.insert("ManufacturerDetail", null, contentValues);
    }

    //    Function to update Manufacturer
    public void updateManufacturer(String id, String firstName, String lastName, String
            companyName, String mobileNo, String city, String state, String pinCode, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("FirstName", firstName);
        contentValues.put("LastName", lastName);
        contentValues.put("CompanyName", companyName);
        contentValues.put("MobileNo", mobileNo);
        contentValues.put("City", city);
        contentValues.put("State", state);
        contentValues.put("PinCode", pinCode);
        contentValues.put("Address", address);
        contentValues.put("CreatedBy", 1);
        contentValues.put("CreatedDate", "2018-01-01");
        db.update("ManufacturerDetail", contentValues, "_id=?", new String[]{id});
    }

    //    Function to delete Manufacturer
    public void deleteManufacturer(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ManufacturerDetail", "_id = ?", new String[]{String.valueOf(id)});
        db.close();
    }

    public ArrayList<String> getAllExpensesTypes() {
        ArrayList<String> labels = new ArrayList<String>();
        String selectQuery = "SELECT  * FROM ExpenseType";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    labels.add(cursor.getString(1));
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No Expense Types Available", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();

        return labels;
    }

    public ArrayList<ExpenseTypeModel> getAllExpensesTypesModels() {
        ArrayList<ExpenseTypeModel> items = new ArrayList<>();
        String selectQuery = "SELECT  * FROM ExpenseType";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    items.add(new ExpenseTypeModel(cursor.getInt(0), cursor.getString(1)));
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No Expense Types Available", Toast.LENGTH_SHORT).show();
        }

        cursor.close();
        db.close();
        return items;
    }

    public boolean updateExpenseType(int id, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("Name", name);
        db.update("ExpenseType", contentValues, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
        return true;
    }

    public boolean deleteExpenseType(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("ExpenseType", "_id = ?", new String[]{String.valueOf(id)});
        db.close();
        return true;
    }

    public ArrayList<ExpenseModel> getAllExpenseData() {
        ArrayList<ExpenseModel> items = new ArrayList<>();
        String selectQuery = "SELECT  * FROM Expense";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    String expenseType = getExpenseTypeById(cursor.getInt(1));
                    items.add(new ExpenseModel(cursor.getInt(0), expenseType, Float.toString(cursor.getFloat(2)), cursor.getString(3), cursor.getString(4)));
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No Expense Available", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();

        return items;
    }

    public boolean updateExpense(int id, int expenseType, String amount, String date, String
            description) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("ExpenseTypeId", expenseType);
        contentValues.put("Amount", amount);
        contentValues.put("ExpenseDate", date);
        contentValues.put("Description", description);
        contentValues.put("CreatedBy", 1);
        contentValues.put("CreatedDate", "27/03/2022");
        db.update("Expense", contentValues, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
        return true;
    }

    public Integer getExpenseTypeIdByName(String expenseType) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM ExpenseType WHERE Name = ?";
        Cursor cursor = db.rawQuery(selectQuery, new String[]{expenseType});
        if (cursor.moveToFirst()) {
            return cursor.getInt(0);
        }
        db.close();
        return null;
    }

    public boolean deleteExpense(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("Expense", "_id = ?", new String[]{String.valueOf(id)});
        db.close();
        return true;
    }

    private String getExpenseTypeById(int anInt) {
        String expenseType = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM ExpenseType where _id = " + anInt;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            expenseType = cursor.getString(1);
        }
        cursor.close();
        db.close();
        return expenseType;
    }

    public ArrayList<StoresModel> getAllStoresData() {
        ArrayList<StoresModel> items = new ArrayList<>();
        String selectQuery = "SELECT  * FROM StoreDetail";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    items.add(new StoresModel(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6), cursor.getString(7)));
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No Stores Available", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
        return items;
    }

    public boolean deleteStore(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("StoreDetail", "_id = ?", new String[]{String.valueOf(id)});
        db.close();
        return true;
    }

    public boolean updateStore(int id, int userId, String storeName, String
            licenceNumber, String contactNumber, String city, String pinCode, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", id);
        contentValues.put("UserId", userId);
        contentValues.put("StoreName", storeName);
        contentValues.put("LicenceNumber", licenceNumber);
        contentValues.put("ContactNumber", contactNumber);
        contentValues.put("City", city);
        contentValues.put("PinCode", pinCode);
        contentValues.put("Address", address);
        contentValues.put("CreatedBy", 1);
        contentValues.put("CreatedDate", "27/03/2022");
        db.update("StoreDetail", contentValues, "_id = ?", new String[]{String.valueOf(id)});
        db.close();
        return true;
    }

    public List<String> getAllStoreNames() {
        List<String> labels = new ArrayList<>();
        String selectQuery = "SELECT  * FROM StoreDetail";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    labels.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No Stores Available", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
        return labels;
    }

    public String getStoreName(int storeId) {
        String storeName = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM StoreDetail where _id = " + storeId;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            storeName = cursor.getString(2);
        }
        cursor.close();
        db.close();
        return storeName;
    }

    public int getStoreId(String storeName) {
        int storeId = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM StoreDetail where StoreName = \'" + storeName + "\'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            storeId = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return storeId;
    }

    public String getProductNameById(int product_id) {
        String productName = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM Products where _id = " + product_id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            productName = cursor.getString(1);
        }
        cursor.close();
        db.close();
        return productName;
    }

    public int getProductId(String productName) {
        int productId = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM Products where Name = \'" + productName + "\'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            productId = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return productId;
    }

    //    getAllProductNames
    public List<String> getAllProductNames() {
        List<String> labels = new ArrayList<>();
        String selectQuery = "SELECT  * FROM Products";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    labels.add(cursor.getString(1));
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No Products Available", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
        return labels;
    }

    public List<String> getAllManufacturerNames() {
        List<String> labels = new ArrayList<>();
        String selectQuery = "SELECT  * FROM ManufacturerDetail";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.getCount() != 0) {
            if (cursor.moveToFirst()) {
                do {
                    labels.add(cursor.getString(3));
                } while (cursor.moveToNext());
            }
        } else {
            Toast.makeText(context, "No Manufacturers Available", Toast.LENGTH_SHORT).show();
        }
        cursor.close();
        db.close();
        return labels;
    }

    public String getManufactureName(Integer id) {
        String manufactureName = "";
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM ManufacturerDetail where _id = " + id;
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            manufactureName = cursor.getString(3);
        }
        cursor.close();
        db.close();
        return manufactureName;
    }

    public int getManufactureId(String companyName) {
        int manufactureId = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM ManufacturerDetail where CompanyName = \'" + companyName + "\'";
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            manufactureId = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return manufactureId;
    }

    public int getUserCount() {
        String countQuery = "SELECT  count(*) FROM AllUsers";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();
        return count;
    }

    public int getStoresCount() {
        String countQuery = "SELECT  count(*) FROM StoreDetail";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();
        return count;
    }


    public int getManufacturesCount() {
        String countQuery = "SELECT  count(*) FROM ManufacturerDetail";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();
        return count;
    }

    public int getExpensesTypesCount() {
        String countQuery = "SELECT  count(*) FROM ExpenseType";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();
        return count;
    }

    public int getExpensesCount() {
        String countQuery = "SELECT  count(*) FROM Expense";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();
        return count;
    }

    public int getProductsCount() {
        String countQuery = "SELECT  count(*) FROM Products";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();
        return count;
    }

    public int getProductPurchaseCount() {
        String countQuery = "SELECT  count(*) FROM ProductPurchase";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();
        return count;
    }

    public int getRevisedPurchasesCount() {
        String countQuery = "SELECT  count(*) FROM RevisedPurchase";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();
        return count;
    }

    public int getProductsTransfersCount() {
        String countQuery = "SELECT  count(*) FROM ProductTransfer";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();
        return count;
    }


    public int getDeadStockCount() {
        String countQuery = "SELECT  count(*) FROM DeadStock";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();
        return count;
    }

    public int getBankDetailsCount() {
        String countQuery = "SELECT  count(*) FROM BankDetails";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        db.close();
        return count;
    }
}
