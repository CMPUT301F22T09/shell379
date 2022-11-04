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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.RecipeListAdapter;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Fragment that displays the list of all recipes.
 */
public class RecipeListFragment extends Fragment {

    private ArrayList<Recipe> recipeList;
    private RecyclerView recipe_recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecipeListAdapter recipeListAdapter;
    private Button addNewRecipe;
    private Environment env;
    private NavController navController;
    private FloatingActionButton backButton;
    private Spinner spinner;

    public RecipeListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        env = Environment.of((AppCompatActivity) this.getActivity());
        this.navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        addNewRecipe = rootView.findViewById(R.id.recipe_list_newButton);
        backButton = rootView.findViewById(R.id.floatingActionButton6);
        spinner = rootView.findViewById(R.id.recipe_list_spinner);
        env = Environment.of((AppCompatActivity) requireActivity());

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                Arrays.asList("Description","Best Before Date", "Location", "Category")
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        final Observer<ArrayList<Recipe>> recipeObserver = new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(ArrayList<Recipe> recipes) {
                if (recipeListAdapter != null) {
                    recipeListAdapter.updateRecipes(recipes);
                }
            }
        };

        env.getRecipes().getListLive().observe(getViewLifecycleOwner(), recipeObserver);

        layoutManager = new LinearLayoutManager(this.getActivity());
        recipe_recyclerView = rootView.findViewById(R.id.recipe_list_recyclerView);
        recipe_recyclerView.setLayoutManager(layoutManager);

        recipeListAdapter = new RecipeListAdapter(recipeList, this);
        recipe_recyclerView.setAdapter(recipeListAdapter);
        recipe_recyclerView.setItemAnimator(new DefaultItemAnimator());

        addNewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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

}