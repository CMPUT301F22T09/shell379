package com.cmput301f22t09.shell379;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertTrue;

import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.cmput301f22t09.shell379.adapters.IngredientInRecipeAdapter;
import com.cmput301f22t09.shell379.adapters.MealPlanAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;

public class MealPlanUITest {

    /**
     * Starts in the main activity (home screen)
     */
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Tests all actions in the following sequence:
     *      Starting from the fragment_main_menu:
     *      1. Click on meal_plans_list_button
     */
    @Test
    public void test_29_30_31_32_33_34_35_36_39() {

        // Action 29
        while (true) {
            try {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException f) {
                    continue;
                }

                onView(withId(R.id.meal_plans_list_button)).perform(click());
                break;
            } catch (PerformException e) {
                Log.e("MealPlanUITest", e.getMessage());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException f) {

                }
            }
        }

        activityRule.getScenario().onActivity(activity -> {
            Environment env = new Environment();

            // Initialize env with one ingredient
            Ingredient ingredient = new Ingredient("Chicken Thighs", "Freezer", 2, "kilograms", "Poultry");
            env.getIngredients().add(ingredient);

            // Initialize env with one recipe
            IngredientStub stub = new IngredientStub("Milk", 2, "liters", "Dairy");
            Recipe recipe = new Recipe("Milk recipe", 2L, 3, "Dairy", "comment");
            recipe.addIngredient(stub);
            env.getRecipes().add(recipe);

            Environment.of(activity, env);
        });

        onView(withText("Meal Plan")).check(matches(isDisplayed()));

        // Action 30
        onView(withId(R.id.new_button)).perform(click());

        // Set meal plan name and comments since we can't save the meal plan without it
        onView(withId(R.id.plan_edit_name)).perform(replaceText("Chicken with milk"));
        onView(withId(R.id.plan_edit_comment_txt)).perform(replaceText("This is a comment!"));

        // Action 31
        onView(withId(R.id.plan_edit_add_recipes_btn)).perform(scrollTo(), click());

        // Action 32
        final int[] pos = {0};
        onView(withId(R.id.add_recipe_to_mealPlan_recyclerView)).perform(
                actionOnItemAtPosition(pos[0], click()));

        // Action 33
        onView(withId(R.id.save_button)).perform(scrollTo(), click());

        // Action 34
        onView(withId(R.id.plan_edit_add_ingr_btn)).perform(scrollTo(), click());

        // Action 35
        onView(withId(R.id.add_ingredient_to_mealPlan_recyclerView)).perform(
                actionOnItemAtPosition(pos[0], click()));

        // Set the ingredient servings to 2 since we can't save the screen without doing so
        onView(withId(R.id.serving_edittext)).perform(replaceText("2"));

        // Action 36
        onView(withId(R.id.save_button)).perform(scrollTo(), click());

        // Action 39
        onView(withId(R.id.mpe_save_plan)).perform(scrollTo(), click());

        // TODO: check that text looks like what its supposed to in the recycler view
        final int[] pos2 = {0};
        onView(withId(R.id.meal_plan_recyclerView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                MealPlanAdapter mealPlanAdapter = (MealPlanAdapter) recyclerView.getAdapter();
                ArrayList<MealPlan> mealPlans = mealPlanAdapter.getMealPlans();


                return true;
            }

            @Override
            public void describeTo(Description description) {
            }
        }));
    }

}
