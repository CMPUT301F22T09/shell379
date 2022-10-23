package com.cmput301f22t09.shell379.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;

import java.util.ArrayList;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder>{
    private ArrayList<Ingredient> ingredients;

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        TextView ingredientName;
        TextView serving;
        TextView bestBefore;

        public IngredientViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ingredientName = (TextView) itemView.findViewById(R.id.food_name_text);
            this.serving = (TextView) itemView.findViewById(R.id.serving_text);
            this.bestBefore = (TextView) itemView.findViewById(R.id.best_before_text);
        }
    }

    public IngredientAdapter(ArrayList<Ingredient> data) {
        this.ingredients = data;
    }

    @NonNull
    @Override
    public IngredientAdapter.IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_in_recipe_9, parent, false);
        IngredientAdapter.IngredientViewHolder ingredientViewHolder = new IngredientAdapter.IngredientViewHolder(view);
        return ingredientViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.IngredientViewHolder holder, int position) {
        TextView ingredientName = holder.ingredientName;
        TextView serving = holder.serving;
        TextView bestBefore = holder.bestBefore;

        ingredientName.setText(ingredients.get(position).getDescription());
        bestBefore.setText(ingredients.get(position).getBestBefore().toString());
        serving.setText(ingredients.get(position).getAmount().toString());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }
}
