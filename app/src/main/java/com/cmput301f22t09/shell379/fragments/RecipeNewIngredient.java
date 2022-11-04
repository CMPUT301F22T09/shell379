package com.cmput301f22t09.shell379.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.cmput301f22t09.shell379.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecipeNewIngredient#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecipeNewIngredient extends Fragment {
    private NavController navController;
    private EditText category;
    private EditText location;

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
        category = ((EditText)rootView.findViewById(R.id.editCategory));
        location = ((EditText)rootView.findViewById(R.id.editLocation));

        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.recipe_new_ingredient, container, false);

        ((ImageView)rootView.findViewById(R.id.back)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );

        category.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onClick(View v) {
                        onIngCategoryClick();
                    }
                }
        );
        location.setOnClickListener(
                new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onClick(View v) {
                        onLocationCategoryClick();
                    }
                }
        );
        return rootView;
    }

    private void onIngCategoryClick(){
        CategorySelectPopup.SelectListener listener = new CategorySelectPopup.SelectListener() {
            @Override
            public void send(String val) {
                category.setText(val);
            }
        };

        IngredientCategorySelectPopup selection = new IngredientCategorySelectPopup(listener,"Category");
        selection.show(getFragmentManager(), "");
        selection.setTargetFragment(RecipeNewIngredient.this, 1);
    }

    private void onLocationCategoryClick(){
        CategorySelectPopup.SelectListener listener = new CategorySelectPopup.SelectListener() {
            @Override
            public void send(String val) {
                location.setText(val);
            }
        };
        LocationCategorySelectPopup selection = new LocationCategorySelectPopup(listener, "Location");
        selection.show(getFragmentManager(), "");
        selection.setTargetFragment(RecipeNewIngredient.this, 1);
    }


    /**
     * Implement the option to go back to previous page
     */
    private void back(){
        navController.popBackStack();
    }
}