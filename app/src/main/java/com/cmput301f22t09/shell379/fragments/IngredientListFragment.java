package com.cmput301f22t09.shell379.fragments;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.IngredientAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.util.ArraySortUtil;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.util.ArrayList;

/**
 * Fragment for ingredient list which implements the listener
 */
public class IngredientListFragment extends Fragment implements IngredientAdapter.AdapterListener {
    ArrayList<Ingredient> ingredientList;
    RecyclerView ingredient_recyclerView;
    RecyclerView.LayoutManager layoutManager;
    IngredientAdapter ingredientListAdapter;
    int selectedSortIndex;

    private NavController navController;
    private Environment envViewModel;

    public IngredientListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        envViewModel = Environment.of((AppCompatActivity) requireActivity());

        final Observer<ArrayList<Ingredient>> ingredientObserver = new Observer<ArrayList<Ingredient>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<Ingredient> ingredient) {
                // Update the UI, in this case, a TextView.
//                ingredient_recyclerView.setText(newName);
                if (ingredientListAdapter != null){
                    ingredientListAdapter.updateIngredient(envViewModel.getIngredients().getList());
                }
            }
        };
        envViewModel.getIngredients().getListLive().observe(this, ingredientObserver);
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_ingredient_list_2, container, false);

        // Implement the spinner option to sort the ingredient list
        Spinner spinner = (Spinner) rootView.findViewById(R.id.ingredient_sort_spinner);
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(
                getActivity(),
                android.R.layout.simple_spinner_item,
                Ingredient.getSortableProps()
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setSelection(0);
        selectedSortIndex = 0;

        // setting spinner events from khaled ben aissa, dec 21 2011
        // https://stackoverflow.com/questions/8597582/get-the-position-of-a-spinner-in-android
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // resorts the recycler view of ingredients
                selectedSortIndex = ((Spinner) rootView.findViewById(R.id.ingredient_sort_spinner)).getSelectedItemPosition();
                ingredientListAdapter.updateIngredient(
                        ArraySortUtil.sortByStringProp(envViewModel.getIngredients().getList(),
                        Ingredient.getStringPropGetter(selectedSortIndex))
                );
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                // do nothing
            }
        });

        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.floatingActionButton5)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );

        ((Button)rootView.findViewById(R.id.new_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navController.navigate(IngredientListFragmentDirections.actionIngredientListFragmentToCreateIngredientFragment());
                    }
                }
        );

        ingredientList = ArraySortUtil.sortByStringProp(envViewModel.getIngredients().getList(),
                Ingredient.getStringPropGetter(selectedSortIndex));
        layoutManager = new LinearLayoutManager(this.getActivity());
        ingredient_recyclerView = (RecyclerView) rootView.findViewById(R.id.ingredient_list_recyclerView);
        ingredient_recyclerView.setLayoutManager(layoutManager);

        ingredientListAdapter = new IngredientAdapter(ingredientList, envViewModel,this);
        ingredient_recyclerView.setAdapter(ingredientListAdapter);
        ingredient_recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    /**
     * Navigate the screen back to the previous one
     */
    private void back(){
        navController.popBackStack();
    }

    public void navigateToViewIngredient(int index){
        IngredientListFragmentDirections.ActionIngredientListFragmentToViewIngredientFragment action
                = IngredientListFragmentDirections.actionIngredientListFragmentToViewIngredientFragment(index);
        navController.navigate(action);
    }


}