package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.AddRecipeMealPlanAdapter;
import com.cmput301f22t09.shell379.adapters.RecipeIngredientsListAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.util.ArraySortUtil;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.util.ArrayList;

public class AddRecipeToMPFragment extends DialogFragment implements AddRecipeMealPlanAdapter.RecipeInMealPlanListener  {
    private Environment env;
    private NavController navController;
    private MealPlanViewModel mealPlanViewModel;
    private AddRecipeMealPlanAdapter addRecipeMealPlanAdapter;
    private ArrayList<MealPlanWrapper<Recipe>> RecipeinMealPlan;
    private int selectedSortIndex;

    public AddRecipeToMPFragment(){

    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mealPlanViewModel = new ViewModelProvider(requireActivity()).get(MealPlanViewModel.class);

        navController = NavHostFragment.findNavController(this);
        env = Environment.of((AppCompatActivity) requireActivity());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_add_recipe_meal_plan_16, container, false);

        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.back_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );

        RecyclerView recipeRecyclerView =(rootView.findViewById(R.id.add_recipe_to_mealPlan_recyclerView));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        recipeRecyclerView.setLayoutManager(layoutManager);

        addRecipeMealPlanAdapter = new AddRecipeMealPlanAdapter(env.getRecipes().getList(), this, mealPlanViewModel);
        recipeRecyclerView.setAdapter(addRecipeMealPlanAdapter);
        recipeRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // Sort spinner setup
        Spinner spinner = rootView.findViewById(R.id.add_recipe_toMP_sort_spinner);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                Recipe.getSortableProps()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        selectedSortIndex = 0;
        // setting spinner events from khaled ben aissa, dec 21 2011
        // https://stackoverflow.com/questions/8597582/get-the-position-of-a-spinner-in-android
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // resorts the recycler view of ingredients
                selectedSortIndex = ((Spinner) rootView.findViewById(R.id.add_recipe_toMP_sort_spinner)).getSelectedItemPosition();
                addRecipeMealPlanAdapter.updateRecipes(ArraySortUtil.sortByStringProp(addRecipeMealPlanAdapter.getRecipes(),Recipe.getStringPropGetter(selectedSortIndex)));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });

        return rootView;
    }

    private void back(){
        navController.popBackStack();
    }

    /**
     * Navigate from add recipe page to pick the specific date for the recipe
     * @param index
     */
    public void navigateToPickDate(int index){
        AddRecipeToMPFragmentDirections.ActionAddRecipetoMealPlanFragmentToAddDatetoRecipeFragment action
                =  AddRecipeToMPFragmentDirections.actionAddRecipetoMealPlanFragmentToAddDatetoRecipeFragment(index);
        navController.navigate(action);
    }

}
