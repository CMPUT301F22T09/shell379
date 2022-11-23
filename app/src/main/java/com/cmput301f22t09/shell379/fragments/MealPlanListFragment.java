package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.MealPlanAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.vm.EditRecipeViewModel;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MealPlanListFragment extends Fragment implements MealPlanAdapter.AdaptorListener{
    private ArrayList<MealPlan> mealPlanList;
    private RecyclerView mealPlan_recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MealPlanAdapter mealPlanListAdapter;
    private Environment envViewModel;
    private NavController navController;
    private MealPlanViewModel mealPlanViewModel;
//    private FloatingActionButton backButton;


    public MealPlanListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        envViewModel = Environment.of((AppCompatActivity) requireActivity());
        mealPlanViewModel =  new ViewModelProvider(requireActivity()).get(MealPlanViewModel.class);

        final Observer<ArrayList<MealPlan>> mealPlanObserver = new Observer<ArrayList<MealPlan>>() {
            @Override
            public void onChanged(@Nullable final ArrayList<MealPlan> mealPlans) {
                // Update the UI, in this case, a TextView.
//                ingredient_recyclerView.setText(newName);
                if (mealPlanListAdapter != null){
                    mealPlanListAdapter.updateMealPlan(envViewModel.getMealPlans().getList());
                }
            }
        };
        envViewModel.getMealPlans().getListLive().observe(this, mealPlanObserver);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_meal_plan_13, container, false);

        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.MP_back_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );


        ((Button)rootView.findViewById(R.id.new_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        navController.navigate(MealPlanListFragmentDirections.actionMealPlanFragmentToCreateMealPlanFragment());
                    }
                }
        );


        mealPlanList = envViewModel.getMealPlans().getList();
        layoutManager = new LinearLayoutManager(this.getActivity());
        mealPlan_recyclerView = (RecyclerView) rootView.findViewById(R.id.meal_plan_recyclerView);
        mealPlan_recyclerView.setLayoutManager(layoutManager);

        mealPlanListAdapter = new MealPlanAdapter(mealPlanList, envViewModel,this, requireActivity());
        mealPlan_recyclerView.setAdapter(mealPlanListAdapter);
        mealPlan_recyclerView.setItemAnimator(new DefaultItemAnimator());

        return rootView;
    }

    /**
     * Navigate the screen back to the previous one
     */
    private void back(){
        navController.popBackStack();
    }

    public void navigateToViewMealPlan(int index){
    //    to-do
        MealPlanListFragmentDirections.ActionMealPlanFragmentToViewMealPlanFragment action
                = MealPlanListFragmentDirections.actionMealPlanFragmentToViewMealPlanFragment(index);
        navController.navigate(action);

    }

}