package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import androidx.fragment.app.DialogFragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.cmput301f22t09.shell379.R;

/**
 * fragment to select how to create a new ingredient stub.
 */
public class RecipeAddIngredientTypeFragment extends DialogFragment {
    // Full screen dialog strategy from Anubhav Arora , Nov 11 2020
    // https://medium.com/geekculture/android-full-screen-dialogfragment-1410dbd96d37
    @Override
    public int getTheme() {
        return R.style.DialogTheme;
    }
    private NavController navController;

    public RecipeAddIngredientTypeFragment() {
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

        View rootView = inflater.inflate(R.layout.fragment_recipe_add_ingredient_type_25, container, false);


        // navigation snippet from https://developer.android.com/guide/navigation/navigation-navigate#groovy
        ((Button)rootView.findViewById(R.id.ingredients_list_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navController.navigate(RecipeAddIngredientTypeFragmentDirections.toStorage());
                    }
                }
        );
        ((Button)rootView.findViewById(R.id.new_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navController.navigate(RecipeAddIngredientTypeFragmentDirections.toNew());
                    }
                }
        );
        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.floatingActionButton7)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navController.popBackStack();
                    }
                }
        );
        return rootView;
    }
}