package com.cmput301f22t09.shell379.data;

import android.util.Log;

import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

/**
 *  Meal plan for a certain set of days.
 */
public class MealPlan implements Serializable {
    private ArrayList<MealPlanWrapper<Recipe>> recipes = new ArrayList<>();
    private ArrayList<MealPlanWrapper<Ingredient>> ingredients = new ArrayList<>();
    private Date startDate;
    private Date endDate;
    private Integer activeDays;
    private String mealPlanName;
    private String comments;
    private static final SimpleDateFormat formatter = new SimpleDateFormat("YYYY-MM-dd");

    public MealPlan(String mealPlanName, ArrayList<Recipe> recipes, ArrayList<Ingredient> ingredients, Date startDate, Date endDate, String comments, Integer activeDays) {
        this.mealPlanName = mealPlanName;
        this.recipes = convertRecipes(recipes);
        this.ingredients = convertIngredients(ingredients);
        this.startDate = startDate;
        this.endDate = endDate;
        this.comments = comments;
        this.activeDays = activeDays;
    }

    public MealPlan() {
        this.mealPlanName = "";
        this.startDate = new Date();
        this.endDate = new Date();
        this.comments = "";
        this.activeDays = 0;
    }

    public ArrayList<MealPlanWrapper<Recipe>> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = convertRecipes(recipes);
    }

    /**
     * Sets pre wrapped recipes
     * @param recipes array of wrapped recipes
     */
    public void setRecipesRaw(ArrayList<MealPlanWrapper<Recipe>> recipes) {
        this.recipes = recipes;
    }

    public ArrayList<MealPlanWrapper<Ingredient>> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = convertIngredients(ingredients);
    }

    /**
     * Sets an array of preWrapped ingredients
     * @param ingredients array of wrapped ingredients
     */
    public void setIngredientsRaw(ArrayList<MealPlanWrapper<Ingredient>> ingredients) {
        this.ingredients = ingredients;
    }

    public String getMealPlanName(){
        return mealPlanName;
    }

    public Date getStartDate() {
        return this.startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;

    }

    public Date getEndDate() {
        return this.endDate;
    }

    public void setEndDate(Date endDate) {
        Log.e("FBK_DATE_END_SETTER", endDate.toString());
        this.endDate = endDate;
        Log.e("FBK_DATE_END_SETTER", this.endDate.toString());
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

    /**
     * Gets formatted start date
     * @return string representing the start date
     */
    public String getStartDateFormatted() {
        if(startDate != null){
            SimpleDateFormat simpleDate = formatter;
            return simpleDate.format(getStartDate());
        }else{
            return "Date not set";
        }
    }

    /**
     * Gets formatted end date
     * @return string representing the end date
     */
    public String getEndDateFormatted() {
        if(endDate != null){
            SimpleDateFormat simpleDate = formatter;
            return simpleDate.format(getEndDate());
        }else{
            return "Date not set";
        }
    }

    public void setMealPlanName(String mealPlanName) {
        this.mealPlanName = mealPlanName;
    }

    /**
     * Converts a list of recipes to a list of wrapped recipes.
     * @param recipes array of recipes to be wrapped
     * @return array list of wrapped recipes.
     */
    private ArrayList convertRecipes(ArrayList<Recipe> recipes) {
        return recipes.stream().map(o->new MealPlanWrapper(o, new Date(), 1)).collect(Collectors.toCollection(ArrayList::new));
    }

    /**
     * Converts a list of recipes to a list of wrapped ingredients.
     * @param ingredients array of ingredients to be wrapped
     * @return array list of wrapped ingredients.
     */
    private ArrayList convertIngredients(ArrayList<Ingredient> ingredients) {
        return ingredients.stream().map(o->new MealPlanWrapper(o, new Date(), 1)).collect(Collectors.toCollection(ArrayList::new));
    }
}
