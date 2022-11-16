package com.cmput301f22t09.shell379.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.vm.EditRecipeViewModel;

import java.util.ArrayList;

/**
 * Adapter for recycler view in view selected ingredients page for recipes.
 */
public class RecipeIngredientsListAdapter extends RecyclerView.Adapter<RecipeIngredientsListAdapter.RecipeIngredientListViewHolder> {

    private ArrayList<IngredientStub> recipeIngredients;
    private RecipeIngredientListListener recipeIngredientListListener;
    private EditRecipeViewModel editRecipeViewModel;

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

        public View getItemView(){
            return itemView;
        }
    }

    public RecipeIngredientsListAdapter( ArrayList<IngredientStub> recipeIngredients, RecipeIngredientListListener recipeIngredientListListener, EditRecipeViewModel editRecipeViewModel) {
        this.recipeIngredients = recipeIngredients;
        this.recipeIngredientListListener =recipeIngredientListListener;
        this.editRecipeViewModel = editRecipeViewModel;
    }

    /**
     * Updates the recycler view and re-renders it
     * @param newRecipeIngredients the new list of ingredient stubs for the recycler view
     */
    public void updateList(ArrayList<IngredientStub> newRecipeIngredients){
        recipeIngredients = newRecipeIngredients;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecipeIngredientListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_ingredients_list_content_23, parent, false);
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
        holder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ingredientOnClick(holder.getAdapterPosition());
            }
        });
    }

    /**
     *  Responds to an ingredient item being clicked in the recyclerView.
     *  Navigates to viewing the ingredient
     * @param i index of ingredient in the edit recipe view model
     */
    public void ingredientOnClick(int i) {
        IngredientStub a = recipeIngredients.get(i);
        ArrayList<IngredientStub> ingredients = editRecipeViewModel.getSelectedIngredients();
        int index = ingredients.indexOf(a);
        recipeIngredientListListener.editRecipeIngredient(index);
    }

    @Override
    public int getItemCount() {
        return recipeIngredients.size();
    }

    public ArrayList<IngredientStub> getIngredients(){
        return recipeIngredients;
    }
}
