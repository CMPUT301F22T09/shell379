package com.cmput301f22t09.shell379.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;

import java.util.ArrayList;

/**
 * Adapter for recycler view in select ingredients page for recipes.
 */
public class RecipeIngredientsListAdapter extends RecyclerView.Adapter<RecipeIngredientsListAdapter.RecipeIngredientListViewHolder> {

    private ArrayList<Ingredient> recipeIngredients;
    private RecipeIngredientListListener recipeIngredientListListener;

    public interface RecipeIngredientListListener{
        public void editRecipeIngredient(int index);
    }


    /**
     * Custom view holder for describing each ingredient's view
     */
    public class RecipeIngredientListViewHolder extends RecyclerView.ViewHolder {

        TextView description;
        TextView amount;
        TextView category;

        public RecipeIngredientListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.description = (TextView) itemView.findViewById(R.id.rsi_description);
            this.amount = (TextView) itemView.findViewById(R.id.rsi_amount);
            this.category = (TextView) itemView.findViewById(R.id.rsi_category);
        }
    }

    public RecipeIngredientsListAdapter( ArrayList<Ingredient> recipeIngredients, RecipeIngredientListListener recipeIngredientListListener) {
        this.recipeIngredients = recipeIngredients;
        this.recipeIngredientListListener =recipeIngredientListListener;
    }

    public void updateList(ArrayList<Ingredient> newIngredients,ArrayList<Ingredient> newRecipeIngredients){
        recipeIngredients = newRecipeIngredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeIngredientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_ingredients_list_content, parent, false);
        RecipeIngredientListViewHolder rsiViewHolder = new RecipeIngredientListViewHolder(view);
        return rsiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeIngredientListViewHolder holder, int position) {
        TextView description = holder.description;
        TextView amount = holder.amount;
        TextView category = holder.category;

        description.setText(recipeIngredients.get(position).getDescription());
        category.setText(recipeIngredients.get(position).getCategory());
        amount.setText(recipeIngredients.get(position).getAmount() +" "+ recipeIngredients.get(position).getUnit());
    }

    @Override
    public int getItemCount() {
        return recipeIngredients.size();
    }
}
