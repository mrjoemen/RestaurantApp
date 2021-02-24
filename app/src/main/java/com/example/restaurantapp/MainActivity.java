package com.example.restaurantapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT = "com.example.restaurantapp.EXTRA_TEXT";
    public static final String EXTRA_TEXT2 = "com.example.restaurantapp.EXTRA_TEXT2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initRateButton();
    }

    private void initRateButton() {
        Button saveButton = findViewById(R.id.rateButton);
        saveButton.setOnClickListener(v -> {
            EditText etName = findViewById(R.id.etName);
            EditText etAddress = findViewById(R.id.etAddress);

            Intent intent = new Intent(this, RateActivity.class); //an intent acts like a vehicle to take information from one activity to the next or to start an activity
            intent.putExtra(EXTRA_TEXT, etName.getText().toString());
            intent.putExtra(EXTRA_TEXT2, etAddress.getText().toString());
            startActivity(intent);
        });
    }

}