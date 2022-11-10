package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Unit;


import java.util.ArrayList;
import java.util.Arrays;

public class EditIngredientStubFragment extends SaveIngredientStubFragment{
    private int ingredientIndex;
    private Ingredient ingredient;

    public void writeToViewModel(Ingredient ing){
        editRecipeViewModel.getSelectedIngredients().set(ingredientIndex,ing);
        editRecipeViewModel.forceSignalUpdate();
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = super.onCreateView(inflater,container,savedInstanceState);

        super.onCreateView(inflater,container,savedInstanceState);
        ingredientIndex = getArguments().getInt("ingredientIndex");
        ingredient = editRecipeViewModel.getSelectedIngredients().get(ingredientIndex);

        ((EditText)rootView.findViewById(R.id.editDescription)).setText(ingredient.getDescription());
        ((EditText)rootView.findViewById(R.id.editAmount)).setText(String.valueOf(ingredient.getAmount()));
        ((EditText)rootView.findViewById(R.id.editCategory)).setText(String.valueOf(ingredient.getCategory()));
        ArrayList<Unit> units = new ArrayList<Unit>(Arrays.asList(Unit.values()));
        int unitSelectionIndex = units.indexOf(Unit.getFromString(ingredient.getUnit()));
        ((Spinner)rootView.findViewById(R.id.editUnit)).setSelection(unitSelectionIndex);

        return rootView;
    }

}