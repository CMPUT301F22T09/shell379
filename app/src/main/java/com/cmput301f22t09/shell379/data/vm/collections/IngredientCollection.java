package com.cmput301f22t09.shell379.data.vm.collections;

import androidx.lifecycle.MutableLiveData;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;

import java.util.ArrayList;

public class IngredientCollection extends Commitable {
    private final MutableLiveData<ArrayList<Ingredient>> ingredients = new MutableLiveData<>();

    public IngredientCollection() {
        ingredients.setValue(new ArrayList<>());
    }

    public MutableLiveData<ArrayList<Ingredient>> getIngredientsLive() {
        return ingredients;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients.getValue();
    }

    public void commit(ArrayList<Ingredient> newIngredients) {
        ingredients.setValue(newIngredients);
        readyForCommit();
    }
}