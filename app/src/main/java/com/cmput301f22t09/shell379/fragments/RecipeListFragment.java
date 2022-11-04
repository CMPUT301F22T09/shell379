package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.RecipeListAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeListFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeListFragment extends Fragment {

    // TODO: Temporary! Testing content
    ArrayList<Recipe> recipeList;
    RecyclerView recipe_recyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecipeListAdapter recipeListAdapter;
    Button addNewRecipe;
    Environment env;
    private NavController navController;
    private FloatingActionButton backButton;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment RecipeListFragment.
     */
    public static RecipeListFragment newInstance(String param1, String param2) {
        RecipeListFragment fragment = new RecipeListFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Recipe newRecipe = createRecipe();
        env = Environment.of((AppCompatActivity) this.getActivity());
//        env.getRecipes().add(newRecipe);
        this.navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        addNewRecipe = rootView.findViewById(R.id.recipe_list_newButton);
        backButton = rootView.findViewById(R.id.floatingActionButton6);
        env = Environment.of((AppCompatActivity) requireActivity());

        // TODO: add same source as ingredient observer
        final Observer<ArrayList<Recipe>> recipeObserver = new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(ArrayList<Recipe> recipes) {
                if (recipeListAdapter != null) {
                    recipeListAdapter.updateRecipes(recipes);
                }
            }
        };

//      //   Implement the button to back to previous page
//        ((ImageView)rootView.findViewById(R.id.floatingActionButton6)).setOnClickListener(
//                new View.OnClickListener() {
//                    public void onClick(View v) {
//                        back();
//                    }
//                }
//        );
        env.getRecipes().getListLive().observe(getViewLifecycleOwner(), recipeObserver);

        layoutManager = new LinearLayoutManager(this.getActivity());
        recipe_recyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_list_recyclerView);
        recipe_recyclerView.setLayoutManager(layoutManager);

        recipeListAdapter = new RecipeListAdapter(recipeList, this);
        recipe_recyclerView.setAdapter(recipeListAdapter);
        recipe_recyclerView.setItemAnimator(new DefaultItemAnimator());

        addNewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("enter", "onlicked");
                int recipeIndex = -1;
                navController.navigate(RecipeListFragmentDirections.actionRecipeListFragmentToEditRecipe(recipeIndex));
            }
        });


        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(RecipeListFragmentDirections.actionRecipeListFragmentToMainMenuFragment());
            }
        });

        return rootView;
    }

    public Recipe createRecipe() {
        Recipe myRecipe = new Recipe("kongpaochicken",100L,3,"chinese","spicy");
        myRecipe.addIngredient(new Ingredient("appleesdadadsdawdwadsaszdazawdas",new Date(2023,9,07),"fridge",2,"1lbs","fruit"));
        myRecipe.addIngredient(new Ingredient("chicken",new Date(2023,9,07),"fridge",2,"1lbs","meat"));
        myRecipe.addIngredient(new Ingredient("banana",new Date(2023,9,07),"fridge",2,"1lbs","fruit"));
        return myRecipe;
    }
}