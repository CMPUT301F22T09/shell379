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
import androidx.test.espresso.contrib.PickerActions;
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
     *      29. Click on meal_plans_list_button
     *           - App goes to fragment_meal_plan_13
     *           - Checks if "Meal Plan" is displayed
     *      30. Click on new_button
     *           - App goes to fragment_meal_plan_15
     *      31. Click on plan_edit_add_recipes_btn
     *           - App goes to fragment_add_recipe_meal_plan_16
     *      32. Click on an existing recipe in recipe_list_item_7
     *           - App goes to mealplan_recipe_date_17
     *      33. Click on save_button
     *           - App goes back to fragment_edit_meal_plan_15
     *      34. Click on plan_edit_add_ingr_btn
     *           - App goes to fragment_add_ingredient_meal_plan_18
     *      35. Click on an existing ingredient in ingredient_content_2
     *           - App goes to mealplan_ingredient_date_19
     *      36. Click on save_button
     *           - App goes back to fragment_edit_meal_plan_15
     *      39. Click on mpe_save_plan
     *           - App goes back to fragment_meal_plan_13
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

        // Initialize ingredient and recipe which will be used to populate the environment
        final Ingredient ingredient = new Ingredient("Chicken Thighs", "Freezer", 2, "kilograms", "Poultry");
        final IngredientStub stub = new IngredientStub("Milk", 2, "liters", "Dairy");
        final Recipe recipe = new Recipe("Milk recipe", 2L, 3, "Dairy", "comment");
        recipe.addIngredient(stub);

        activityRule.getScenario().onActivity(activity -> {
            Environment env = new Environment();

            // Initialize env with one ingredient and one recipe
            env.getIngredients().add(ingredient);
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
                pos2[0] = mealPlanAdapter.getItemCount() - 1;
                MealPlan mp = mealPlans.get(pos2[0]);

                assertTrue(mp.getMealPlanName().equals("Chicken with milk"));

                return true;
            }

            @Override
            public void describeTo(Description description) {
            }
        }));
    }

    /**
     * Tests all actions in the following sequence:
     *      Starting from the fragment_main_menu
     *      29. Click on meal_plans_list_button
     *           - App goes to fragment_meal_plan_13
     *           - Checks if "Meal Plan" is displayed
     *      41. Click on an existing meal plan in fragment_meal_plan_content_13
     *           - App goes to fragment_view_meal_plan_14
     *      42. Click on edit_plan
     *           - App goes to fragment_edit_meal_plan_15
     *           - Edit the meal plan details and add an ingredient
     *      43. Click on mpe_save_plan
     *           - App goes back to fragment_view_meal_plan_14
     *      45. Click on delete_plan
     *           - App goes back to fragment_meal_plan_13
     *           - Meal plan is deleted
     */
    @Test
    public void test_29_41_42_43_45() {

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

            // Initialize env with one ingredient and one recipe
            final Ingredient ingredient = new Ingredient("Chicken Thighs", "Freezer", 2, "kilograms", "Poultry");
            env.getIngredients().add(ingredient);

            // Initialize env with a meal plan
            MealPlan mp = new MealPlan();
            env.getMealPlans().add(mp);

            Environment.of(activity, env);
        });

        onView(withText("Meal Plan")).check(matches(isDisplayed()));

        // Action 41
        final int[] pos = {0};
        onView(withId(R.id.meal_plan_recyclerView)).perform(
                actionOnItemAtPosition(pos[0], click()));

        // Action 42
        onView(withId(R.id.edit_plan)).perform(click());

        // Edit the meal plan details
        onView(withId(R.id.plan_edit_name)).perform(replaceText("Lunch Prep"));
        onView(withId(R.id.plan_edit_comment_txt)).perform(replaceText("Preparing lunch for the week"));
        onView(withId(R.id.editPlanStart)).perform(scrollTo(), PickerActions.setDate(2022, 11, 26));
        onView(withId(R.id.editPlanEnd)).perform(scrollTo(), PickerActions.setDate(2022, 11, 30));

        // Add an ingredient to the meal plan
        onView(withId(R.id.plan_edit_add_ingr_btn)).perform(scrollTo(), click());
        onView(withId(R.id.add_ingredient_to_mealPlan_recyclerView)).perform(
                actionOnItemAtPosition(pos[0], click()));
        onView(withId(R.id.serving_edittext)).perform(replaceText("2"));
        onView(withId(R.id.save_button)).perform(scrollTo(), click());

        // Action 43
        onView(withId(R.id.mpe_save_plan)).perform(scrollTo(), click());

        // Action 45
        onView(withId(R.id.delete_plan)).perform(scrollTo(), click());
    }

}
