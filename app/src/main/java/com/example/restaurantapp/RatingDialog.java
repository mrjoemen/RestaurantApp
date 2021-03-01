package com.example.restaurantapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;

import androidx.fragment.app.DialogFragment;

public class RatingDialog extends DialogFragment {

    RatingBar dishRating;
    float myRating;

    public interface SaveRatingListener {
        void didFinishRating(float myRating); // that same variable also must be placed here
    }

    public RatingDialog() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.select_rating, container);
        final RatingBar rating = view.findViewById(R.id.ratingBar);
        getDialog().setTitle("Rate the dish!");

        rating.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                myRating = ratingBar.getRating();
            }
        });

        Button saveBtn = view.findViewById(R.id.saveBtn);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveItem(myRating);
                getDialog().dismiss();
            }
        });

        return view;
    }

    private void saveItem(float rating) {
        SaveRatingListener activity = (SaveRatingListener) getActivity();

        activity.didFinishRating(myRating);
        getDialog().dismiss();

    }


}
