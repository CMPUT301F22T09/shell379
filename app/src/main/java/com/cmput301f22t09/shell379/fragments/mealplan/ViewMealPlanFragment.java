package com.cmput301f22t09.shell379.fragments.mealplan;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.MealPlanAdapter;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.util.ArrayList;

public class ViewMealPlanFragment extends Fragment {
    private ArrayList<T> mealPlanList;
    private RecyclerView mealPlan_recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private MealPlanAdapter mealPlanListAdapter;
    private Environment envViewModel;
    private NavController navController;
//    private FloatingActionButton backButton;


    public ViewMealPlanFragment() {
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
        View rootView =  inflater.inflate(R.layout.fragment_meal_plan_13, container, false);

        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.MP_back_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );


        mealPlanList = envViewModel.getMealPlans().getList();
        layoutManager = new LinearLayoutManager(this.getActivity());
        mealPlan_recyclerView = (RecyclerView) rootView.findViewById(R.id.meal_plan_recyclerView);
        mealPlan_recyclerView.setLayoutManager(layoutManager);

        mealPlanListAdapter = new MealPlanAdapter(mealPlanList, envViewModel,this);
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
//        MealPlanListFragmentDirections.

//        IngredientListFragmentDirections.ActionIngredientListFragmentToViewIngredientFragment action
//                = IngredientListFragmentDirections.actionIngredientListFragmentToViewIngredientFragment(index);
//        navController.navigate(action);
    }
}
