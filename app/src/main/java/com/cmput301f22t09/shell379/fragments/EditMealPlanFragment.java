package com.cmput301f22t09.shell379.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

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
    // private FloatingActionButton backButton;


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
                navController.popBackStack();
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
        Date startDate = mpViewModel.getMealPlan().getStartDate();
        Date endDate = mpViewModel.getMealPlan().getEndDate();
        ((TextView) rootView.findViewById(R.id.plan_edit_comment_txt))
                .setText(mpViewModel.getMealPlan().getComments());
        ((DatePicker) rootView.findViewById(R.id.editPlanStart))
                .updateDate(startDate.getYear(), startDate.getMonth(), startDate.getDay());
        ((DatePicker) rootView.findViewById(R.id.editPlanEnd))
                .updateDate(endDate.getYear(), endDate.getMonth(), endDate.getDay());
        ((TextView) rootView.findViewById(R.id.plan_edit_name))
                .setText(mpViewModel.getMealPlan().getMealPlanName());
    }

    private void collectData(View rootView) {
        mpViewModel.getMealPlan().setMealPlanName(
                ((TextView) rootView.findViewById(R.id.plan_edit_name)).getText().toString()
        );
        mpViewModel.getMealPlan().setComments(
                ((TextView) rootView.findViewById(R.id.plan_edit_comment_txt)).getText().toString()
        );

        Calendar cal = Calendar.getInstance();
        DatePicker picker = rootView.findViewById(R.id.editPlanStart);
        cal.set(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());
        mpViewModel.getMealPlan().setStartDate(cal.getTime());

        picker = rootView.findViewById(R.id.editPlanEnd);
        cal.set(picker.getYear(), picker.getMonth(), picker.getDayOfMonth());
        mpViewModel.getMealPlan().setEndDate(cal.getTime());

        envViewModel.getMealPlans().setAtIdxOrAdd(mpViewModel.getIdx(), mpViewModel.getMealPlan());
        envViewModel.getMealPlans().commit();
    }


}
