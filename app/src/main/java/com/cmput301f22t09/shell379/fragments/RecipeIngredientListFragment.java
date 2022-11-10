package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.vm.EditRecipeViewModel;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.util.ArrayList;

/**
 * Fragment for viewing selected ingredients for a recipe.
 */
public class RecipeIngredientListFragment extends DialogFragment implements RecipeIngredientsListAdapter.RecipeIngredientListListener{
    //    From Anubhav Arora  https://medium.com/geekculture/android-full-screen-dialogfragment-1410dbd96d37
    @Override
    public int getTheme() {
        return R.style.DialogTheme;
    }

    ArrayList<Ingredient> ingredientList;
    ArrayList<Ingredient> recipeIngredientList;
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
        View rootView = inflater.inflate(R.layout.recipe_ingredients_list, container, false);


        final Observer<ArrayList<Ingredient>> ingredientObserver = new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Ingredient> ingredients) {
                // update the recipe draft.
                rsiAdapter.updateList(env.getIngredients().getFilteredCollection().getList(),
                        editRecipeViewModel.getSelectedIngredients());
            }
        };
        env.getIngredients().getListLive().observe(this, ingredientObserver);

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

    private void renderList(View rootView){
        recipeIngredientList = editRecipeViewModel.getSelectedIngredients();
        // filtered ingredients list from environment
        ingredientList = env.getIngredients().getFilteredCollection().getList();

        layoutManager = new LinearLayoutManager(this.getActivity());
        ingredientsRecyclerView = (RecyclerView) rootView.findViewById(R.id.rsi_recyclerView);
        ingredientsRecyclerView.setLayoutManager(layoutManager);

        rsiAdapter = new RecipeIngredientsListAdapter(recipeIngredientList, this);
        ingredientsRecyclerView.setAdapter(rsiAdapter);
        ingredientsRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     *  save the ing draft and move to creating a new ingredient
     */
    private void onNewIngStubClicked(){

//            navController.navigate( RecipeSelectIngredientFragmentDirections.actionRecipeSelectIngredientFragmentToCreateIngredientStubFragment3());

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

    };
}