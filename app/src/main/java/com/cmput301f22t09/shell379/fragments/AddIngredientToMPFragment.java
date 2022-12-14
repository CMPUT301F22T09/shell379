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
import com.cmput301f22t09.shell379.adapters.AddIngredMealPlanAdapter;
import com.cmput301f22t09.shell379.adapters.AddRecipeMealPlanAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.util.ArraySortUtil;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import java.util.ArrayList;

/**
 *  List of ingredients to add to a meal plan
 */
public class AddIngredientToMPFragment extends DialogFragment implements AddIngredMealPlanAdapter.IngredInMealPlanListener {
    private Environment env;
    private NavController navController;
    private MealPlanViewModel mealPlanViewModel;
    AddIngredMealPlanAdapter addIngredMealPlanAdapter;
    int selectedSortIndex;

    public AddIngredientToMPFragment(){
        // Required empty constructor
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
        View rootView =  inflater.inflate(R.layout.fragment_add_ingredient_meal_plan_18, container, false);

        // Implement the spinner option to sort the ingredient list
        Spinner spinner = (Spinner) rootView.findViewById(R.id.add_ingredient_toMP_sort_spinner);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                Ingredient.getSortableProps()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        selectedSortIndex = 0;

        RecyclerView ingredRecyclerView = (rootView.findViewById(R.id.add_ingredient_to_mealPlan_recyclerView));

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        ingredRecyclerView.setLayoutManager(layoutManager);

        addIngredMealPlanAdapter = new AddIngredMealPlanAdapter(env.getIngredients().getList(), this, mealPlanViewModel);
        ingredRecyclerView.setAdapter(addIngredMealPlanAdapter);
        ingredRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // setting spinner events from khaled ben aissa, dec 21 2011
        // https://stackoverflow.com/questions/8597582/get-the-position-of-a-spinner-in-android
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // resorts the recycler view of ingredients
                selectedSortIndex = ((Spinner) rootView.findViewById(R.id.add_ingredient_toMP_sort_spinner)).getSelectedItemPosition();
                addIngredMealPlanAdapter.updateIngredient(
                        ArraySortUtil.sortByStringProp(addIngredMealPlanAdapter.getIngredients(),
                                Ingredient.getStringPropGetter(selectedSortIndex))
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });

        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.back_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {

                        back();
                    }
                }
        );

        return rootView;
    }

    private void back(){
        navController.popBackStack();
    }

    /**
     * Navigate from add Ingredient page to pick the specific date for the ingredient
     * @param index
     */
    public void navigateToPickDate(int index){
        //    to-do
        AddIngredientToMPFragmentDirections.ActionAddIngredtoMealPlanFragmentToAddDatetoIngredFragment action
                =  AddIngredientToMPFragmentDirections.actionAddIngredtoMealPlanFragmentToAddDatetoIngredFragment(index);
        navController.navigate(action);
    }

}
