package com.cmput301f22t09.shell379.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class RecipeSelectIngredientsAdapter extends RecyclerView.Adapter<RecipeSelectIngredientsAdapter.RecipeSelectIngredientsViewHolder> {

    private ArrayList<Ingredient> ingredients;

    public class RecipeSelectIngredientsViewHolder extends RecyclerView.ViewHolder {

        TextView description;
        TextView bestBefore;
        TextView location;
        NumberPicker amount;
        TextView category;

        public RecipeSelectIngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.description = (TextView) itemView.findViewById(R.id.rsi_description);
            this.bestBefore = (TextView) itemView.findViewById(R.id.rsi_bestBefore);
            this.location = (TextView) itemView.findViewById(R.id.rsi_location);
            this.amount = (NumberPicker) itemView.findViewById(R.id.rsi_amount_numberPicker);
            this.category = (TextView) itemView.findViewById(R.id.rsi_category);
        }
    }

    public RecipeSelectIngredientsAdapter(ArrayList<Ingredient> data) {
        this.ingredients = data;
    }

    @NonNull
    @Override
    public RecipeSelectIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_select_ingredients_content, parent, false);
        RecipeSelectIngredientsViewHolder rsiViewHolder = new RecipeSelectIngredientsViewHolder(view);
        return rsiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeSelectIngredientsViewHolder holder, int position) {
        TextView description = holder.description;
        TextView bestBefore = holder.bestBefore;
        TextView location = holder.location;
        NumberPicker amount = holder.amount;
        TextView category = holder.category;

        description.setText(ingredients.get(position).getDescription());
        bestBefore.setText(ingredients.get(position).getBestBefore().toString());
        location.setText(ingredients.get(position).getLocation());
        amount.setValue(ingredients.get(position).getAmount());
        category.setText(ingredients.get(position).getCategory());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

}
