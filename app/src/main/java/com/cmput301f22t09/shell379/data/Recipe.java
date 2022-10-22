package com.cmput301f22t09.shell379.data;

import android.media.Image;

import java.util.ArrayList;

public class Recipe {
    private String title;
    private Long preparationTime; //in milliseconds
    private Integer servings;
    private String category;
    private String comments;
    private Image photograph;
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    public Recipe(String title, Long preparationTime, Integer servings, String category, String comments) {
        this.title = title;
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.category = category;
        this.comments = comments;
    }

    public Recipe(String title, Long preparationTime, Integer servings, String category, String comments, Image photograph) {
        this.title = title;
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.category = category;
        this.comments = comments;
        this.photograph = photograph;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(Long preparationTime) {
        this.preparationTime = preparationTime;
    }

    public Integer getServings() {
        return servings;
    }

    public void setServings(Integer servings) {
        this.servings = servings;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Image getPhotograph() {
        return photograph;
    }

    public void setPhotograph(Image photograph) {
        this.photograph = photograph;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void addIngredient(Ingredient ingredient) {
        ingredients.add(ingredient);
    }

    public void addIngredients(ArrayList<Ingredient> newIngredients) {
        ingredients.addAll(newIngredients);
    }
}