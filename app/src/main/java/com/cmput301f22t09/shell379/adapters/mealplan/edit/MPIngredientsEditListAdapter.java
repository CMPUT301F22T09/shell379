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
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.util.ArrayList;
import java.util.Date;

/**
 * Adapter for ingredients recycler view in view selected ingredients page for recipes.
 */
public class MPIngredientsEditListAdapter extends MPObjectWrapperListAdapter {

    public MPIngredientsEditListAdapter(MealPlanViewModel viewModel) {
        super(viewModel);
    }

    /**
     * Removes an item
     * @param index index of the item
     */
    @Override
    protected void removeItem(int index) {
        viewModel.getIngredients().remove(index);
        viewModel.forceNotify();
    }

    /**
     * adds one to the servings
     * @param index index of the item
     */
    @Override
    protected void addServings(int index) {
        MealPlanWrapper ingredient =  viewModel.getIngredientAtIdx(index);
        ingredient.setServings(ingredient.getServings() + 1);
        viewModel.forceNotify();
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
        viewModel.forceNotify();
    }

    /**
     *  updates  eating date of an item
     * @param index index of item
     * @param newDate new date
     */
    @Override
    protected void updateDate(int index, Date newDate) {
        MealPlanWrapper ingredient =  viewModel.getIngredientAtIdx(index);
        ingredient.setDate(newDate);
        viewModel.forceNotify();
    }

    /**
     *  Gets an item at a certain index
     * @param index index of item
     * @return item at index
     */
    @Override
    protected MealPlanWrapper getItemAtIndex(int index) {
        return viewModel.getIngredientAtIdx(index);
    }

    /**
     * Gets size of list inside of view model for the adapter's getSize() method
     * @return
     */
    @Override
    protected int getSizeInternal() {
        return  viewModel.getIngredients().size();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MPEditIngredientListViewHolder holder, int position) {
        super.onBindViewHolder( holder, position);
        String units = ((Ingredient)getItemAtIndex(position).getObj()).getUnit();
        holder.amountLabel.setText("Amount ( "+units+" )");
    }
}
