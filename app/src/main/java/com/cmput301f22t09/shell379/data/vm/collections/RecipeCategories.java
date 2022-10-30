package com.cmput301f22t09.shell379.data.vm.collections;

import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;

import java.util.HashSet;

public class RecipeCategories extends Commitable {
    private HashSet<String> recipeCategories;

    public RecipeCategories() {
        this.recipeCategories = new HashSet<String>();
    }

    public HashSet<String> getRecipeCategories() {
        return recipeCategories;
    }

    public void setRecipeCategories(HashSet<String> recipeCategories) {
        this.recipeCategories = recipeCategories;
    }

    public void addCategory(String category) {
        recipeCategories.add(category);
    }
}
