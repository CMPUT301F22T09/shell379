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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.RecipeListAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.util.ArraySortUtil;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    int selectedSortIndex;

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
                Recipe.getSortableProps()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        selectedSortIndex = 0;
        // setting spinner events from khaled ben aissa, dec 21 2011
        // https://stackoverflow.com/questions/8597582/get-the-position-of-a-spinner-in-android
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // resorts the recycler view of ingredients
                selectedSortIndex = ((Spinner) rootView.findViewById(R.id.recipe_list_spinner)).getSelectedItemPosition();
                recipeListAdapter.updateRecipes(ArraySortUtil.sortByStringProp(recipeListAdapter.getRecipes(),Recipe.getStringPropGetter(selectedSortIndex)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });


        final Observer<ArrayList<Recipe>> recipeObserver = new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(ArrayList<Recipe> recipes) {
                if (recipeListAdapter != null && ((Spinner) rootView.findViewById(R.id.recipe_list_spinner)) != null) {
                    selectedSortIndex = ((Spinner) rootView.findViewById(R.id.recipe_list_spinner)).getSelectedItemPosition();
                    recipeListAdapter.updateRecipes(ArraySortUtil.sortByStringProp(recipes,Recipe.getStringPropGetter(selectedSortIndex)));
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