package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.IngredientAdapter;
import com.cmput301f22t09.shell379.adapters.RecipeListAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;

import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Edit_recipe#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Edit_recipe extends Fragment {

    Recipe myRecipe;
    RecyclerView recipe_recyclerView;
    RecyclerView.LayoutManager layoutManager;
    IngredientAdapter recipeListAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Edit_recipe() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Edit_recipe.
     */
    // TODO: Rename and change types and number of parameters
    public static Edit_recipe newInstance(String param1, String param2) {
        Edit_recipe fragment = new Edit_recipe();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_edit_recipe_9, container, false);
        myRecipe = new Recipe("kongpaochicken",100L,3,"chinese","spicy");
        myRecipe.addIngredient(new Ingredient("apple",new Date(2023,9,07),"fridge",2,"1lbs","fruit"));
        myRecipe.addIngredient(new Ingredient("chicken",new Date(2023,9,07),"fridge",2,"1lbs","meat"));
        myRecipe.addIngredient(new Ingredient("banana",new Date(2023,9,07),"fridge",2,"1lbs","fruit"));


        layoutManager = new LinearLayoutManager(this.getActivity());
        recipe_recyclerView = (RecyclerView) rootView.findViewById(R.id.ingredientsInRep);
        recipe_recyclerView.setLayoutManager(layoutManager);

        recipeListAdapter = new IngredientAdapter(myRecipe.getIngredients());
        recipe_recyclerView.setAdapter(recipeListAdapter);
        recipe_recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }
}