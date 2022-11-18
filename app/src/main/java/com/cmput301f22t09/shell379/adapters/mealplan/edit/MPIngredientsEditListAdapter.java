package com.cmput301f22t09.shell379.adapters.mealplan.edit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Adapter for recycler view in view selected ingredients page for recipes.
 */
public class MPIngredientsEditListAdapter extends MPObjectWrapperListAdapter {

    public MPIngredientsEditListAdapter(MealPlanViewModel viewModel) {
        super(viewModel);
    }

    @Override
    protected void removeItem(int index) {
        viewModel.getIngredients().remove(index);
        viewModel.notify();
    }

    /**
     * adds one to the servings
     * @param index index of the item
     */
    @Override
    protected void addServings(int index) {
        MealPlanWrapper ingredient =  viewModel.getIngredientAtIdx(index);
        ingredient.setServings(ingredient.getServings() + 1);
    }

    /**
     * removes one from the servings
     * @param index index of the item
     */
    @Override
    protected void subServings(int index) {
        MealPlanWrapper ingredient =  viewModel.getIngredientAtIdx(index);
        if(ingredient.getServings()  >= 1){
            ingredient.setServings(ingredient.getServings() - 1);
        }
    }


    @Override
    protected void updateDate(int index, Date newDate) {
        MealPlanWrapper ingredient =  viewModel.getIngredientAtIdx(index);
        ingredient.setDate(newDate);
    }

    @Override
    protected MealPlanWrapper getItemAtIndex(int index) {
        return viewModel.getIngredientAtIdx(index);
    }
}
