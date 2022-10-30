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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.RecipeListAdapter;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.util.ArrayList;

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
    private NavController navController;
    Environment env;

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
        this.navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_recipe_list, container, false);

        addNewRecipe = rootView.findViewById(R.id.recipe_list_newButton);

        env = Environment.of((AppCompatActivity) this.getActivity());

        // TODO: add same source as ingredient observer
        final Observer<ArrayList<Recipe>> recipeObserver = new Observer<ArrayList<Recipe>>() {
            @Override
            public void onChanged(ArrayList<Recipe> recipes) {
                if (recipeListAdapter != null) {
                    recipeListAdapter.updateRecipes(recipes);
                }
            }
        };
        env.getRecipes().getListLive().observe(getViewLifecycleOwner(), recipeObserver);

//        recipeList = env.getRecipes().getRecipes();
//
//        // TODO: This is temporary! Most likely replace this with data from ViewModel later...
//        recipeList = new ArrayList<Recipe>();
//        recipeList.add(new Recipe("Pizza", 36000L, 5, "Italian", "This is a Pizza"));
//        recipeList.add(new Recipe("Fried Rice", 26000L, 3, "Asian", "This is fried rice"));
//        recipeList.add(new Recipe("Soup", 26000L, 1, "Yes", "This is soup"));


        layoutManager = new LinearLayoutManager(this.getActivity());
        recipe_recyclerView = (RecyclerView) rootView.findViewById(R.id.recipe_list_recyclerView);
        recipe_recyclerView.setLayoutManager(layoutManager);

        recipeListAdapter = new RecipeListAdapter(recipeList, this);
        recipe_recyclerView.setAdapter(recipeListAdapter);
        recipe_recyclerView.setItemAnimator(new DefaultItemAnimator());

        addNewRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("New recipe button clicked!");
                navController.navigate(RecipeListFragmentDirections.actionRecipeListFragmentToEditRecipe());
            }
        });

        // TODO: this is temporary!
//        Recipe pizzaRecipe = new Recipe("Pizza", 36000L, 5, "Italian", "This is a Pizza");
//        env.getRecipes().add(pizzaRecipe);
//        env.getRecipes().commit();

        return rootView;
    }
}