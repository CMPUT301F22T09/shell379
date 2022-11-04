package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cmput301f22t09.shell379.R;

public class MainMenuFragment extends Fragment {
    private NavController navController;

    public MainMenuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_main_menu, container, false);

        // navigation snippet from https://developer.android.com/guide/navigation/navigation-navigate#groovy
        ((Button)rootView.findViewById(R.id.ingredients_list_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navController.navigate(MainMenuFragmentDirections.actionMainMenuFragmentToIngredientListFragment());
                    }
                }
        );
        ((Button)rootView.findViewById(R.id.recipes_list_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navController.navigate(MainMenuFragmentDirections.actionMainMenuFragmentToRecipeListFragment());
                    }
                }
        );
        ((Button)rootView.findViewById(R.id.meal_plans_list_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navController.navigate(MainMenuFragmentDirections.actionMainMenuFragmentToMealPlanListFragment());
                    }
                }
        );
        ((Button)rootView.findViewById(R.id.shopping_list_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navController.navigate(MainMenuFragmentDirections.actionMainMenuFragmentToShoppingListFragment());
                    }
                }
        );
        return rootView;
    }
}