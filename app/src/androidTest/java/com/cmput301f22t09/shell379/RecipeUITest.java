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

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.cmput301f22t09.shell379.adapters.IngredientAdapter;
import com.cmput301f22t09.shell379.adapters.RecipeListAdapter;
import com.cmput301f22t09.shell379.adapters.fragmentadapters.CategoriesSelectRecViewAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class RecipeUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void test_10_11_14_15_12() {



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

        // action 11
        onView(withId(R.id.recipe_list_newButton)).perform(click());
        onView(withId(R.id.recipe_name)).perform(scrollTo(), replaceText("Rec1"));

        // action 14
        onView(withId(R.id.select_category)).perform(scrollTo(), click());
        onView(withId(R.id.textInputEditText)).perform(replaceText("Cat1"));

        // action 15
        onView(withId(R.id.addButton)).perform(click());
        onView(withId(R.id.prepare_text)).perform(scrollTo(), replaceText("123"));
        onView(withId(R.id.serving_text)).perform(scrollTo(), replaceText("456"));
        onView(withId(R.id.comment_text)).perform(scrollTo(), replaceText("This is a comment"));

        // action 12
        onView(withId(R.id.save_recipe)).perform(scrollTo(), click());

        final int[] pos = new int[1];
        onView(withId(R.id.recipe_list_recyclerView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                RecipeListAdapter recipeListAdapter = (RecipeListAdapter) recyclerView.getAdapter();
                Recipe rec = recipeListAdapter.getRecipe(recipeListAdapter.getItemCount() - 1);
                pos[0] = recipeListAdapter.getItemCount()-1;

                assertTrue(rec.getTitle().equals("Rec1"));
                assertTrue(rec.getCategory().equals("Cat1"));
                assertTrue(rec.getPreparationTime() == 123);
                assertTrue(rec.getServings() == 456);
                assertTrue(rec.getComments().equals("This is a comment"));
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));
    }

    @Test
    public void test_10_13_14_16_12() {


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

        // action 12
        onView(withId(R.id.save_recipe)).perform(scrollTo(), click());

//        final int[] pos = new int[1];
        onView(withId(R.id.recipe_list_recyclerView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                RecipeListAdapter recipeListAdapter = (RecipeListAdapter) recyclerView.getAdapter();
                Recipe rec = recipeListAdapter.getRecipe(recipeListAdapter.getItemCount() - 1);
                pos[0] = recipeListAdapter.getItemCount()-1;

                assertTrue(rec.getTitle().equals("Rec2"));
//                assertTrue(rec.getCategory().equals("Cat1"));
                assertTrue(rec.getPreparationTime() == 234);
                assertTrue(rec.getServings() == 345);
                assertTrue(rec.getComments().equals("This is another comment"));
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));


    }

}
