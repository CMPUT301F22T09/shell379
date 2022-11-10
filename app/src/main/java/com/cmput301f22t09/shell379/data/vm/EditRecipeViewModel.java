package com.cmput301f22t09.shell379.data.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.Recipe;

import java.util.ArrayList;

/**
 *  maintains state for a draft recipe change. Currently it only tracks ingredients for the recipe
 */
public class EditRecipeViewModel extends ViewModel {
    // view model template code from https://developer.android.com/topic/libraries/architecture/viewmodel

    private MutableLiveData<ArrayList<IngredientStub>> liveSelectedIngredientStubs = new MutableLiveData<ArrayList<IngredientStub>>();

    public MutableLiveData<ArrayList<IngredientStub>> getLiveSelectedIngredients() {
        return liveSelectedIngredientStubs;
    }

    /**
     * notifies all listeners of the ingredient stubs of a change
     */
    public void forceSignalUpdate(){
        liveSelectedIngredientStubs.setValue(liveSelectedIngredientStubs.getValue());
    }

    public ArrayList<IngredientStub> getSelectedIngredients(){
        if (liveSelectedIngredientStubs.getValue() == null){
            ArrayList<IngredientStub> selectedIngredientStubs = new ArrayList<IngredientStub>();
            liveSelectedIngredientStubs.setValue(selectedIngredientStubs);
            return liveSelectedIngredientStubs.getValue();
        }
        return  liveSelectedIngredientStubs.getValue();
    }

    public void setSelectedIngredients(ArrayList<IngredientStub> newIngs){
        liveSelectedIngredientStubs.setValue(newIngs);
    }

}