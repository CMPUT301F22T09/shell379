package com.cmput301f22t09.shell379.adapters;

import android.text.Editable;
import android.text.TextWatcher;
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
import com.cmput301f22t09.shell379.data.IngredientStub;

import java.util.ArrayList;

/**
 * Adapter for recycler view in select ingredients page for recipes.
 */
public class RecipeSelectIngredientsAdapter extends RecyclerView.Adapter<RecipeSelectIngredientsAdapter.RecipeSelectIngredientsViewHolder> {

    private ArrayList<Ingredient> ingredients;
    // This contains "dupe" stub ingredients
    private ArrayList<IngredientStub> checkedIngredients;


    /**
     * Custom view holder for describing each ingredient's view
     */
    public class RecipeSelectIngredientsViewHolder extends RecyclerView.ViewHolder {

        TextView description;
        TextView unit;
        TextView category;
        CheckBox checkbox;
        EditText inputAmount;

        public RecipeSelectIngredientsViewHolder(@NonNull View itemView) {
            super(itemView);
            this.description = (TextView) itemView.findViewById(R.id.rsi_description);
            this.unit = (TextView) itemView.findViewById(R.id.rsi_unit);
            this.category = (TextView) itemView.findViewById(R.id.rsi_category);
            this.checkbox = (CheckBox) itemView.findViewById(R.id.rsi_checkBox);
            this.inputAmount = (EditText) itemView.findViewById(R.id.rsi_amount);
        }
    }

    public RecipeSelectIngredientsAdapter(ArrayList<Ingredient> ingredients, ArrayList<Ingredient> recipeIngredients) {
        this.ingredients = ingredients;
        this.checkedIngredients = new ArrayList<>();
    }

    /**
     * Gives information on whether all ingredients have valid amount numbers
     * @return true is all ingredients have non zero and non null amounts
     */
    public boolean selectedIngsHaveAmounts(){
        for (int i = 0 ; i < checkedIngredients.size();i++){
            if(checkedIngredients.get(i).getAmount() == null){
              return false;
            }
        }
        return true;
    }

    @NonNull
    @Override
    public RecipeSelectIngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_select_ingredients_content_10, parent, false);
        RecipeSelectIngredientsViewHolder rsiViewHolder = new RecipeSelectIngredientsViewHolder(view);
        return rsiViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeSelectIngredientsViewHolder holder, int position) {
        TextView description = holder.description;
        TextView unit = holder.unit;
        TextView category = holder.category;
        EditText inputAmount = holder.inputAmount;
        holder.checkbox.setChecked(false);
        holder.inputAmount.setText("");

        description.setText(ingredients.get(position).getDescription());
        category.setText(ingredients.get(position).getCategory());
        unit.setText(ingredients.get(position).getUnit());

        // referenced for how to detect when a checkbox is clicked
        // url: https://stackoverflow.com/questions/51778606/android-how-to-check-if-a-checkbox-is-checked-in-an-item-of-a-recyclerview
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    String amount = holder.inputAmount.getText().toString();
                    System.out.println(amount);
                    Ingredient originalIngredient = ingredients.get(holder.getAdapterPosition());
                    IngredientStub dupeIngredient = createDupeIngredient(holder, originalIngredient);
                    checkedIngredients.add(dupeIngredient);
                }
                else {
                    // get index of dupe ingredient and remove it
                    Ingredient originalIngredient = ingredients.get(holder.getAdapterPosition());
                    IngredientStub dupeIngredient = createDupeIngredient(holder, originalIngredient);

                    int removeIndex = -1;
                    for (int i = 0; i < checkedIngredients.size(); i++) {
                        if (dupeIngredient.looseEquals(checkedIngredients.get(i))) {
                            removeIndex = i;
                            break;
                        }
                    }
                    if(removeIndex == -1){
                        throw new RuntimeException("ingredient is checked in UI but not checked for recipe in data model");
                    }
                    checkedIngredients.remove(removeIndex);
                    holder.inputAmount.setText("", TextView.BufferType.EDITABLE);
                }
            }
        });

        // text listener template from jettimadhuChowdary, Aug 19, 2011
        // https://stackoverflow.com/questions/7117209/how-to-know-key-presses-in-edittext
        inputAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(holder.checkbox.isChecked()){
                    Ingredient ingredient = ingredients.get(holder.getAdapterPosition());
                    IngredientStub ingredientStub = new IngredientStub(
                            ingredient.getDescription(),
                            ingredient.getAmount(),
                            ingredient.getUnit(),
                            ingredient.getCategory());

                    for (int i = 0;  i < checkedIngredients.size(); i++){
                        if (ingredientStub.looseEquals(checkedIngredients.get(i))){
                            checkedIngredients.get(i).setAmount(inputAmount.getText().toString().isEmpty()?
                                    null: Integer.parseInt(inputAmount.getText().toString()));
                        }
                    }
                }
                else if(!inputAmount.getText().toString().isEmpty()){
                    holder.checkbox.setChecked(true);
                }

            }
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // TODO Auto-generated method stub
            }
            @Override
            public void afterTextChanged(Editable s) {
                // TODO Auto-generated method stub
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public ArrayList<IngredientStub> getCheckedIngredients() {
        return this.checkedIngredients;
    }

    /**
     * creates a duplicate stub ingredient from an ingredient and the holder's current
     * amount value. Copies the description, unit and category from the ingredient.
     * @param holder holder to pull the amount from
     * @param originalIngredient ingredient to pull unit, category and description from.
     * @return
     */
    public IngredientStub createDupeIngredient(RecipeSelectIngredientsViewHolder holder, Ingredient originalIngredient) {
        String description = originalIngredient.getDescription();
        Integer amount;
        if (!holder.inputAmount.getText().toString().isEmpty()) {
            amount = Integer.parseInt(holder.inputAmount.getText().toString());
        }
        else {
            amount = null;
        }
        String category = originalIngredient.getCategory();

        IngredientStub dupeIngredient = new IngredientStub(description, amount, originalIngredient.getUnit(), category);
        return dupeIngredient;
    }

    public ArrayList<Ingredient> getIngredients(){
        return ingredients;
    }

    /**
     * sets the adapter's ingredients
     * @param newIngredient ingredients to update with
     */
    public void updateIngredient(ArrayList<Ingredient> newIngredient){
        ingredients = newIngredient;
        notifyDataSetChanged();
    }

}
