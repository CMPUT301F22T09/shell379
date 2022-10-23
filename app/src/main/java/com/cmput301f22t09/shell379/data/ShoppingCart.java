package com.cmput301f22t09.shell379.data;

import com.cmput301f22t09.shell379.data.wrapper.CartIngredientWrapper;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingCart implements Serializable {
    private ArrayList<CartIngredientWrapper> ingredients = new ArrayList<>();
    private Integer activeDays;

    public void setIngredients(ArrayList<CartIngredientWrapper> ingredients) {
        this.ingredients = ingredients;
    }

    public void setActiveDays(Integer activeDays) {
        this.activeDays = activeDays;
    }

    public ArrayList<CartIngredientWrapper> getIngredients() {
        return ingredients;
    }

    public Integer getActiveDays() {
        return activeDays;
    }
}