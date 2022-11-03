package com.cmput301f22t09.shell379;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.swipeUp;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.contrib.PickerActions;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertTrue;

import android.view.View;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.cmput301f22t09.shell379.adapters.IngredientAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IngredientsUITest {


    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void test_1_2_3_8_5_4_8_6_4() {


        onView(withText("Ingredients")).perform(click());
        onView(withId(R.id.ingredient_list_recyclerView)).check(matches(isDisplayed()));
        onView(withId(R.id.new_button)).perform(click());
        onView(withText("Name/description")).check(matches(isDisplayed()));
        onView(withText("Best Before")).check(matches(isDisplayed()));
        onView(withText("Location")).check(matches(isDisplayed()));

        onView(withId(R.id.editDescription)).perform(scrollTo(), replaceText("Ing1"));
        onView(withId(R.id.editBestBeforeDate)).perform(PickerActions.setDate(2022, 12, 30));
        onView(withId(R.id.editLocation)).perform(scrollTo(),replaceText("Pantry"));
//        onView(withId())
        onView(withId(R.id.editAmount)).perform(scrollTo(),replaceText("34"));
        onView(withId(R.id.editCategory)).perform(scrollTo(),replaceText("Cat"));

        onView(withId(R.id.save_button)).perform(scrollTo(), click());


        final int[] pos = {0};
        // back to fragment_ingredients_list
        onView(withId(R.id.ingredient_list_recyclerView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                IngredientAdapter ingredientAdapter = (IngredientAdapter) recyclerView.getAdapter();
                Ingredient ing = ingredientAdapter.getIngredient(ingredientAdapter.getItemCount() - 1);
                pos[0] = ingredientAdapter.getItemCount()-1;

                assertTrue(ing.getDescription().equals("Ing1"));
                assertTrue(ing.getLocation().equals("Pantry"));
                assertTrue(ing.getAmount() == 34);
                assertTrue(ing.getCategory().equals("Cat"));
                assertTrue(ing.getBestBeforeFormatted().equals("30/12/2022"));
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        onView(withId(R.id.ingredient_list_recyclerView)).perform(actionOnItemAtPosition(pos[0], click()));
    }




}
