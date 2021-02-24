package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RateActivity extends AppCompatActivity {
    private Restaurant currentRestaurant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        currentRestaurant = new Restaurant();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);
        initSaveButton();
    }

    private void initSaveButton(){
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            Intent intent = getIntent();
            String name = intent.getStringExtra(MainActivity.EXTRA_TEXT);
            String address = intent.getStringExtra(MainActivity.EXTRA_TEXT2);
            RatingBar rLiquor = findViewById(R.id.ratingBarLiquor);
            RatingBar rProduce = findViewById(R.id.ratingBarProduce);

            currentRestaurant.setName(name);
            currentRestaurant.setAddress(address);
            currentRestaurant.setLiquorRating(rLiquor.getRating());
            currentRestaurant.setProduceRating(rProduce.getRating());
            
            boolean wasSuccessful;
            RestaurantDataSource ds = new RestaurantDataSource(this);
            try {
                ds.open();
                if (currentRestaurant.getRestaurantID() == -1) {
                    wasSuccessful = ds.insertRestaurant(currentRestaurant);
                } else {
                    wasSuccessful = ds.updateRestaurant(currentRestaurant);
                }
            } catch (Exception e) {
                wasSuccessful = false;
            }

            if (wasSuccessful) {
                TextView averageReview = findViewById(R.id.averageReview);
                float average = (currentRestaurant.getLiquorRating() + currentRestaurant.getProduceRating()) / 2;
                averageReview.setText(String.format("%s has an average review of a %s out of 5 ⭐", currentRestaurant.getName(), average));
                Log.i("tag", Integer.toString(currentRestaurant.getRestaurantID()));
            }
            else {
                Toast.makeText(this, "Unsuccessful, check log please.", Toast.LENGTH_LONG);
            }
        });
    }
}