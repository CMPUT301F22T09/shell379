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
import static androidx.test.espresso.matcher.ViewMatchers.withChild;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.contrib.PickerActions;

import static org.hamcrest.CoreMatchers.anything;
import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.cmput301f22t09.shell379.adapters.IngredientAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.google.firebase.firestore.ServerTimestamp;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IngredientsUITest {


    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void test_1_2_3_8_5_9_7_8_6() {

        // action 1
        onView(withText("Ingredients")).perform(click());
//        onView(withId(R.id.ingredient_list_recyclerView)).check(matches(isDisplayed()));

        // action 2
        onView(withId(R.id.new_button)).perform(click());
        onView(withText("Name/description")).check(matches(isDisplayed()));
        onView(withText("Best Before")).check(matches(isDisplayed()));
        onView(withText("Location")).check(matches(isDisplayed()));


        onView(withId(R.id.editDescription)).perform(scrollTo(), replaceText("Ing1"));
        onView(withId(R.id.editBestBeforeDate)).perform(PickerActions.setDate(2030, 12, 30));
        onView(withId(R.id.editLocation)).perform(scrollTo(),replaceText("Pantry"));
        onView(withId(R.id.editAmount)).perform(scrollTo(),replaceText("34"));
        onView(withId(R.id.editCategory)).perform(scrollTo(),replaceText("Cat"));

        // action 3
        onView(withId(R.id.save_button)).perform(scrollTo(), click());

        // Checking contents of recyclerview at the new item
        final int[] pos = {0};
        final String[] unit = new String[1];
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
                assertTrue(ing.getBestBeforeFormatted().equals("30/12/2030"));
                unit[0] = ing.getUnit();
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        // action 8
        onView(withId(R.id.ingredient_list_recyclerView)).perform(
                actionOnItemAtPosition(pos[0], click()));

        onView(withText("Ing1")).check(matches(isDisplayed()));
        onView(withText("30/12/2030")).check(matches(isDisplayed()));
        onView(withText("Pantry")).check(matches(isDisplayed()));
        onView(withText("34testUnit")).check(matches(isDisplayed()));
        onView(withText("Cat")).check(matches(isDisplayed()));

        // action 5
        onView(withId(R.id.edit_ingredient_button)).perform(click());

        onView(withId(R.id.editDescription)).perform(scrollTo(), replaceText("Ing2"));
        onView(withId(R.id.editBestBeforeDate)).perform(PickerActions.setDate(2030, 10, 30));
        onView(withId(R.id.editLocation)).perform(scrollTo(),replaceText("Fridge"));
        onView(withId(R.id.editAmount)).perform(scrollTo(),replaceText("35"));
        onView(withId(R.id.editCategory)).perform(scrollTo(),replaceText("Cat2"));


        // action 9
        onView(withId(R.id.save_button)).perform(scrollTo(), click());

        // action 7
        onView(withId(R.id.back)).perform(scrollTo(), click());

        // Checking saved edited ingredient
        final int[] pos2 = {0};
        final String[] unit2 = new String[1];
        // back to fragment_ingredients_list
        onView(withId(R.id.ingredient_list_recyclerView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                IngredientAdapter ingredientAdapter = (IngredientAdapter) recyclerView.getAdapter();
                Ingredient ing = ingredientAdapter.getIngredient(ingredientAdapter.getItemCount() - 1);
                pos2[0] = ingredientAdapter.getItemCount()-1;

                assertTrue(ing.getDescription().equals("Ing2"));
                assertTrue(ing.getLocation().equals("Fridge"));
                assertTrue(ing.getAmount() == 35);
                assertTrue(ing.getCategory().equals("Cat2"));
                assertTrue(ing.getBestBeforeFormatted().equals("30/10/2030"));
                unit2[0] = ing.getUnit();
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));

        // After editing, the size of the recyclerview should not change
        assertEquals(pos[0], pos2[0]);

        // action 8
        onView(withId(R.id.ingredient_list_recyclerView)).perform(
                actionOnItemAtPosition(pos2[0], click()));

        onView(withText("Ing2")).check(matches(isDisplayed()));
        onView(withText("30/10/2030")).check(matches(isDisplayed()));
        onView(withText("Fridge")).check(matches(isDisplayed()));
        onView(withText("35testUnit")).check(matches(isDisplayed()));
        onView(withText("Cat2")).check(matches(isDisplayed()));


        // action 6
        onView(withId(R.id.delete_ingredient_button)).perform(click());

        // Checking to see that RecyclerView has shrunk by 1
        final int[] pos3 = new int[1];
        onView(withId(R.id.ingredient_list_recyclerView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                IngredientAdapter ingredientAdapter = (IngredientAdapter) recyclerView.getAdapter();
                pos3[0] = ingredientAdapter.getItemCount()-1;
                return true;
            }
            @Override
            public void describeTo(Description description) {
            }
        }));

        assertEquals(pos2[0]-1, pos3[0]);




    }

    @Test
    public void test_1_2_4() {
        // action 1
        onView(withText("Ingredients")).perform(click());

        // action 2
        onView(withId(R.id.new_button)).perform(click());
        onView(withText("Name/description")).check(matches(isDisplayed()));
        onView(withText("Best Before")).check(matches(isDisplayed()));
        onView(withText("Location")).check(matches(isDisplayed()));

        // action 4
        onView(withId(R.id.cancel_button)).perform(scrollTo(), click());
        onView(withId(R.id.new_button)).check(matches(isDisplayed()));


    }






}
