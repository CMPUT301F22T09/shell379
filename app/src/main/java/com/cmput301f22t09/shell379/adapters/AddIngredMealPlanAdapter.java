package com.cmput301f22t09.shell379.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;

import java.util.ArrayList;

/**
 *  Adapter for list that shows ingredients that you can add to a meal plan
 */
public class AddIngredMealPlanAdapter extends RecyclerView.Adapter<AddIngredMealPlanAdapter.AddIngredMealPlanViewHolder>{

    private ArrayList<Ingredient> ingredients;
    private AddIngredMealPlanAdapter.IngredInMealPlanListener ingredInMealPlanListener;
    private MealPlanViewModel mealPlanViewModel;

    /**
     *  Observer who will handle navigating to the fragment for setting the date and amount.
     */
    public interface IngredInMealPlanListener{
        public void navigateToPickDate(int index);
    }

    public class AddIngredMealPlanViewHolder extends RecyclerView.ViewHolder {
        TextView ingredientName;
        TextView bestBefore;
        TextView location;
        TextView category;

        /**
         * Construct the MealPlanViewHolder class
         * @param itemView
         */
        public AddIngredMealPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            this.ingredientName = (TextView) itemView.findViewById(R.id.ingredient_name);
            this.bestBefore = (TextView) itemView.findViewById(R.id.best_before_date_textView);
            this.category = (TextView) itemView.findViewById(R.id.category_textView);
            this.location = (TextView) itemView.findViewById(R.id.location_textView);
        }

        public View getItemView(){
            return itemView;
        }
    }

    public AddIngredMealPlanAdapter(ArrayList<Ingredient> ingredients, AddIngredMealPlanAdapter.IngredInMealPlanListener ingredInMealPlanListener, MealPlanViewModel mealPlanViewModel){
        this.ingredients = ingredients;
        this.ingredInMealPlanListener = ingredInMealPlanListener;
        this.mealPlanViewModel = mealPlanViewModel;
    }

    @NonNull
    @Override
    public AddIngredMealPlanAdapter.AddIngredMealPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.ingredient_content_2, parent, false);
        AddIngredMealPlanAdapter.AddIngredMealPlanViewHolder ingredMealPlanViewHolder = new AddIngredMealPlanAdapter.AddIngredMealPlanViewHolder(view);
        return ingredMealPlanViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AddIngredMealPlanViewHolder holder, int position) {
        TextView ingredientName = holder.ingredientName;
        TextView bestBefore = holder.bestBefore;
        TextView location = holder.location;
        TextView category = holder.category;

        ingredientName.setText(ingredients.get(position).getDescription());
        bestBefore.setText(ingredients.get(position).getBestBeforeFormatted());
        category.setText(ingredients.get(position).getCategory());
        location.setText(ingredients.get(position).getLocation());


        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredInMealPlanListener.navigateToPickDate(holder.getAdapterPosition());

            }

        }
        );
    }

    /**
     * sets the adapter's ingredients
     * @param newIngredient ingredients to update with
     */
    public void updateIngredient(ArrayList<Ingredient> newIngredient){
        ingredients = newIngredient;
        notifyDataSetChanged();
    }

    public ArrayList<Ingredient> getIngredients(){
        return ingredients;
    }

    @Override
    public int getItemCount() {
            return ingredients.size();
    }
}

