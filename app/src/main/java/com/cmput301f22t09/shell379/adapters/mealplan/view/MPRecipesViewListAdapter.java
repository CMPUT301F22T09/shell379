package com.cmput301f22t09.shell379.adapters.mealplan.view;

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

/**
 * Adapter for recycler view in view selected ingredients page for recipes.
 */
public class MPRecipesViewListAdapter extends RecyclerView.Adapter<MPRecipesViewListAdapter.MPViewRecipeListViewHolder> {
    private MealPlanViewModel viewModel;

    /**
     * Custom view holder for describing each ingredient's view
     */
    public class MPViewRecipeListViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView amount;
        TextView date;

        public MPViewRecipeListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.mpv_name);
            this.amount = (TextView) itemView.findViewById(R.id.mpv_servings_val);
            this.date = (TextView) itemView.findViewById(R.id.mpv_date_val);
        }

        public View getItemView(){
            return itemView;
        }
    }

    public MPRecipesViewListAdapter(MealPlanViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Updates the recycler view and re-renders it
     * @param newRecipes the new list of recipes for the recycler view
     */
    public void updateRecipes(ArrayList<MealPlanWrapper<Recipe>> newRecipes){
        viewModel.setRecipesRaw(newRecipes);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MPViewRecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mealplan_view_list_item_14, parent, false);
        return new MPViewRecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MPViewRecipeListViewHolder holder, int position) {
        TextView name = holder.name;
        TextView amount = holder.amount;
        TextView date = holder.date;

        name.setText(viewModel.getRecipeAtIdx(position).getName());
        amount.setText(Integer.toString(viewModel.getRecipeAtIdx(position).getServings()
                *((Recipe)viewModel.getRecipeAtIdx(position).getObj()).getServings()));
        date.setText(viewModel.getRecipeAtIdx(position).getDisplayDate());
    }

    @Override
    public int getItemCount() {
        return viewModel.getRecipes().size();
    }

    public ArrayList<MealPlanWrapper<Ingredient>> getIngredients(){
        return viewModel.getIngredients();
    }
}
