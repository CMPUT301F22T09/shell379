package com.cmput301f22t09.shell379.data;

import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

public class MealPlan implements Serializable {
    private ArrayList<MealPlanWrapper<Recipe>> recipes = new ArrayList<>();
    private ArrayList<MealPlanWrapper<Ingredient>> ingredients = new ArrayList<>();
    private String startDate;
    private String endDate;
    private Integer activeDays;
    private String mealPlanName;
    private String comments;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-DD");

    public MealPlan(String mealPlanName, ArrayList<Recipe> recipes, ArrayList<Ingredient> ingredients, Date startDate, Date endDate, String comments, Integer activeDays) {
        this.mealPlanName = mealPlanName;
        this.recipes = convertRecipes(recipes);
        this.ingredients = convertIngredients(ingredients);
        this.startDate = formatter.format(startDate);
        this.endDate = formatter.format(endDate);
        this.comments = comments;
        this.activeDays = activeDays;
    }

    public MealPlan() {

    }

//    public MealPlan(Date startDate, Date endDate, Integer activeDays) {
//        this.startDate = formatter.format(startDate);
//        this.endDate = formatter.format(endDate);
//        this.activeDays = activeDays;
//    }


    public ArrayList<MealPlanWrapper<Recipe>> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = convertRecipes(recipes);
    }

    public void setRecipesRaw(ArrayList<MealPlanWrapper<Recipe>> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<MealPlanWrapper<Ingredient>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = convertIngredients(ingredients);
    }

    public void setIngredientsRaw(ArrayList<MealPlanWrapper<Ingredient>> ingredients) {
        this.ingredients = ingredients;
    }

    public String getMealPlanName(){
        return mealPlanName;
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

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public void addIngredient(Ingredient ingredient) {
        this.ingredients.add(new MealPlanWrapper<>(ingredient, new Date(), 0));
    }

    public void addIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients.addAll(convertIngredients(ingredients));
    }

    public void addRecipe(Recipe recipe) {
        this.recipes.add(new MealPlanWrapper<>(recipe, new Date(), 0));
    }

    public void addRecipes(ArrayList<Recipe> recipes) {
        this.recipes.addAll(convertRecipes(recipes));
    }

    public MealPlanWrapper<Ingredient> getIngredientAtIdx(Integer idx) {
        return this.ingredients.get(idx);
    }

    public MealPlanWrapper<Recipe> getRecipeAtIdx(Integer idx) {
        return this.recipes.get(idx);
    }

    public void removeIngredientAtIdx(Integer idx) {
        this.ingredients.remove(idx);
    }

    public void removeRecipeAtIdx(Integer idx) {
        this.recipes.remove(idx);
    }

    public String getStartDateFormatted() {
        if(startDate != null){
            SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");
            return simpleDate.format(getStartDate());
        }else{
            return "Date not set";
        }
    }

    public String getEndDateFormatted() {
        if(endDate != null){
            SimpleDateFormat simpleDate =  new SimpleDateFormat("dd/MM/yyyy");
            return simpleDate.format(getEndDate());
        }else{
            return "Date not set";
        }
    }

    public void setMealPlanName(String mealPlanName) {
        this.mealPlanName = mealPlanName;
    }

    private ArrayList convertRecipes(ArrayList<Recipe> recipes) {
        return recipes.stream().map(o->new MealPlanWrapper(o, new Date(), 1)).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList convertIngredients(ArrayList<Ingredient> ingredients) {
        return ingredients.stream().map(o->new MealPlanWrapper(o, new Date(), 1)).collect(Collectors.toCollection(ArrayList::new));
    }
}
