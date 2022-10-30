package com.cmput301f22t09.shell379.data.vm.collections;

import androidx.lifecycle.MutableLiveData;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;

import java.io.Serializable;
import java.util.ArrayList;

public class IngredientCollection extends Commitable implements Serializable {
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

    public void add(Ingredient ingredient) {
        ingredients.getValue().add(ingredient);
        setIngredients(ingredients.getValue());
    }

    public void add(ArrayList<Ingredient> ingredients) {
        this.ingredients.getValue().addAll(ingredients);
        setIngredients(this.ingredients.getValue());
    }

    public void removeAtIdx(int i) {
        this.ingredients.getValue().remove(i);
        setIngredients(ingredients.getValue());
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients.setValue(ingredients);
    }

    public int checkFullEqual(Ingredient ingredient){
        ArrayList<Ingredient> allIngredient = ingredients.getValue();
        for (int i=0; i< allIngredient.size(); i++) {
            Ingredient a = allIngredient.get(i);
            boolean fullEqual = ingredient.getFullEqual(a);
            if (fullEqual==true){
                return i;
            }
        }
        return -1;
    }

}