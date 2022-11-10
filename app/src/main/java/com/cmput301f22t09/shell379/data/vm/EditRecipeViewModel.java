package com.cmput301f22t09.shell379.data.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;

import java.util.ArrayList;

/**
 *  maintains state for a draft recipe change.
 */
public class EditRecipeViewModel extends ViewModel {
    // view model template code from https://developer.android.com/topic/libraries/architecture/viewmodel

    private MutableLiveData<ArrayList<Ingredient>> liveSelectedIngredients = new MutableLiveData<ArrayList<Ingredient>>();

    public MutableLiveData<ArrayList<Ingredient>> getLiveSelectedIngredients() {
        return liveSelectedIngredients;
    }

    public void forceSignalUpdate(){
        liveSelectedIngredients.setValue(liveSelectedIngredients.getValue());
    }

    public ArrayList<Ingredient> getSelectedIngredients(){
        if (liveSelectedIngredients.getValue() == null){
            ArrayList<Ingredient> selectedIngredients = new ArrayList<Ingredient>();
            liveSelectedIngredients.setValue(selectedIngredients);
            return liveSelectedIngredients.getValue();
        }
        return  liveSelectedIngredients.getValue();
    }

    public void setSelectedIngredients(ArrayList<Ingredient> newIngs){
        liveSelectedIngredients.setValue(newIngs);
    }

}