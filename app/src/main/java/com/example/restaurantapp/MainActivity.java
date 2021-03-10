package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RatingDialog.SaveRatingListener {

    Restaurant currentRestaurant = new Restaurant();

    /*
    * Modify the RestaurantRater app created in Chapter 5, Exercise 9, to display a list of rated meals on the main activity below the address. The list should do the following:
            ▪ It should display the meal name, type, and rating.
            ▪ Tapping an object in the list opens the rating activity created previously, and it operates in the same way except that closing the activity now updates the rating in the list.
            ▪ It allows the user to delete a meal from the list and the database.
            *
            * Additionally: take a look into RestaurantDataSource to see the columns names as needed.
            * TODO:
            *  DONE - main activity page
            *  DONE - rate dialog
            *  DONE - connect to db and have it functioning
            *  DONE- create list item
            *  DONE - get data from database to place into the adapter
            *  create delete button and have that hooked up to delete list items
            *  when selecting the item, we need to have a way to edit the meal like we did on the contact app when you click on a contact
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initRateButton();
        initSaveButton();
        pullArray();

    }

    private void pullArray() {
        RestaurantDataSource ds = new RestaurantDataSource(this);
        ArrayList<Restaurant> meals;

        try{
            ds.open();
            meals = ds.getMeals();
            ds.close();
            RecyclerView mealList = findViewById(R.id.rvMeals);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            mealList.setLayoutManager(layoutManager);
            MealAdapter mealAdapter = new MealAdapter(meals);
            mealList.setAdapter(mealAdapter);
        }
        catch(Exception e){
            Toast.makeText(this, "Error Retrieving meals", Toast.LENGTH_LONG).show();
        }
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        EditText editMeal = findViewById(R.id.editMealName);
        imm.hideSoftInputFromWindow(editMeal.getWindowToken(),0);
        EditText editRestName = findViewById(R.id.editName);
        imm.hideSoftInputFromWindow(editRestName.getWindowToken(),0);
        EditText editAddress= findViewById(R.id.editAddress);
        imm.hideSoftInputFromWindow(editAddress.getWindowToken(),0);
        EditText editCity = findViewById(R.id.editCity);
        imm.hideSoftInputFromWindow(editCity.getWindowToken(),0);
       EditText editState = findViewById(R.id.editState);
        imm.hideSoftInputFromWindow(editState.getWindowToken(),0);
        EditText editZip = findViewById(R.id.editZipCode);
        imm.hideSoftInputFromWindow(editZip.getWindowToken(),0);
        EditText editType = findViewById(R.id.editMealType);
        imm.hideSoftInputFromWindow(editType.getWindowToken(),0);


    }

    private void initRateButton() {
        Button saveButton = findViewById(R.id.rateButton);
        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                FragmentManager fm = getSupportFragmentManager();
                RatingDialog ratingDialog = new RatingDialog(); // We create the dialog here
                ratingDialog.show(fm, "RateMe"); // DialogFragment.show() method shows the actual dialog. For this to happen, we need the fragment manager that is above
            }
        });
    }

    private void initSaveButton(){
        Button saveButton = findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            EditText name = findViewById(R.id.editName);
            EditText address = findViewById(R.id.editAddress);
            EditText city = findViewById(R.id.editCity);
            EditText state = findViewById(R.id.editState);
            EditText zip = findViewById(R.id.editZipCode);
            EditText mealName = findViewById(R.id.editMealName);
            EditText mealType = findViewById(R.id.editMealType);

            currentRestaurant.setName(name.getText().toString());
            currentRestaurant.setAddress(address.getText().toString());
            currentRestaurant.setCity(city.getText().toString());
            currentRestaurant.setState(state.getText().toString());
            currentRestaurant.setZipcode(zip.getText().toString());
            currentRestaurant.setMeal(mealName.getText().toString());
            currentRestaurant.setType(mealType.getText().toString());

            boolean wasSuccessful;
            hideKeyboard();
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
                Toast.makeText(this, "Updated!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(this, "Unsuccessful, check log please.", Toast.LENGTH_LONG).show();
            }
        });

            pullArray();

    }

    @Override
    public void didFinishRating(float myRating) { // this will grab the rating from the dialog box
        currentRestaurant.setRating(myRating);
    }

}