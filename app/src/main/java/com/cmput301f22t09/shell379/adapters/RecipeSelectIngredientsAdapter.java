package com.cmput301f22t09.shell379.adapters;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;

import org.checkerframework.checker.units.qual.C;
import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Adapter for recycler view in select ingredients page for recipes.
 */
public class RecipeSelectIngredientsAdapter extends RecyclerView.Adapter<RecipeSelectIngredientsAdapter.RecipeSelectIngredientsViewHolder> {

    private ArrayList<Ingredient> ingredients;
    private ArrayList<Ingredient> recipeIngredients;
    // This contains "dupe" ingredients
    private ArrayList<Ingredient> checkedIngredients;


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

//        public Ingredient getDupeIngredient() {
//            String description = this.description.getText().toString();
//            String category = this.category.getText().toString();
//            Integer amount;
////            if (this.inputAmount.getText().toString().equals("")) {
////                amount = -1;
////            }
////            else {
////                amount = Integer.parseInt(this.inputAmount.getText().toString());
////            }
//            amount = Integer.parseInt(this.inputAmount.getText().toString());
//
//            Ingredient dupeIngredient = new Ingredient(description, null, amount, null, category);
//            return dupeIngredient;
//        }
    }

    public RecipeSelectIngredientsAdapter(ArrayList<Ingredient> ingredients, ArrayList<Ingredient> recipeIngredients) {
        this.ingredients = ingredients;
        this.recipeIngredients = recipeIngredients;
        this.checkedIngredients = new ArrayList<>();
        if (!recipeIngredients.isEmpty()) {
            this.checkedIngredients = recipeIngredients;
        }
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
        TextView unit = holder.unit;
        TextView category = holder.category;

        description.setText(ingredients.get(position).getDescription());
        category.setText(ingredients.get(position).getCategory());
        unit.setText(ingredients.get(position).getUnit());

        for (int i = 0; i < recipeIngredients.size(); i++) {
            if (createDupeIngredient(holder, ingredients.get(position)).partialEquals(recipeIngredients.get(i))) {
                holder.checkbox.setChecked(true);
                holder.inputAmount.setText(recipeIngredients.get(i).getAmount().toString(), TextView.BufferType.EDITABLE);
            }
        }

        // referenced for how to detect when a checkbox is clicked
        // url: https://stackoverflow.com/questions/51778606/android-how-to-check-if-a-checkbox-is-checked-in-an-item-of-a-recyclerview
        holder.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    String amount = holder.inputAmount.getText().toString();
                    // Prevent the checkbox from being clicked if there is no amount entered
                    if (amount == null || amount.length() == 0) {
                        holder.checkbox.setChecked(false);
                        return;
                    }
                    System.out.println(amount);
                    Ingredient originalIngredient = ingredients.get(holder.getAdapterPosition());
                    Ingredient dupeIngredient = createDupeIngredient(holder, originalIngredient);
                    checkedIngredients.add(dupeIngredient);
                }
                else {
                    // get index of dupe ingredient and remove it
                    Ingredient originalIngredient = ingredients.get(holder.getAdapterPosition());
                    Ingredient dupeIngredient = createDupeIngredient(holder, originalIngredient);
                    //int dupeIngredientIndex = checkedIngredients.indexOf(dupeIngredient);
                    //checkedIngredients.remove(dupeIngredientIndex);
                    int removeIndex = -1;
                    for (int i = 0; i < checkedIngredients.size(); i++) {
                        if (dupeIngredient.partialEquals(checkedIngredients.get(i))) {
                            removeIndex = i;
                            break;
                        }
                    }
                    checkedIngredients.remove(removeIndex);
                    holder.inputAmount.setText("", TextView.BufferType.EDITABLE);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public ArrayList<Ingredient> getCheckedIngredients() {
        return this.checkedIngredients;
    }

    public Ingredient createDupeIngredient(RecipeSelectIngredientsViewHolder holder, Ingredient originalIngredient) {
        String description = originalIngredient.getDescription();
        String test = holder.inputAmount.getText().toString();
        Integer amount;
        if (!holder.inputAmount.getText().toString().isEmpty()) {
            amount = Integer.parseInt(holder.inputAmount.getText().toString());
        }
        else {
            amount = 0;
        }
        String category = originalIngredient.getCategory();

        Ingredient dupeIngredient = new Ingredient(description, null, amount, null, category);
        return dupeIngredient;
    }
}
