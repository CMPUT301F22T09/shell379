package com.cmput301f22t09.shell379;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static org.junit.Assert.assertTrue;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.cmput301f22t09.shell379.adapters.IngredientAdapter;
import com.cmput301f22t09.shell379.adapters.RecipeListAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.Recipe;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

public class RecipeUITest {

    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void test_10_11_14_15_12() {

        // action 10
        onView(withId(R.id.recipes_list_button)).perform(click());
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

        onView(withId(R.id.recipe_list_recyclerView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                RecipeListAdapter recipeListAdapter = (RecipeListAdapter) recyclerView.getAdapter();
//                Recipe rec = recipeListAdapter.(ingredientAdapter.getItemCount() - 1);
//                pos[0] = ingredientAdapter.getItemCount()-1;
//
//                assertTrue(ing.getDescription().equals("Ing1"));
//                assertTrue(ing.getLocation().equals("Pantry"));
//                assertTrue(ing.getAmount() == 34);
//                assertTrue(ing.getCategory().equals("Cat"));
//                assertTrue(ing.getBestBeforeFormatted().equals("30/12/2030"));
//                unit[0] = ing.getUnit();
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
        onView(withId(R.id.recipes_list_button)).perform(click());
        onView(withText("Recipes")).check(matches(isDisplayed()));
    }

}
