package com.cmput301f22t09.shell379.adapters;

import android.graphics.Color;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.fragments.EditRecipeFragment;
import com.cmput301f22t09.shell379.fragments.RecipeListFragment;

import java.util.ArrayList;

/**
 * Adapter for recycler view in edit recipes for choosing ingredients
 */
public class IngredientInRecipeAdapter extends RecyclerView.Adapter<IngredientInRecipeAdapter.IngredientInRecipeViewHolder> {
    private ArrayList<Ingredient> ingredients;
    private int selectedPos = RecyclerView.NO_POSITION;
    private EditRecipeFragment editRecipeFragment;

    public class IngredientInRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView name;
        private TextView amount;

        public IngredientInRecipeViewHolder(@NonNull View itemView, Fragment fragment) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.food_name_text);
            this.amount =  (TextView) itemView.findViewById(R.id.amount_text);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            // Below line is just like a safety check, because sometimes holder could be null,
            // in that case, getAdapterPosition() will return RecyclerView.NO_POSITION
            if (getAdapterPosition() == RecyclerView.NO_POSITION) return;

            // Updating old as well as new positions
            notifyItemChanged(selectedPos);
            selectedPos = getAdapterPosition();
            notifyItemChanged(selectedPos);
        }
    }

    public IngredientInRecipeAdapter(ArrayList<Ingredient> data, EditRecipeFragment editRecipeFragment) {
        this.ingredients = data;
        this.editRecipeFragment = editRecipeFragment;
    }

    @NonNull
    @Override
    public IngredientInRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_in_recipe_9, parent, false);
        IngredientInRecipeViewHolder ingredientInRecipeViewHolder = new IngredientInRecipeViewHolder(view, editRecipeFragment);
        return ingredientInRecipeViewHolder;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull IngredientInRecipeViewHolder holder, int position) {
        TextView name = holder.name;
        TextView amount = holder.amount;

        name.setText(ingredients.get(position).getDescription());
        amount.setText(ingredients.get(position).getAmount().toString());
        holder.itemView.setSelected(selectedPos == position);
        holder.itemView.setBackgroundColor(selectedPos == position ? Color.GRAY : Color.TRANSPARENT);

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void removeIngredient(int index) {
        ingredients.remove(index);
    }
}
