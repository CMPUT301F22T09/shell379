package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

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

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.RecipeIngredientsListAdapter;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.vm.EditRecipeViewModel;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.util.ArrayList;

/**
 * Fragment for viewing selected ingredients for a recipe.
 */
public class RecipeIngredientListFragment extends DialogFragment implements RecipeIngredientsListAdapter.RecipeIngredientListListener{
    // Full screen dialog strategy from Anubhav Arora , Nov 11 2020
    // https://medium.com/geekculture/android-full-screen-dialogfragment-1410dbd96d37
    @Override
    public int getTheme() {
        return R.style.DialogTheme;
    }

    ArrayList<IngredientStub> recipeIngredientList;
    RecyclerView ingredientsRecyclerView;
    RecyclerView.LayoutManager layoutManager;
    RecipeIngredientsListAdapter rsiAdapter;
    Button botBackButton;
    Environment env;
    private NavController navController;
    private EditRecipeViewModel editRecipeViewModel;

    public RecipeIngredientListFragment() {
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
        View rootView = inflater.inflate(R.layout.recipe_ingredients_list_23, container, false);


        final Observer<ArrayList<IngredientStub>> ingredientObserver = new Observer<ArrayList<IngredientStub>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<IngredientStub> ingredients) {
                rsiAdapter.updateList(editRecipeViewModel.getSelectedIngredients());
            }
        };
        editRecipeViewModel.getLiveSelectedIngredients().observe(this, ingredientObserver);

        renderList(rootView);

        botBackButton = rootView.findViewById(R.id.bot_back_button);
        botBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
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
     * renders recucler view and pulls data.
     * @param rootView
     */
    private void renderList(View rootView){
        recipeIngredientList = editRecipeViewModel.getSelectedIngredients();
        // filtered ingredients list from environment

        layoutManager = new LinearLayoutManager(this.getActivity());
        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rsi_recyclerView);
        ingredientsRecyclerView.setLayoutManager(layoutManager);

        rsiAdapter = new RecipeIngredientsListAdapter(recipeIngredientList, this, editRecipeViewModel);
        ingredientsRecyclerView.setAdapter(rsiAdapter);
        ingredientsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     *  save the ing draft and move to creating a new ingredient
     */
    private void onNewIngStubClicked(){
            navController.navigate(RecipeIngredientListFragmentDirections.actionIngListToSelectIngType());
    }

    /**
     * Implement the option to go back to previous page
     */
    private void back(){
        navController.popBackStack();
    }

    /**
     *  Moves to the edit recipe ingredient fragment
     */
    public void editRecipeIngredient(int index){
        navController.navigate(RecipeIngredientListFragmentDirections.actionIngListToEditIngredientStub(index));
    };
}