package com.cmput301f22t09.shell379;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cmput301f22t09.shell379.data.Ingredient;

import java.util.ArrayList;


public class IngredientCustomList extends ArrayAdapter<Ingredient> {
    private ArrayList<Ingredient> ingredients;
    private Context context;

    public IngredientCustomList(Context context, ArrayList<Ingredient> ingredients) {
        super(context, 0, ingredients);
        this.ingredients = ingredients;
        this.context = context;
    }


    @NonNull
    @Override

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.ingredient_content, parent, false);
        }

        Ingredient ingredient = ingredients.get(position);

        TextView description = view.findViewById(R.id.ingredient_name);
        TextView best_before_date = view.findViewById(R.id.best_before_date);
        TextView location = view.findViewById(R.id.location);
        TextView category = view.findViewById(R.id.category);

        description.setText(ingredient.getDescription());
        //date to string
        best_before_date.setText(String.valueOf(ingredient.getBestBefore()));
        location.setText(ingredient.getLocation());
        category.setText(ingredient.getCategory());

        return view;
    }




}
