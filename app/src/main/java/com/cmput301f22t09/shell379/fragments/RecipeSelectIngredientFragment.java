package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.RecipeSelectIngredientsAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.EditRecipeViewModel;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.util.ArrayList;

/**
 * Fragment for selecting ingredients for recipes.
 */
public class RecipeSelectIngredientFragment extends DialogFragment {
    // Full screen dialog strategy from Anubhav Arora , Nov 11 2020
    // https://medium.com/geekculture/android-full-screen-dialogfragment-1410dbd96d37
    @Override
    public int getTheme() {
        return R.style.DialogTheme;
    }

    ArrayList<Ingredient> ingredientList;
    ArrayList<Ingredient> recipeIngredientList;
    RecyclerView ingredientsRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecipeSelectIngredientsAdapter rsiAdapter;
    Button botBackButton;
    Environment env;
    private NavController navController;
    private EditRecipeViewModel editRecipeViewModel;

    public RecipeSelectIngredientFragment() {
        // Required empty public constructor
    }

    /**
     * Initial creation of fragment, called before onCreateView.
     * @param savedInstanceState state used if the fragment is being recreated from a previous state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        editRecipeViewModel =  new ViewModelProvider(requireActivity()).get(EditRecipeViewModel.class);
        ingredientList = new ArrayList<Ingredient>();

        navController = NavHostFragment.findNavController(this);
        env = Environment.of((AppCompatActivity) requireActivity());

    }

    /**
     * Set up the onCreateView method to create the view object.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.recipe_select_ingredients, container, false);

        renderList(rootView);

        botBackButton = rootView.findViewById(R.id.bot_back_button);
        botBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(saveIngToDraft()){
                    navController.popBackStack();
                    navController.popBackStack();
                }
            }
        });

        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.floatingActionButton7)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );

        return rootView;
    }


    /**
     * renders recucler view and pulls data.
     * @param rootView
     */
    private void renderList(View rootView){
        ingredientList = env.getIngredients().getList();

        layoutManager = new LinearLayoutManager(this.getActivity());
        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rsi_recyclerView);
        ingredientsRecyclerView.setLayoutManager(layoutManager);

        rsiAdapter = new RecipeSelectIngredientsAdapter(ingredientList, recipeIngredientList);
        ingredientsRecyclerView.setAdapter(rsiAdapter);
        ingredientsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }


    /**
     *  save the ing draft.
     * @return returns true if successful, false otherwise.
     */
    private boolean saveIngToDraft(){
        if(rsiAdapter.selectedIngsHaveAmounts()){
            ArrayList<IngredientStub> checkedIngredients = rsiAdapter.getCheckedIngredients();
            editRecipeViewModel.setSelectedIngredients(checkedIngredients);
        }else{
            Toast.makeText(getContext(), "Please enter amounts for all ingredients", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    /**
     * Implement the option to go back to previous page
     */
    private void back(){
        navController.popBackStack();
    }
}