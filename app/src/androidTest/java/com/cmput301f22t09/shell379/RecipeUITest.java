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

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.cmput301f22t09.shell379.adapters.IngredientAdapter;
import com.cmput301f22t09.shell379.adapters.IngredientInRecipeAdapter;
import com.cmput301f22t09.shell379.adapters.RecipeIngredientsListAdapter;
import com.cmput301f22t09.shell379.adapters.RecipeListAdapter;
import com.cmput301f22t09.shell379.adapters.fragmentadapters.CategoriesSelectRecViewAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import java.util.ArrayList;
import java.util.Date;

/**
 * Branches to be covered:  10  11  12  13  14  15  16
 * First test case covers:  10  11  12      14  15
 * Second test case covers: 10      12  13  14      16
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecipeUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Tests all actions mentioned in the sequence:
     * Starting from the fragment_main_menu:
     * 10. Click on recipes_list_button
     *      - App goes to fragment_recipe_list
     *      - Checks if "Recipes" is displayed
     * 11. Click on recipe_list_newButton
     *      - App goes to fragment_edit_recipe_9
     *      - Set recipe_name = "Rec1"
     * 14. Click on select_category
     *      - App launches dialog fragment fragment_categories_select
     *      - Set textInputEditText = "Cat1"
     * 15. Click on addButton
     *      - App goes back to fragment_edit_recipe_9
     *      - Set prepare_text = 123
     *      - Set serving_text = 456
     *      - Set comment_text = "This is a comment"
     * 12. Click on save_recipe
     *      - App goes to fragment_recipes_list
     *      - Checks that the last element of RecipeListAdapter
     *          is a Recipe object with the fields shown above
     */
    @Test
    public void test_10_11_14_15_18_22_25_27_28_12() {



        // action 10
        while (true) {
            try {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException f) {
                    continue;
                }

                onView(withId(R.id.recipes_list_button)).perform(click());
                break;
            } catch (PerformException e) {
                Log.e("RecipeUITest", e.getMessage());

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException f) {

                }
            }
        }

        activityRule.getScenario().onActivity(activity -> {
            // use 'activity'.
            Environment.of(activity, new Environment());

        });


        onView(withText("Recipes")).check(matches(isDisplayed()));

        // action 11
        onView(withId(R.id.recipe_list_newButton)).perform(click());
        onView(withId(R.id.recipe_name)).perform(scrollTo(), replaceText("Rec1"));

        // action 14
        onView(withId(R.id.select_category)).perform(scrollTo(), click());
        onView(withId(R.id.textInputEditText)).perform(replaceText("Cat1"));
        onView(withId(R.id.addButton)).perform(click());


        // action 15
        onView(withId(R.id.prepare_text)).perform(scrollTo(), replaceText("123"));
        onView(withId(R.id.serving_text)).perform(scrollTo(), replaceText("456"));
        onView(withId(R.id.comment_text)).perform(scrollTo(), replaceText("This is a comment"));


        // TODO: need to add ingredients

        // Action 18
        onView(withId(R.id.edit_ings_button)).perform(scrollTo(), click());
        onView(withText("Selected Ingredients")).check(matches(isDisplayed()));

        // action 22
        onView(withId(R.id.new_ingredient_stub_button)).perform(click());
        onView(withText("Add Ingredient")).check(matches(isDisplayed()));

        // action 25
        onView(withId(R.id.new_button)).perform(click());

        onView(withId(R.id.editDescription)).perform(replaceText("newIng"));
        onView(withId(R.id.editAmount)).perform( replaceText("2"));
        onView(withId(R.id.editCategory)).perform( click());
        onView(withId(R.id.textInputEditText)).perform( replaceText("newCat"));
        onView(withId(R.id.addButton)).perform(click());

        // action 27
        onView(withId(R.id.save_button)).perform(click());
        onView(withText("Selected Ingredients")).check(matches(isDisplayed()));

        // action 28
        onView(withId(R.id.bot_back_button)).perform(click());
        onView(withId(R.id.ingredientsInRep)).perform(scrollTo());

        final int[] pos = new int[1];
        onView(withId(R.id.ingredientsInRep)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                IngredientInRecipeAdapter ingListAdapter = (IngredientInRecipeAdapter) recyclerView.getAdapter();
                ArrayList<IngredientStub> ings = ingListAdapter.getIngredients();
                pos[0] = ingListAdapter.getItemCount()-1;
                IngredientStub ing = ings.get(pos[0]);

                assertTrue(ing.getDescription().equals("newIng"));
                assertTrue(ing.getCategory().equals("newCat"));
                assertTrue(ing.getAmount().equals(2));
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        // action 12
        onView(withId(R.id.save_recipe)).perform(scrollTo(), click());

        final int[] pos2 = new int[1];
        onView(withId(R.id.recipe_list_recyclerView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                RecipeListAdapter recipeListAdapter = (RecipeListAdapter) recyclerView.getAdapter();
                Recipe rec = recipeListAdapter.getRecipe(recipeListAdapter.getItemCount() - 1);
                pos2[0] = recipeListAdapter.getItemCount()-1;

                assertTrue(rec.getTitle().equals("Rec1"));
                assertTrue(rec.getCategory().equals("Cat1"));
                assertTrue(rec.getPreparationTime() == 123);
                assertTrue(rec.getServings() == 456);
                assertTrue(rec.getComments().equals("This is a comment"));

                ArrayList<IngredientStub> ings = rec.getIngredients();

                IngredientStub ing = ings.get(ings.size()-1);
                assertTrue(ing.getDescription().equals("newIng"));
                assertTrue(ing.getCategory().equals("newCat"));
                assertTrue(ing.getAmount().equals(2));

                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));
    }


    /**
     * This test relies on the last one completing
     * Tests all actions mentioned in the sequence:
     * Starting from the fragment_main_menu:
     * 10. Click on recipes_list_button
     *      - App goes to fragment_recipe_list
     *      - Checks if "Recipes" is displayed
     * 13. Click on the last item of recipe_list_recyclerView
     *      - App goes to fragment_edit_recipe_9
     *      - Set recipe_name = "Rec2"
     * 14. Click on select_category
     *      - App launches dialog fragment fragment_categories_select
     * 16. Click on categories_list at element 0 (selecting from existing categories)
     *      - App goes back to fragment_edit_recipe_9
     *      - Set prepare_text = 234
     *      - Set serving_text = 345
     *      - Set comment_text = This is another comment
     * 18. Click on edit_ings_button
     *      - check if "Selected Ingredients" is displayed
     * 22. Click on new_ingredient_stub_button
     *      - check if "Add Ingredient" is displayed
     * 23. Click on ingredients_list_button
     *      - check if "Select Ingredients" is displayed
     * 24. Check rsi_checkBox, fill in amount at rsi_amount and press bot_back_button
     * 19. Check if "Selected Ingredients" is displayed and click into the first item in rsi_recyclerView
     * 20. Check if all the details of the ingredients are saved and click the save button
     * 28. Check if all the details of the ingredients are displaying from the recycler view, press back
     * 12. Click on save_recipe
     *      - App goes to fragment_recipe_list
     *      - Checks that the last element of RecipeListAdapter
     *          is a Recipe object with the fields shown above
     * 13. Click into the recipe details
     * 17. Check if the recipe is deleted by checking the size of the recycler view
     *
     */
    @Test
    public void test_10_13_14_16_18_22_23_24_19_20_28_12_13_17() {

        // action 10
        while (true) {
            try {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException f) {
                    continue;
                }

                onView(withId(R.id.recipes_list_button)).perform(click());
                break;
            } catch (PerformException e) {
                Log.e("RecipeUITest", e.getMessage());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException f) {

                }
            }
        }
        onView(withText("Recipes")).check(matches(isDisplayed()));


        activityRule.getScenario().onActivity(activity -> {
            // use 'activity'.
            Environment env = Environment.of(activity, new Environment());
            env.getIngredientCategories().addCategory("drink");
            env.getIngredients().add(new Ingredient("Milk", new Date(123, 0, 1), "Fridge", 2, "grams", "drink"));
            env.getRecipeCategories().addCategory("Cat1");
            env.getRecipes().add(new Recipe("Rec2", 234L, 345, "Cat1", "This is another comment"));

        });

        // get length of the RecipeListAdapter
        final int[] pos = new int[1];
        onView(withId(R.id.recipe_list_recyclerView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {

            }

            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                RecipeListAdapter recipeListAdapter = (RecipeListAdapter) recyclerView.getAdapter();
                pos[0] = recipeListAdapter.getItemCount() - 1;
                return true;
            }
        }));

        // action 13
        onView(withId(R.id.recipe_list_recyclerView)).perform(actionOnItemAtPosition(pos[0], click()));
        onView(withId(R.id.recipe_name)).perform(scrollTo(), replaceText("Rec2"));

        // action 14
        onView(withId(R.id.select_category)).perform(scrollTo(), click());

        // action 16
        onView(withId(R.id.categories_list)).perform(actionOnItemAtPosition(0, click()));

        onView(withId(R.id.prepare_text)).perform(scrollTo(), replaceText("234"));
        onView(withId(R.id.serving_text)).perform(scrollTo(), replaceText("345"));
        onView(withId(R.id.comment_text)).perform(scrollTo(), replaceText("This is another comment"));

        // Action 18
        onView(withId(R.id.edit_ings_button)).perform(scrollTo(), click());
        onView(withText("Selected Ingredients")).check(matches(isDisplayed()));

        // action 22
        onView(withId(R.id.new_ingredient_stub_button)).perform(click());
        onView(withText("Add Ingredient")).check(matches(isDisplayed()));

        // action 23
        onView(withId(R.id.ingredients_list_button)).perform(click());
        onView(withText("Select Ingredients")).check(matches(isDisplayed()));

        // action 24
        onView(withId(R.id.rsi_checkBox)).perform(click());
        onView(withId(R.id.rsi_amount)).perform(replaceText("2"));
        onView(withId(R.id.bot_back_button)).perform(click());

        // action 19 click into first item to check the details
        onView(withText("Selected Ingredients")).check(matches(isDisplayed()));
        onView(withId(R.id.rsi_recyclerView)).perform(actionOnItemAtPosition(0, click()));

        // action 20
        onView(withText("Milk")).check(matches(isDisplayed()));
        onView(withText("drink")).check(matches(isDisplayed()));
        onView(withText("grams")).check(matches(isDisplayed()));
        onView(withText(containsString("2"))).check(matches(isDisplayed()));
        onView(withId(R.id.save_button)).perform(click());

        // action 28
        onView(withText("Milk")).check(matches(isDisplayed()));
        onView(withText("drink")).check(matches(isDisplayed()));
        onView(withText("2 grams")).check(matches(isDisplayed()));
        onView(withText(containsString("2"))).check(matches(isDisplayed()));
        onView(withId(R.id.bot_back_button)).perform(click());

        // action 12
        onView(withId(R.id.save_recipe)).perform(scrollTo(), click());

        onView(withId(R.id.recipe_list_recyclerView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                RecipeListAdapter recipeListAdapter = (RecipeListAdapter) recyclerView.getAdapter();
                Recipe rec = recipeListAdapter.getRecipe(recipeListAdapter.getItemCount() - 1);
                pos[0] = recipeListAdapter.getItemCount()-1;

                assertTrue(rec.getTitle().equals("Rec2"));
                assertTrue(rec.getCategory().equals("Cat1"));
                assertTrue(rec.getPreparationTime() == 234);
                assertTrue(rec.getServings() == 345);
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        // action 13
        onView(withId(R.id.recipe_list_recyclerView)).perform(actionOnItemAtPosition(0, click()));
        onView(withId(R.id.delete_recipe)).perform(scrollTo(), click());

        // action 17
        onView(withId(R.id.recipe_list_recyclerView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                RecipeListAdapter recipeListAdapter = (RecipeListAdapter) recyclerView.getAdapter();
                return 0 == recipeListAdapter.getItemCount();
            }
            @Override
            public void describeTo(Description description) {
            }
        }));


    }

}
