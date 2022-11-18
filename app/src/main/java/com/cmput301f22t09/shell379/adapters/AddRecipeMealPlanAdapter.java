package com.cmput301f22t09.shell379.adapters;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.collections.LiveCollection;
import com.cmput301f22t09.shell379.fragments.RecipeListFragmentDirections;

import java.util.ArrayList;

public class AddRecipeMealPlanAdapter extends RecyclerView.Adapter<AddRecipeMealPlanAdapter.AddRecipeMealPlanViewHolder>{

    private ArrayList<Recipe> RecipeinMealPlan;
    private Environment envViewModel;
//    private MealPlanAdapter.AdaptorListener mealPlanListener;
    private RecipeInMealPlanListener recipeInMealPlanListener;

    public interface RecipeInMealPlanListener{
//         to-do (implement the editRecipeMealplan function)
        public void editRecipeMealPlan(int index);
    }


    public class AddRecipeMealPlanViewHolder extends RecyclerView.ViewHolder {
        TextView recipeName;
        TextView prepTime;
        TextView servings;
        TextView category;

        /**
         * Construct the MealPlanViewHolder class
         * @param itemView
         */
        public AddRecipeMealPlanViewHolder(@NonNull View itemView) {
            super(itemView);
            this.recipeName = itemView.findViewById(R.id.rli_name);
            this.prepTime = itemView.findViewById(R.id.rli_prep_time_textView);
            this.servings = itemView.findViewById(R.id.rli_servings_textView);
            this.category = itemView.findViewById(R.id.rli_category_textView);
        }

        public View getItemView(){
            return itemView;
        }
    }

    public AddRecipeMealPlanAdapter(ArrayList<Recipe> RecipeinMealPlan, RecipeInMealPlanListener recipeInMealPlanListener, Environment envViewModel){
        this.envViewModel = envViewModel;
        this.RecipeinMealPlan = RecipeinMealPlan;
        this.recipeInMealPlanListener = recipeInMealPlanListener;
    }


    @NonNull
    @Override
    public AddRecipeMealPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_list_item_7, parent, false);
        AddRecipeMealPlanAdapter.AddRecipeMealPlanViewHolder recipeMealPlanViewHolder = new AddRecipeMealPlanAdapter.AddRecipeMealPlanViewHolder(view);
        return recipeMealPlanViewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull AddRecipeMealPlanAdapter.AddRecipeMealPlanViewHolder holder, int position) {
        TextView recipeName = holder.recipeName;
        TextView prepTime = holder.prepTime;
        TextView servings = holder.servings;
        TextView category = holder.category;

        recipeName.setText(RecipeinMealPlan.get(holder.getAdapterPosition()).getTitle());
        prepTime.setText(RecipeinMealPlan.get(holder.getAdapterPosition()).getPreparationTime().toString());
        servings.setText(RecipeinMealPlan.get(holder.getAdapterPosition()).getServings().toString());
        category.setText(RecipeinMealPlan.get(holder.getAdapterPosition()).getCategory());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                public void onClick(View view) {
                recipeOnClick(holder.getAdapterPosition());
            }
//                int recipePosition = holder.getAdapterPosition();
//                navController.navigate(RecipeListFragmentDirections.actionRecipeListFragmentToEditRecipe(recipePosition));
            }
        });
    }


    /**
     *  Responds to an recipe item being clicked in the recyclerView.
     *  Navigates to viewing the ingredient
     * @param i index of ingredient in the edit recipe view model
     */
//     to-do for the view model???
    public void recipeOnClick(int i) {
        Recipe a = RecipeinMealPlan.get(i);
        ArrayList<Recipe> recipes = editRecipeViewModel.getSelectedIngredients();
        int index = recipes.indexOf(a);
        RecipeInMealPlanListener.editRecipeIngredient(index);
    }


    @Override
    public int getItemCount() {
        return RecipeinMealPlan.size();
    }



}