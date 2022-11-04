package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
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

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Date;

/**
 */
public class RecipeSelectIngredientFragment extends Fragment {

    // TODO: Temporary! Testing content
    ArrayList<Ingredient> ingredientList;
    ArrayList<Ingredient> recipeIngredientList;
    Recipe selectedRecipe;
    RecyclerView ingredientsRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecipeSelectIngredientsAdapter rsiAdapter;
    Button selectButton;
    Environment env;
    int recipeIndex;
    private NavController navController;

    public RecipeSelectIngredientFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredientList = new ArrayList<Ingredient>();
        recipeIngredientList = new ArrayList<Ingredient>();
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        env = Environment.of((AppCompatActivity) requireActivity());
        View rootView = inflater.inflate(R.layout.recipe_select_ingredients, container, false);
        recipeIndex = getArguments().getInt("recipeIndex");

        // recipeIndex = -1 means we're creating a new recipe
        if (recipeIndex == -1) {
            // TODO: can't get recipe from env using recipeIndex because it doesn't exist in env yet
            recipeIngredientList = new ArrayList<>();
        }
        else {
            selectedRecipe = env.getRecipes().getList().get(recipeIndex);
            // the selected recipe's ingredients
            recipeIngredientList = selectedRecipe.getIngredients();
        }
        // filtered ingredients list from environment
        ingredientList = env.getIngredients().getFilteredCollection().getList();

        layoutManager = new LinearLayoutManager(this.getActivity());
        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rsi_recyclerView);
        ingredientsRecyclerView.setLayoutManager(layoutManager);

        rsiAdapter = new RecipeSelectIngredientsAdapter(ingredientList, recipeIngredientList);
        ingredientsRecyclerView.setAdapter(rsiAdapter);
        ingredientsRecyclerView.setItemAnimator(new DefaultItemAnimator());

//        selectedRecipe = env.getRecipes().getList().get(recipeIndex);
//        // the selected recipe's ingredients
//        recipeIngredientList = selectedRecipe.getIngredients();
//        // filtered ingredients list from environment
//        ingredientList = env.getIngredients().getFilteredCollection().getList();
//
//        layoutManager = new LinearLayoutManager(this.getActivity());
//        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rsi_recyclerView);
//        ingredientsRecyclerView.setLayoutManager(layoutManager);
//
//        rsiAdapter = new RecipeSelectIngredientsAdapter(ingredientList, recipeIngredientList);
//        ingredientsRecyclerView.setAdapter(rsiAdapter);
//        ingredientsRecyclerView.setItemAnimator(new DefaultItemAnimator());
        selectButton = rootView.findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            // TODO: Create dupe ingredient objects with amount

            @Override
            public void onClick(View view) {
                // Creating a new recipe
                if (recipeIndex == -1) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("selectedIngredients", rsiAdapter.getCheckedIngredients());
                    RecipeSelectIngredientFragmentDirections.ActionRecipeSelectIngredientFragmentToEditRecipe action
                            = RecipeSelectIngredientFragmentDirections.actionRecipeSelectIngredientFragmentToEditRecipe(recipeIndex);
                    action.setSelectedIngredients(bundle);
                    navController.navigate(action);
                }
                // Editing an existing recipe
                else {
                    // Get checked ingredients from recycler view
                    selectedRecipe.setIngredients(rsiAdapter.getCheckedIngredients());
                    env.getRecipes().commit();
                }
            }
        });

        return rootView;
    }

}