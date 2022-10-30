package com.cmput301f22t09.shell379.data;

import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;

public class MealPlan implements Serializable {
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private Integer activeDays;
}
