package com.cmput301f22t09.shell379.adapters.mealplan.edit;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Adapter for Recipe recycler view in view selected ingredients page for recipes.
 */
public class MPRecipesEditListAdapter extends MPObjectWrapperListAdapter {

    public MPRecipesEditListAdapter(MealPlanViewModel viewModel) {
        super(viewModel);
    }


    /**
     * Removes an item
     * @param index index of the item
     */
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
        int currentServingMultiplier = recipe.getServings();
        recipe.setServings(currentServingMultiplier + 1);
        viewModel.forceNotify();
    }

    /**
     * removes a multiple of the base recipes servings
     * @param index index of the item
     */
    @Override
    protected void subServings(int index) {
        MealPlanWrapper recipe =  viewModel.getRecipeAtIdx(index);
        int currentServingMultiplier = recipe.getServings();
        if(currentServingMultiplier > 1){
            recipe.setServings(currentServingMultiplier - 1);
        }

        viewModel.forceNotify();
    }

    /**
     *  updates  eating date of a recipe
     * @param index index of item
     * @param newDate new date
     */
    @Override
    protected void updateDate(int index, Date newDate) {
        MealPlanWrapper recipe = viewModel.getRecipeAtIdx(index);
        recipe.setDate(newDate);
        viewModel.forceNotify();
    }

    /**
     *  Gets an item at a certain index
     * @param index index of item
     * @return item at index
     */
    @Override
    protected MealPlanWrapper getItemAtIndex(int index) {
        return viewModel.getRecipeAtIdx(index);
    }

    /**
     * Gets size of list inside of view model for the adapter's getSize() method
     * @return
     */
    @Override
    protected int getSizeInternal() {
        return  viewModel.getRecipes().size();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MPEditIngredientListViewHolder holder, int position) {
        super.onBindViewHolder(holder,position);
        int recipeServings = ((Recipe)getItemAtIndex(position).getObj()).getServings();
        int wrapperServings = getItemAtIndex(position).getServings();
        holder.amount.setText( Integer.toString(wrapperServings*recipeServings) );
    }
}
