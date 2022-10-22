package com.cmput301f22t09.shell379.data;

import com.cmput301f22t09.shell379.data.wrapper.CartIngredientWrapper;

import java.util.ArrayList;

public class ShoppingCart {
    private ArrayList<CartIngredientWrapper> ingredients = new ArrayList<>();
    private Integer activeDays;
}