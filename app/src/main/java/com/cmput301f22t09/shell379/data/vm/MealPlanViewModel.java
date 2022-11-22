package com.cmput301f22t09.shell379.data.vm;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
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
    private MutableLiveData<MealPlan> mealPlan = new MutableLiveData<>();
    private MutableLiveData<Integer> idx = new MutableLiveData<>();

    public MealPlanViewModel() {
        this.mealPlan.setValue(new MealPlan("", new ArrayList<Recipe>(),
                new ArrayList<Ingredient>(), new Date(), new Date(), "", 0));
    }

    public static MealPlanViewModel of(FragmentActivity activity) {
        MealPlanViewModel viewModel = new ViewModelProvider(activity).get(MealPlanViewModel.class);
        if (viewModel == null) viewModel = new MealPlanViewModel();
        return viewModel;
    }

    public static MealPlanViewModel of(MealPlan mealPlan, FragmentActivity activity) {
        MealPlanViewModel mpViewModel = new ViewModelProvider(activity).get(MealPlanViewModel.class);
        mpViewModel.mealPlan.setValue(mealPlan);
        Log.e("MP_ADAPTER_MPVM", mpViewModel.getIngredients().toString());
        return mpViewModel;
    }

    public static MealPlanViewModel of(int idx, FragmentActivity activity) {
        Environment env = Environment.of((AppCompatActivity) activity);
        MealPlanViewModel mpViewModel = new ViewModelProvider(activity).get(MealPlanViewModel.class);
        mpViewModel.idx.setValue(idx);
        mpViewModel.mealPlan.setValue(env.getMealPlans().getList().get(idx));
        Log.e("MP_ADAPTER_MPVM", mpViewModel.getIngredients().toString());
        return mpViewModel;
    }

    public MutableLiveData<MealPlan> getLive() {
        return mealPlan;
    }

    /**
     * notifies all listeners of the ingredient stubs of a change
     */
    public void forceSignalUpdate() {
        mealPlan.setValue(mealPlan.getValue());
    }

    public MealPlan getMealPlan() {
        if (mealPlan.getValue() == null) {
            MealPlan mp = new MealPlan("", new ArrayList<Recipe>(),
                    new ArrayList<Ingredient>(), new Date(), new Date(), "testing comments", 0);
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

    public void setMealPlan(MealPlan mealPlan) {
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

    public void setIdx(MutableLiveData<Integer> idx) {
        this.idx = idx;
    }

    public MutableLiveData<Integer> getIdx() {
        return idx;
    }

        /**
         * Notifies all listeners to the meal plan of a change
         */
        public void forceNotify () {

            this.setMealPlan(this.getMealPlan());

            // For tests, remove later
            try {
                Log.e("test", getMealPlan().getIngredients().get(0).getDisplayDate());
            } catch (Exception e) {

            }
        }

}