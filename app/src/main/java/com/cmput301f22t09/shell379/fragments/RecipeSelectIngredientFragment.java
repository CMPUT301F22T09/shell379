package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.RecipeSelectIngredientsAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.util.ArrayList;
import java.util.Date;

/**
 */
public class RecipeSelectIngredientFragment extends Fragment {

    // TODO: Temporary! Testing content
    ArrayList<Ingredient> ingredientList;
    RecyclerView ingredientsRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecipeSelectIngredientsAdapter rsiAdapter;
    Button selectButton;
    Environment env;
    int recipeIndex;


    public RecipeSelectIngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredientList = new ArrayList<Ingredient>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        env = Environment.of((AppCompatActivity) requireActivity());
        View rootView = inflater.inflate(R.layout.recipe_select_ingredients, container, false);
        recipeIndex = getArguments().getInt("recipeIndex");

        // selected recipe
        Recipe selectedR = env.getRecipes().getList().get(recipeIndex);

        ingredientList = env.getIngredients().getFilteredCollection().getList();

//        ingredientList.add(new Ingredient("Ingredient1", new Date(), "location", 2, "90 unit", "category"));
//        ingredientList.add(new Ingredient("Ingredient2", new Date(), "location2", 2, "90 unit", "category2"));

//        selectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                select();
//            }
//        });

        layoutManager = new LinearLayoutManager(this.getActivity());
        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rsi_recyclerView);
        ingredientsRecyclerView.setLayoutManager(layoutManager);

        rsiAdapter = new RecipeSelectIngredientsAdapter(ingredientList);
        ingredientsRecyclerView.setAdapter(rsiAdapter);
        ingredientsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

//    public void select() {
//        // get specific recipe & add ingredient
//
//    }
}