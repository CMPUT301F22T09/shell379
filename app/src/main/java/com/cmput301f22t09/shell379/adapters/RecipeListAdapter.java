package com.cmput301f22t09.shell379.adapters;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.fragments.RecipeListFragment;
import com.cmput301f22t09.shell379.fragments.RecipeListFragmentDirections;

import java.util.ArrayList;

/**
 * This class is a custom RecyclerView adapter which helps display Recipe data in the RecyclerView.
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>{

    private ArrayList<Recipe> recipes;
    private RecipeListFragment recipeListFragment;
    private NavController navController;

    /**
     * Custom ViewHolder to describe each recipe list item's view.
     */
    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeName;
        TextView prepTime;
        TextView servings;
        TextView category;

        public RecipeViewHolder(@NonNull View itemView, Fragment fragment) {
            super(itemView);
            this.recipeName = itemView.findViewById(R.id.rli_name);
            this.prepTime = itemView.findViewById(R.id.rli_prep_time_textView);
            this.servings = itemView.findViewById(R.id.rli_servings_textView);
            this.category = itemView.findViewById(R.id.rli_category_textView);
        }

        // Used this to reference how to create an on click listener for recycler view items
        // https://stackoverflow.com/questions/24885223/why-doesnt-recyclerview-have-onitemclicklistener
        @Override
        public void onClick(View view) {
            //navController.navigate(RecipeListFragmentDirections.actionRecipeListFragmentToEditRecipe());
        }
    }

    public RecipeListAdapter(ArrayList<Recipe> data, RecipeListFragment recipeListFragment) {
        this.recipes = data;
        this.recipeListFragment = recipeListFragment;
        this.navController = NavHostFragment.findNavController(recipeListFragment);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view, recipeListFragment);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        TextView recipeName = holder.recipeName;
        TextView prepTime = holder.prepTime;
        TextView servings = holder.servings;
        TextView category = holder.category;

        recipeName.setText(recipes.get(holder.getAdapterPosition()).getTitle());
        prepTime.setText(recipes.get(holder.getAdapterPosition()).getPreparationTime().toString());
        servings.setText(recipes.get(holder.getAdapterPosition()).getServings().toString());
        category.setText(recipes.get(holder.getAdapterPosition()).getCategory());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int recipePosition = holder.getAdapterPosition();
                Log.e("debug", "clicked recipe list");
                navController.navigate(RecipeListFragmentDirections.actionRecipeListFragmentToEditRecipe(recipePosition));
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public void updateRecipes(ArrayList<Recipe> newRecipes) {
        recipes = newRecipes;
        notifyDataSetChanged();
    }

    public Recipe getRecipe(int index) {
        Recipe recipe = recipes.get(index);
        return recipe;
    }

    public ArrayList<Recipe> getRecipes(){
        return recipes;
    }
}
