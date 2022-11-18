package com.cmput301f22t09.shell379.adapters.mealplan.edit;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Adapter for recycler view in view selected ingredients page for recipes.
 */
public class MPRecipesEditListAdapter extends MPObjectWrapperListAdapter {

    public MPRecipesEditListAdapter(MealPlanViewModel viewModel) {
        super(viewModel);
    }

    @Override
    protected void removeItem(int index) {
        viewModel.getRecipes().remove(index);
        viewModel.forceNotify();
    }

    /**
     * adds a multiple of the base recipes servings
     * @param index index of the item
     */
    @Override
    protected void addServings(int index) {
        MealPlanWrapper recipe =  viewModel.getRecipeAtIdx(index);
        int recipeServes = ((Recipe)recipe.getObj()).getServings();
        int currentServingMultiplier = recipe.getServings()/recipeServes;
        recipe.setServings(recipeServes * (currentServingMultiplier + 1));
        viewModel.forceNotify();
    }

    /**
     * removes a multiple of the base recipes servings
     * @param index index of the item
     */
    @Override
    protected void subServings(int index) {
        MealPlanWrapper recipe =  viewModel.getRecipeAtIdx(index);
        int recipeServes = ((Recipe)recipe.getObj()).getServings();
        int currentServingMultiplier = recipe.getServings()/recipeServes;
        if(currentServingMultiplier > 1){
            recipe.setServings(recipeServes * (currentServingMultiplier - 1));
        }

        viewModel.forceNotify();
    }


    @Override
    protected void updateDate(int index, Date newDate) {
        MealPlanWrapper recipe = viewModel.getRecipeAtIdx(index);
        recipe.setDate(newDate);
        viewModel.forceNotify();
    }

    @Override
    protected MealPlanWrapper getItemAtIndex(int index) {
        return viewModel.getRecipeAtIdx(index);
    }

    @Override
    protected int getSizeInternal() {
        return  viewModel.getRecipes().size();
    }
}
