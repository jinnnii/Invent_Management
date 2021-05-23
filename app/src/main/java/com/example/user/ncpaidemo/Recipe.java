package com.example.user.ncpaidemo;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {
    private String name;
    private int price;
    private int total_unit_price;
    private ArrayList<RecipeItem> userItems = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ArrayList<RecipeItem> getUserItems() {
        return userItems;
    }

    public void setUserItems(ArrayList<RecipeItem> userItems) {
        this.userItems = userItems;
    }

    public int getTotal_unit_price() {
        return total_unit_price;
    }

    public void setTotal_unit_price(int total_unit_price) {
        this.total_unit_price = total_unit_price;
    }

    public Recipe() {
    }

    public Recipe(String name, int price, int total_unit_price, ArrayList<RecipeItem> wUserItems) {
        this.name = name;
        this.price = price;
        this.userItems = wUserItems;
        this.total_unit_price =total_unit_price;
    }
}
