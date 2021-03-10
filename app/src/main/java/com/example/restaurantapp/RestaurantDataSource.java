package com.example.restaurantapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class RestaurantDataSource {

    private SQLiteDatabase database;
    private RestaurantDBHelper dbHelper;

    public RestaurantDataSource(Context context) {
        dbHelper = new RestaurantDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public boolean insertRestaurant(Restaurant r) {
        boolean didSucceed = false;
        try {
            ContentValues initialValues = new ContentValues();

            initialValues.put("name", r.getName());
            initialValues.put("address", r.getAddress());
            initialValues.put("city", r.getCity());
            initialValues.put("state", r.getState());
            initialValues.put("zipcode", r.getZipcode());
            initialValues.put("meal", r.getMeal());
            initialValues.put("type", r.getType());
            initialValues.put("rating", r.getRating());

            didSucceed = database.insert("restaurants", null, initialValues) > 0; // this will return the number of rows that were affected by the change, which is why we compare it 0
        }

        catch (Exception e) { // don't have to put anything here because if false it will return false
        }
        return didSucceed;
    }

    public boolean updateRestaurant(Restaurant r) {
        boolean didSucceed = false;
        try {
            long rowId = (long) r.getRestaurantID();
            ContentValues updateValues = new ContentValues();

            updateValues.put("name", r.getName());
            updateValues.put("address", r.getAddress());
            updateValues.put("city", r.getCity());
            updateValues.put("state", r.getState());
            updateValues.put("zipcode", r.getZipcode());
            updateValues.put("meal", r.getMeal());
            updateValues.put("type", r.getType());
            updateValues.put("rating", r.getRating());

            didSucceed = database.update("restaurants", updateValues, "_id=" + rowId, null) > 0; // this will return the number of rows that were affected by the change, which is why we compare it 0
        }
        catch (Exception ignored) {
        }
        return didSucceed;
    }

    public int getLastRestaurantID() {
        int lastId;

        try {
            String query = "Select MAX(_id) from restaurants";
            Cursor cursor = database.rawQuery(query, null);

            cursor.moveToFirst();
            lastId = cursor.getInt(0);
            cursor.close();
        }
        catch (Exception e) {
            lastId = -1;
        }
        return lastId;
    }


    //creating an Array of Restaurant type where we store name, meal and rating data,
    // pulled from the DB
    public ArrayList<Restaurant> getMeals(){
        ArrayList<Restaurant> meals = new ArrayList<>();
        try{
            //we just need these 3 values
            String query = "Select meal, type, rating from restaurants";
            Cursor cursor = database.rawQuery(query, null);
            cursor.moveToFirst();

            while(!cursor.isAfterLast()){
                //while queried list lasts, we create a new object
                Restaurant restaurant = new Restaurant();

                //store these 3 values
                restaurant.setMeal(cursor.getString(0));
                restaurant.setType(cursor.getString(1));
                restaurant.setRating(cursor.getFloat(2));

                //adding objects to the array
                meals.add(restaurant);
                cursor.moveToNext();
            }
        }
        catch (Exception e){
            meals = new ArrayList<>();
        }
        return meals;
    }

}
