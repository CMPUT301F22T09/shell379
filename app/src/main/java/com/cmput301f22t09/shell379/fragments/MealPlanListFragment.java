package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.IngredientAdapter;
import com.cmput301f22t09.shell379.adapters.MealPlanAdapter;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MealPlanListFragment extends Fragment {
    private ArrayList<MealPlan> mealPlanList;
    private RecyclerView mealPlan_recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MealPlanAdapter mealPlanListAdapter;
    private Environment envViewModel;
    private NavController navController;
    private FloatingActionButton backButton;


    public MealPlanListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        envViewModel = Environment.of((AppCompatActivity) requireActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_meal_plan, container, false);

        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.MP_back_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );


        mealPlanList = envViewModel.getIngredients().getFullList();
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
}