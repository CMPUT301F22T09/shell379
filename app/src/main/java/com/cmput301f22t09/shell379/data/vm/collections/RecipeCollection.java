package com.cmput301f22t09.shell379.data.vm.collections;

import androidx.lifecycle.MutableLiveData;

import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;

import java.util.ArrayList;

public class RecipeCollection extends Commitable {
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

    public void commit(ArrayList<Recipe> newRecipe) {
        recipes.setValue(newRecipe);
        readyForCommit();
    }
}