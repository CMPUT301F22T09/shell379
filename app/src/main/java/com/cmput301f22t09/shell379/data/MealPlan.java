package com.cmput301f22t09.shell379.data;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MealPlan implements Serializable {
    private ArrayList<Recipe> recipes = new ArrayList<>();
    private ArrayList<Ingredient> ingredients = new ArrayList<>();
    private String startDate;
    private String endDate;
    private Integer activeDays;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");

    public MealPlan(ArrayList<Recipe> recipes, ArrayList<Ingredient> ingredients, Date startDate, Date endDate, Integer activeDays) {
        this.recipes = recipes;
        this.ingredients = ingredients;
        this.startDate = formatter.format(startDate);
        this.endDate = formatter.format(endDate);
        this.activeDays = activeDays;
    }

    public MealPlan(Date startDate, Date endDate, Integer activeDays) {
        this.startDate = formatter.format(startDate);
        this.endDate = formatter.format(endDate);
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

    public Date getStartDate() {
        try {
            return formatter.parse(this.startDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public void setStartDate(Date startDate) {
        this.startDate = formatter.format(startDate);
    }

    public Date getEndDate() {
        try {
            return formatter.parse(this.endDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public void setEndDate(Date endDate) {
        this.endDate = formatter.format(endDate);
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

    public Ingredient getIngredientAtIdx(Integer idx) {
        return this.ingredients.get(idx);
    }

    public Recipe getRecipeAtIdx(Integer idx) {
        return this.recipes.get(idx);
    }

    public void removeIngredientAtIdx(Integer idx) {
        this.ingredients.remove(idx);
    }

    public void removeRecipeAtIdx(Integer idx) {
        this.recipes.remove(idx);
    }
}
