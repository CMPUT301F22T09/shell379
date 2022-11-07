package com.cmput301f22t09.shell379.data;

import android.media.Image;

import java.io.Serializable;
import java.util.ArrayList;

public class MealPlan implements Serializable {
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private String startDate;
    private String endDate;
    private Integer activeDays;

    public MealPlan(ArrayList<Recipe> recipes, ArrayList<Ingredient> ingredients, String startDate, String endDate, Integer activeDays) {
        this.recipes = recipes;
        this.ingredients = ingredients;
        this.startDate = startDate;
        this.endDate = endDate;
        this.activeDays = activeDays;
    }

    public MealPlan(String startDate, String endDate, Integer activeDays) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.activeDays = activeDays;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getActiveDays() {
        return activeDays;
    }

    public void setActiveDays(Integer activeDays) {
        this.activeDays = activeDays;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(ingredient);
    }

    public void addIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients.addAll(ingredients);
    }

    public void addRecipe(Recipe recipe) {
        this.recipes.add(recipe);
    }

    public void addRecipes(ArrayList<Recipe> recipes) {
        this.recipes.addAll(recipes);
    }
}
