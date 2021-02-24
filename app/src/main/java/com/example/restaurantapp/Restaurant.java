package com.example.restaurantapp;

public class Restaurant {
    private String name, address;
    private int restaurantID;
    private float liquorRating, produceRating;

    public Restaurant(){

        restaurantID = -1;
        liquorRating = 0;
        produceRating = 0;
    }

    public float getLiquorRating() {
        return liquorRating;
    }

    public void setLiquorRating(float liquorRating) {
        this.liquorRating = liquorRating;
    }

    public float getProduceRating() {
        return produceRating;
    }

    public void setProduceRating(float produceRating) {
        this.produceRating = produceRating;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    private float rating;

    public int getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
