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

/**
 * Adapter for recycler view in view selected ingredients page for recipes.
 */
public class MPRecipesEditListAdapter extends RecyclerView.Adapter<MPRecipesEditListAdapter.MPEditRecipeListViewHolder> {
    private MealPlanViewModel viewModel;

    /**
     * Custom view holder for describing each ingredient's view
     */
    public class MPEditRecipeListViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView amount;
        TextView date;

        public MPEditRecipeListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.mpv_name);
            this.amount = (TextView) itemView.findViewById(R.id.mpv_servings_textView);
            this.date = (TextView) itemView.findViewById(R.id.mpv_date_textView);
        }

        public View getItemView(){
            return itemView;
        }
    }

    public MPRecipesEditListAdapter(MealPlanViewModel viewModel) {
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
    public MPEditRecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.mealplan_view_list_item_14, parent, false);
        return new MPEditRecipeListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MPEditRecipeListViewHolder holder, int position) {
        TextView name = holder.name;
        TextView amount = holder.amount;
        TextView date = holder.date;

        name.setText(viewModel.getIngredientAtIdx(position).getName());
        amount.setText(viewModel.getIngredientAtIdx(position).getServings());
        date.setText(viewModel.getIngredientAtIdx(position).getDisplayDate());
//        holder.getItemView().setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                itemOnClick(holder.getAdapterPosition());
//            }
//        });
    }
//
//    /**
//     *  Responds to an ingredient item being clicked in the recyclerView.
//     *  Navigates to viewing the ingredient
//     * @param i index of ingredient in the edit recipe view model
//     */
//    public void itemOnClick(int i) {
//        MealPlanWrapper<Ingredient> a = viewModel.getIngredientAtIdx(i);
//    }

    @Override
    public int getItemCount() {
        return viewModel.getIngredients().size();
    }

    public ArrayList<MealPlanWrapper<Ingredient>> getIngredients(){
        return viewModel.getIngredients();
    }
}
