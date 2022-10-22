package com.cmput301f22t09.shell379.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Recipe;

import java.util.ArrayList;

/**
 * This class is a custom RecyclerView adapter which helps display Recipe data in the RecyclerView
 * TODO: Cite these sources since they were referenced while working on this adapter:
 * TODO:    - https://www.digitalocean.com/community/tutorials/android-recyclerview-android-cardview-example-tutorial
 * TODO:    -
 */
public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder>{

    private ArrayList<Recipe> recipes;

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        TextView recipeName;
        TextView prepTime;
        TextView servings;
        TextView category;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.recipeName = (TextView) itemView.findViewById(R.id.rli_name);
            this.prepTime = (TextView) itemView.findViewById(R.id.rli_prep_time_textView);
            this.servings = (TextView) itemView.findViewById(R.id.rli_servings_textView);
            this.category = (TextView) itemView.findViewById(R.id.rli_category_textView);
        }
    }

    public RecipeListAdapter(ArrayList<Recipe> data) {
        this.recipes = data;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item, parent, false);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        TextView recipeName = holder.recipeName;
        TextView prepTime = holder.prepTime;
        TextView servings = holder.servings;
        TextView category = holder.category;

        recipeName.setText(recipes.get(position).getTitle());
        prepTime.setText(recipes.get(position).getPreparationTime().toString());
        servings.setText(recipes.get(position).getServings().toString());
        category.setText(recipes.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}
