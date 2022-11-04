package com.cmput301f22t09.shell379.data;

import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.cmput301f22t09.shell379.data.util.SerializeUtil;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * The Recipe class is a model for potential recipes used in the app.
 */
public class Recipe implements Serializable {
    private String title;
    private Long preparationTime; //in milliseconds
    private Integer servings;
    private String category;
    private String comments;
    private String photograph; //this is a serialized photo
    private ArrayList<Ingredient> ingredients = new ArrayList<>();

    /**
     * Creates recipe object without image.
     * @param title recipe name
     * @param preparationTime the time required for preparation in minutes
     * @param servings the number of servings for the recipe
     * @param category the recipe category
     * @param comments comments regarding the recipe
     */
    public Recipe(String title, Long preparationTime, Integer servings, String category, String comments) {
        this.title = title;
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.category = category;
        this.comments = comments;
    }

    /**
     * Creates recipe object with an image.
     * The image will be serialized to a string!
     * @param title recipe name
     * @param preparationTime the time required for preparation in minutes
     * @param servings the number of servings for the recipe
     * @param category the recipe category
     * @param comments comments regarding the recipe
     * @param photograph the recipe image
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Recipe(String title, Long preparationTime, Integer servings, String category, String comments, Bitmap photograph) {
        this.title = title;
        this.preparationTime = preparationTime;
        this.servings = servings;
        this.category = category;
        this.comments = comments;
        this.photograph = SerializeUtil.serializeImg(photograph);
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

    /**
     * Deserializes and returns the Recipe image
     * @return deserialized image
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public Bitmap getPhotograph() {
        return SerializeUtil.deserializeImg(photograph);
    }

    /**
     * Serializes image to string and sets internally
     * @param photograph image to be stored
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void setPhotograph(Bitmap photograph) {
        this.photograph = SerializeUtil.serializeImg(photograph);
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

    /**
     * overrides Java's equals method
     * @param o recipe to compare to
     * @return true if the recipe is equal, false otherwise
     */
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }

        if (!(o instanceof Recipe)) {
            return false;
        }

        Recipe r = (Recipe) o;

        return r.getTitle().equals(title) && r.getPreparationTime().equals(preparationTime) && r.getServings().equals(servings)
                && r.getCategory().equals(category) && r.getComments().equals(comments);
    }
}