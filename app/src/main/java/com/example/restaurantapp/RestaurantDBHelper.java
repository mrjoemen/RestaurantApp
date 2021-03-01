package com.example.restaurantapp;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class RestaurantDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "restaurants.db";
    private static final int DATABASE_VERSION = 1;

    //Database creation sql statement
    private static final String CREATE_TABLE_RESTAURANT =
            "create table restaurants(_id integer primary key autoincrement, "
                    + "name text not null, address text, city text, state text, zipcode text," +
                        "meal text, type text, rating real);";
    public RestaurantDBHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_RESTAURANT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(RestaurantDBHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all data");
        db.execSQL("DROP TABLE IF EXISTS restaurants");
        onCreate(db);
    }
}
