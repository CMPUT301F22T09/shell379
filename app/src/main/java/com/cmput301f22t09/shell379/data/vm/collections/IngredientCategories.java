package com.cmput301f22t09.shell379.data.vm.collections;

import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;

import java.util.HashSet;

public class IngredientCategories extends Commitable {
    private HashSet<String> ingredCategories;

    public IngredientCategories() {
        this.ingredCategories = new HashSet<String>();
    }

    public HashSet<String> getIngredCategories() {
        return ingredCategories;
    }

    public void setIngredCategories(HashSet<String> ingredCategories) {
        this.ingredCategories = ingredCategories;
    }

    public void addCategory(String category) {
        ingredCategories.add(category);
    }
}
