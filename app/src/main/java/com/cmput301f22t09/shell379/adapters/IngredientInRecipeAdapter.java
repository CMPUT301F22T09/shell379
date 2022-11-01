package com.cmput301f22t09.shell379.adapters;

import android.graphics.Color;
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

import java.util.ArrayList;

public class IngredientInRecipeAdapter extends RecyclerView.Adapter<IngredientInRecipeAdapter.IngredientInRecipeViewHolder> {
    private ArrayList<Ingredient> ingredients;
    private int selectedPos = RecyclerView.NO_POSITION;

    public class IngredientInRecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView name;
        TextView amount;
        TextView bestBefore;

        public IngredientInRecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.food_name_text);
            this.amount =  (TextView) itemView.findViewById(R.id.amount_text);
            this.bestBefore =  (TextView) itemView.findViewById(R.id.best_before_text);

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

    public IngredientInRecipeAdapter(ArrayList<Ingredient> data) {
        this.ingredients = data;
    }

    @NonNull
    @Override
    public IngredientInRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredients_in_recipe_9, parent, false);
        IngredientInRecipeViewHolder ingredientInRecipeViewHolder = new IngredientInRecipeViewHolder(view);
        return ingredientInRecipeViewHolder;
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull IngredientInRecipeAdapter.IngredientInRecipeViewHolder holder, int position) {
        TextView name = holder.name;
        TextView amount = holder.amount;
        TextView bestBefore = holder.bestBefore;

        name.setText(ingredients.get(position).getDescription());
        amount.setText(ingredients.get(position).getAmount().toString());
        bestBefore.setText(ingredients.get(position).getBestBeforeFormatted());
        holder.itemView.setSelected(selectedPos == position);
        holder.itemView.setBackgroundColor(selectedPos == position ? Color.GRAY : Color.TRANSPARENT);

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public void updateIngredient(ArrayList<Ingredient> newIngredient){
        ingredients = newIngredient;
        notifyDataSetChanged();
    }

    public int getSelectedPos() {
        return selectedPos;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void removeIngredient(int index) {
        ingredients.remove(index);
    }
}
