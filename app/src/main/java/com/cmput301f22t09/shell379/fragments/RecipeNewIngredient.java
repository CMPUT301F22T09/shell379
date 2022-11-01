package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmput301f22t09.shell379.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeNewIngredient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeNewIngredient extends Fragment {

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.recipe_new_ingredient, container, false);
    }
}