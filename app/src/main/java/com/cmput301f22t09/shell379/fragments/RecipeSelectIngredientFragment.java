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
import android.widget.ImageView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.RecipeSelectIngredientsAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Date;

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
    private NavController navController;
    int recipeIndex;

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

    /**
     * Set up the onCreateView method to create the view object
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
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

        selectButton = rootView.findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
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
                    // TODO: cite https://stackoverflow.com/questions/32811156/how-to-iterate-over-recyclerview-items
                    // for iterating over recyclerview items
                    ArrayList<Ingredient> checkedIngredients = rsiAdapter.getCheckedIngredients();
//                    for (int i = 0; i < ingredientsRecyclerView.getChildCount(); i++) {
//                        RecipeSelectIngredientsAdapter.RecipeSelectIngredientsViewHolder holder
//                                = (RecipeSelectIngredientsAdapter.RecipeSelectIngredientsViewHolder) ingredientsRecyclerView.findViewHolderForAdapterPosition(i);
//                        Ingredient dupeIngredient = holder.getDupeIngredient();
//                        int checkIngredientIndex = 0;
//                        for (int j = 0; j < checkedIngredients.size(); j++) {
//                            if (dupeIngredient.partialEquals(checkedIngredients.get(j))) {
//                                checkIngredientIndex = j;
//                                break;
//                            }
//                        }
//                        checkedIngredients.get(checkIngredientIndex).setAmount(dupeIngredient.getAmount());
//                    }
                    // Get checked ingredients from recycler view
                    selectedRecipe.setIngredients(checkedIngredients);
                    env.getRecipes().commit();
                    navController.popBackStack();
                }
            }
        });


        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.floatingActionButton7)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );
        return rootView;
    } 

    public void select() {
        // get specific recipe & add ingredient 
    }

    /**
     * Implement the option to go back to previous page
     */
    private void back(){
        navController.popBackStack();
    }
}