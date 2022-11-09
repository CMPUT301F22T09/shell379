package com.cmput301f22t09.shell379.data.vm;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.cmput301f22t09.shell379.data.Recipe;

/**
 *  maintains state for a draft recipe change.
 */
public class EditRecipeViewModel extends ViewModel {
    // view model template code from https://developer.android.com/topic/libraries/architecture/viewmodel

    private MutableLiveData<Recipe> liveRecipe = new MutableLiveData<Recipe>();

    public MutableLiveData<Recipe> liveRecipe() {
        return liveRecipe;
    }

    public void forceSignalUpdate(){
        liveRecipe.setValue(liveRecipe.getValue());
    }


    public Recipe getRecipe(){
        if (liveRecipe.getValue() == null){
            Recipe newRecipe = new Recipe(null,null,null,null,null);
            liveRecipe.setValue(newRecipe);
            return newRecipe;
        }
        return  liveRecipe.getValue();
    }

}