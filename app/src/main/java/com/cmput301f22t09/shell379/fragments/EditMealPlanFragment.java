package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.mealplan.edit.MPIngredientsEditListAdapter;
import com.cmput301f22t09.shell379.adapters.mealplan.edit.MPRecipesEditListAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.MealPlanViewModel;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;

public class EditMealPlanFragment extends Fragment {
    private ArrayList<Ingredient> ingredientsList;
    private ArrayList<Recipe> recipesList;
    private RecyclerView mpIngredientRecycler;
    private RecyclerView mpRecipeRecycler;
    private MPIngredientsEditListAdapter ingredientsAdapter;
    private MPRecipesEditListAdapter recipesAdapter;
    private Environment envViewModel;
    private NavController navController;
    private MealPlanViewModel mpViewModel;


    public EditMealPlanFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        navController = NavHostFragment.findNavController(this);
        envViewModel = Environment.of((AppCompatActivity) requireActivity());
        mpViewModel = MealPlanViewModel.of(requireActivity());

        // test data
//        MealPlan testMp = new MealPlan();
//        ArrayList<Ingredient> testI = new ArrayList<Ingredient>();
//        testI.add(new Ingredient("test",new Date(), "place",10, "kg", "kg"));
//        testMp.setIngredients(testI);
//        ArrayList<Recipe> testR = new ArrayList<Recipe>();
//        testR.add(new Recipe("e",new Long(1),10,"e","e"));
//        testMp.setRecipes(testR);
//        mpViewModel.setMealPlan(testMp);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView =  inflater.inflate(R.layout.fragment_edit_meal_plan_15, container, false);

        // Implement the button to back to previous page
        ((ImageView)rootView.findViewById(R.id.mpe_back_button)).setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        back();
                    }
                }
        );

        rootView.findViewById(R.id.plan_edit_add_recipes_btn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        navController.navigate(EditMealPlanFragmentDirections.actionEditMealPlanFragmentToAddRecipetoMealPlanFragment());
                    }
                }
        );

        rootView.findViewById(R.id.plan_edit_add_ingr_btn).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        navController.navigate(EditMealPlanFragmentDirections.actionEditMealPlanFragmentToAddIngredtoMealPlanFragment());
                    }
                }
        );

//        layoutManager = new LinearLayoutManager(this.getActivity());

        mpIngredientRecycler = (RecyclerView) rootView.findViewById(R.id.plan_edit_ingredients);
        mpRecipeRecycler = (RecyclerView) rootView.findViewById(R.id.plan_edit_recipes);

        ingredientsAdapter = new MPIngredientsEditListAdapter(mpViewModel);
        mpIngredientRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mpIngredientRecycler.setAdapter(ingredientsAdapter);
        mpIngredientRecycler.setItemAnimator(new DefaultItemAnimator());

        recipesAdapter = new MPRecipesEditListAdapter(mpViewModel);
        mpRecipeRecycler.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mpRecipeRecycler.setAdapter(recipesAdapter);
        mpRecipeRecycler.setItemAnimator(new DefaultItemAnimator());

        Log.e("MP_ADAPTER", ingredientsAdapter.getIngredients().toString());
        fillFields(rootView);

        rootView.findViewById(R.id.mpe_save_plan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                collectData(rootView);
            }
        });

        rootView.findViewById(R.id.mpe_cancel_plan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.popBackStack();
//                navController.navigate(EditMealPlanFragmentDirections.actionEditMealPlanFragmentToMealPlanListFragment());
            }
        });
        return rootView;
    }

    /**
     * Navigate the screen back to the previous one
     */
    private void back(){
        navController.popBackStack();
    }

    private void fillFields(View rootView) {
        Calendar startDate = Calendar.getInstance();
        startDate.setTime(mpViewModel.getMealPlan().getStartDate());
//        Date start_date = startDate.getTime();

        Calendar endDate = Calendar.getInstance();
        endDate.setTime(mpViewModel.getMealPlan().getEndDate());
//        Date end_date = endDate.getTime();

        ((TextView) rootView.findViewById(R.id.plan_edit_comment_txt))
                .setText(mpViewModel.getMealPlan().getComments());

        ((TextView) rootView.findViewById(R.id.plan_edit_name))
                .setText(mpViewModel.getMealPlan().getMealPlanName());


    }

    private void collectData(View rootView) {
        // validate
        if ( ((TextView) rootView.findViewById(R.id.plan_edit_comment_txt)).getText().toString().isEmpty()){
            showError("Comments not filled");
            return;
        }else if( ((TextView) rootView.findViewById(R.id.plan_edit_name)).getText().toString().isEmpty()){
            showError("Name not filled");
            return;
        }

        int amount = 0;
        if (mpViewModel.getIngredients().size() <= 0 && mpViewModel.getRecipes().size() <= 0) {
            showError("Choose some recipes or ingredients!");
            return;
        }


        mpViewModel.getMealPlan().setMealPlanName(
                ((TextView) rootView.findViewById(R.id.plan_edit_name)).getText().toString()
        );
        mpViewModel.getMealPlan().setComments(
                ((TextView) rootView.findViewById(R.id.plan_edit_comment_txt)).getText().toString()
        );

        Calendar cal = Calendar.getInstance(TimeZone.getDefault());
        DatePicker startDatePicker = rootView.findViewById(R.id.editPlanStart);
        Date startdate = new GregorianCalendar(
                startDatePicker.getYear(),
                startDatePicker.getMonth(),
                startDatePicker.getDayOfMonth()).getTime();

        cal.set(startDatePicker.getYear(), startDatePicker.getMonth(), startDatePicker.getDayOfMonth());
//        mpViewModel.getMealPlan().setStartDate(cal.getTime());
        mpViewModel.getMealPlan().setStartDate(startdate);

        DatePicker endDatePicker = rootView.findViewById(R.id.editPlanEnd);
        cal.set(endDatePicker.getYear(), endDatePicker.getMonth(), endDatePicker.getDayOfMonth());
        Date enddate = new GregorianCalendar(
                endDatePicker.getYear(),
                endDatePicker.getMonth(),
                endDatePicker.getDayOfMonth()).getTime();

        mpViewModel.getMealPlan().setEndDate(enddate);

        // check if the enda date is smaller than the start date
        if (enddate.compareTo(startdate) <0){
            showError("Please choose correct start and end date");
            return;
        }

        envViewModel.getMealPlans().setAtIdxOrAdd(mpViewModel.getIdx(), mpViewModel.getMealPlan());
        envViewModel.getMealPlans().commit();

        navController.popBackStack();
    }
    private void showError(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }
}
