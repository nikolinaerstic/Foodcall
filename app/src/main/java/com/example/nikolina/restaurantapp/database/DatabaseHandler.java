package com.example.nikolina.restaurantapp.database;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "cart.db";
    private static final String TABLE_CART = "CART";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_MEAL_NAME = "MEAL_NAME";
    private static final String COLUMN_MEAL_PRICE = "MEAL_PRICE";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_CART + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_MEAL_NAME + " TEXT, "
                + COLUMN_MEAL_PRICE + " INTEGER" + ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CART);
        onCreate(db);
    }

    public void saveToCart(String mealName, int price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEAL_NAME, mealName);
        values.put(COLUMN_MEAL_PRICE, price);
        db.insert(TABLE_CART, null, values);
        db.close();
    }

    public Cursor getAllMeals(){
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_CART, null);
    }

    public void clearCartItems(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CART, null, null);
        db.close();
    }
}

