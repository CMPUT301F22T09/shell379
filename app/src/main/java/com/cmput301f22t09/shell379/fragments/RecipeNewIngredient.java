package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cmput301f22t09.shell379.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeNewIngredient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeNewIngredient extends Fragment {
    private NavController navController;

    public RecipeNewIngredient() {
        // Required empty public constructor
    }

    public static RecipeNewIngredient newInstance() {
        RecipeNewIngredient fragment = new RecipeNewIngredient();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipe_new_ingredient, container, false);

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.recipe_new_ingredient, container, false);

        ((ImageView)rootView.findViewById(R.id.back)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );
        return rootView;
    }


    /**
     * Implement the option to go back to previous page
     */
    private void back(){
        navController.popBackStack();
    }
}