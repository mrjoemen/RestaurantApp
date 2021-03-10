package com.example.restaurantapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MealAdapter extends RecyclerView.Adapter {

    private ArrayList<Restaurant> mealData;
    private View.OnClickListener onItemListener;

    public class MealViewHolder extends RecyclerView.ViewHolder{

        //this where we set displayed values
        //first we created the fields
        public TextView textViewMeal;
        public TextView textViewType;
        public RatingBar ratingBar;

        public MealViewHolder(@NonNull View itemView){
            super(itemView);

            //assign xml id to each from list_item
            textViewMeal = itemView.findViewById(R.id.mealName);
            textViewType = itemView.findViewById(R.id.mealType);
            ratingBar = itemView.findViewById(R.id.mealRating);
            itemView.setOnClickListener(onItemListener);
        }

        public TextView getTextViewType() {
            return textViewType;
        }

        public RatingBar getRatingBar() {
            return ratingBar;
        }

        public TextView getTextViewMeal(){
            return textViewMeal;
        }
    }

    public MealAdapter(ArrayList<Restaurant> arrayList){
        mealData = arrayList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent, false);

        return new MealViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        //assign object values to the ViewHolder object's field
        MealViewHolder mvh = (MealViewHolder) holder;
        mvh.getTextViewMeal().setText(mealData.get(position).getMeal());
        mvh.getTextViewType().setText(mealData.get(position).getType());
        mvh.getRatingBar().setRating(mealData.get(position).getRating());

    }

    @Override
    public int getItemCount() {
        return mealData.size();
    }
}
