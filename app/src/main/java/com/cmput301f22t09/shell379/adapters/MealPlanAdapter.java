package com.cmput301f22t09.shell379.adapters;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.collections.LiveCollection;

import java.util.ArrayList;

public class MealPlanAdapter {
    private ArrayList<MealPlan> mealPlans;
    private Environment envViewModel;
    private IngredientAdapter.AdaptorListener mealPlanListener;
    private int mealPlanIndex;

    public MealPlanAdapter(ArrayList<MealPlan> data, Environment envViewModel, IngredientAdapter.AdaptorListener ingredientListener){
        this.envViewModel = envViewModel;
        this.mealPlans = data;
        this.mealPlanListener = ingredientListener;
    }


    @Override
    public int getItemCount() {
        return mealPlans.size();
    }


    public void updateMealPlan(ArrayList<MealPlan> newMealPlan){
        mealPlans = newMealPlan;
        notifyDataSetChanged();
    }


    public void mealPlanOnClick(int i) {
        MealPlan m = mealPlans.get(i);
        LiveCollection<MealPlan> mealPlanCollection = envViewModel.getMealPlans();
        mealPlanCollection.getList().indexOf(m);
        mealPlanListener.navigateToViewIngredient(i);
    }

// maybe dont need this (only for testing purpose)
//    public MealPlan getMealPlan(int index){
//        MealPlan mealPlan = mealPlans.get(index);
//        return mealPlan;
//    }
}


