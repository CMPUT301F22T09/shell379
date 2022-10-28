package com.cmput301f22t09.shell379.data.vm.collections;

import androidx.lifecycle.MutableLiveData;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;

import java.io.Serializable;
import java.util.ArrayList;

public class RecipeCollection extends Commitable implements Serializable {
    private final MutableLiveData<ArrayList<Recipe>> recipes = new MutableLiveData<>();
    private final MutableLiveData<Boolean> changed = new MutableLiveData<>();

    public RecipeCollection() {
        recipes.setValue(new ArrayList<>());
    }

    public MutableLiveData<ArrayList<Recipe>> getRecipesLive() {
        return recipes;
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes.getValue();
    }

    public void add(Recipe ingredient) {
        recipes.getValue().add(ingredient);
    }

    public void add(ArrayList<Recipe> recipes) {
        this.recipes.getValue().addAll(recipes);
    }

    public void removeAtIdx(int i) {
        this.recipes.getValue().remove(i);
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes.setValue(recipes);
    }
}