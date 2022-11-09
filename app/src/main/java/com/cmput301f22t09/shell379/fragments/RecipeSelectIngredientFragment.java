package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
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
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.EditRecipeViewModel;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Date;

/**
 * Fragment for selecting ingredients for recipes.
 */
public class RecipeSelectIngredientFragment extends DialogFragment {
    //    From Anubhav Arora  https://medium.com/geekculture/android-full-screen-dialogfragment-1410dbd96d37
    @Override
    public int getTheme() {
        return R.style.DialogTheme;
    }

    ArrayList<Ingredient> ingredientList;
    ArrayList<Ingredient> recipeIngredientList;
    RecyclerView ingredientsRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecipeSelectIngredientsAdapter rsiAdapter;
    Button selectButton;
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
        recipeIngredientList = editRecipeViewModel.getRecipe().getIngredients();

        navController = NavHostFragment.findNavController(this);
        env = Environment.of((AppCompatActivity) requireActivity());

        // filtered ingredients list from environment
        ingredientList = env.getIngredients().getFilteredCollection().getList();
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

        layoutManager = new LinearLayoutManager(this.getActivity());
        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rsi_recyclerView);
        ingredientsRecyclerView.setLayoutManager(layoutManager);

        rsiAdapter = new RecipeSelectIngredientsAdapter(ingredientList, recipeIngredientList);
        ingredientsRecyclerView.setAdapter(rsiAdapter);
        ingredientsRecyclerView.setItemAnimator(new DefaultItemAnimator());

        selectButton = rootView.findViewById(R.id.select_button);
        selectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(rsiAdapter.selectedIngsHaveAmounts()){
                    ArrayList<Ingredient> checkedIngredients = rsiAdapter.getCheckedIngredients();
                    editRecipeViewModel.getRecipe().setIngredients(checkedIngredients);
                    editRecipeViewModel.forceSignalUpdate();
                    navController.popBackStack();
                }else{
                    Toast.makeText(getContext(), "Please enter amounts for all ingredients", Toast.LENGTH_LONG).show();
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

        // Implement the new ingredient function
        ((Button)rootView.findViewById(R.id.new_ingredient_stub_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        onNewIngStubClicked();
                    }
                }
        );

        return rootView;
    }

    /**
     *  save the ing draft and move to creating a new ingredient
     */
    private void onNewIngStubClicked(){
        saveIngToDraft();

        Bundle bundle = new Bundle();
        bundle.putSerializable("selectedIngredients", rsiAdapter.getCheckedIngredients());

        navController.navigate( RecipeSelectIngredientFragmentDirections.actionRecipeSelectIngredientFragmentToCreateIngredientStubFragment3());
    }

    /**
     *  save the ing draft
     */
    private void saveIngToDraft(){
        Recipe newRecipe = editRecipeViewModel.getRecipe();
        newRecipe.setIngredients(rsiAdapter.getCheckedIngredients());
        editRecipeViewModel.liveRecipe().setValue(newRecipe);
    }

    /**
     * Implement the option to go back to previous page
     */
    private void back(){
        navController.popBackStack();
    }
}