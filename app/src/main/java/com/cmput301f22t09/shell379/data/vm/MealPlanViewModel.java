package com.cmput301f22t09.shell379.data.vm;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.util.ArrayList;
import java.util.Date;

/**
 *  maintains state for a draft recipe change. Currently it only tracks ingredients for the recipe
 */
public class MealPlanViewModel extends ViewModel {
    // view model template code from https://developer.android.com/topic/libraries/architecture/viewmodel

    public MealPlanViewModel() {
        this.mealPlan.setValue(new MealPlan("", new ArrayList<Recipe>(),
                new ArrayList<Ingredient>(), new Date(), new Date(), 0));
    }

    public static MealPlanViewModel of(MealPlan mealPlan, FragmentActivity activity) {
        MealPlanViewModel mpViewModel = new ViewModelProvider(activity).get(MealPlanViewModel.class);
        mpViewModel.mealPlan.setValue(mealPlan);
        return mpViewModel;
    }

    private MutableLiveData<MealPlan> mealPlan = new MutableLiveData<>();

    public MutableLiveData<MealPlan> getLive() {
        return mealPlan;
    }

    /**
     * notifies all listeners of the ingredient stubs of a change
     */
    public void forceSignalUpdate(){
        mealPlan.setValue(mealPlan.getValue());
    }

    public MealPlan getMealPlan(){
        if (mealPlan.getValue() == null){
            MealPlan mp = new MealPlan("", new ArrayList<Recipe>(),
                    new ArrayList<Ingredient>(), new Date(), new Date(), 0);
            mealPlan.setValue(mp);
            return mealPlan.getValue();
        }
        return mealPlan.getValue();
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        MealPlan mealPlan = this.mealPlan.getValue();
        if (mealPlan != null) {
            mealPlan.setIngredients(ingredients);
        }
        this.setMealPlan(mealPlan);
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        MealPlan mealPlan = this.mealPlan.getValue();
        if (mealPlan != null) {
            mealPlan.setRecipes(recipes);
        }
        this.setMealPlan(mealPlan);
    }

    public MealPlanWrapper<Ingredient> getIngredientAtIdx(Integer idx) {
        return getMealPlan().getIngredients().get(idx);
    }

    public MealPlanWrapper<Recipe> getRecipeAtIdx(Integer idx) {
        return getMealPlan().getRecipes().get(idx);
    }

    public void setMealPlan(MealPlan mealPlan){
        this.mealPlan.setValue(mealPlan);
    }

    public void setIngredientsRaw(ArrayList<MealPlanWrapper<Ingredient>> newIngredients) {
        this.mealPlan.getValue().setIngredientsRaw(newIngredients);
    }

    public void setRecipesRaw(ArrayList<MealPlanWrapper<Recipe>> recipes) {
        this.mealPlan.getValue().setRecipesRaw(recipes);
    }

    public ArrayList<MealPlanWrapper<Recipe>> getRecipes() {
        return mealPlan.getValue().getRecipes();
    }

    public ArrayList<MealPlanWrapper<Ingredient>> getIngredients() {
        return mealPlan.getValue().getIngredients();
    }
}