package com.cmput301f22t09.shell379.fragments;

import android.media.audiofx.EnvironmentalReverb;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.RecipeSelectIngredientsAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.util.ArrayList;
import java.util.Date;

public class RecipeSelectIngredientFragment extends Fragment {

    // TODO: Temporary! Testing content
    ArrayList<Ingredient> testList;
    RecyclerView ingredientsRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecipeSelectIngredientsAdapter rsiAdapter;
    Button selectButton;
    Environment env;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecipeSelectIngredientFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecipeSelectIngredientFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecipeSelectIngredientFragment newInstance(String param1, String param2) {
        RecipeSelectIngredientFragment fragment = new RecipeSelectIngredientFragment();
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
        env = Environment.of((AppCompatActivity) requireActivity());
        View rootView = inflater.inflate(R.layout.recipe_select_ingredients, container, false);

        testList = new ArrayList<Ingredient>();
        testList.add(new Ingredient("Ingredient1", new Date(), "location", 2, "90 unit", "category"));
        testList.add(new Ingredient("Ingredient2", new Date(), "location2", 2, "90 unit", "category2"));

//        selectButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                select();
//            }
//        });

        layoutManager = new LinearLayoutManager(this.getActivity());
        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rsi_recyclerView);
        ingredientsRecyclerView.setLayoutManager(layoutManager);

        rsiAdapter = new RecipeSelectIngredientsAdapter(testList);
        ingredientsRecyclerView.setAdapter(rsiAdapter);
        ingredientsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

//    public void select() {
//        // get specific recipe & add ingredient
//
//    }
}