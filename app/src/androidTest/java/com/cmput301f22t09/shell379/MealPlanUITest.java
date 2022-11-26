package com.cmput301f22t09.shell379;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.util.Log;

import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.cmput301f22t09.shell379.data.vm.Environment;

import org.junit.Rule;
import org.junit.Test;

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
            // use 'activity'.
            Environment.of(activity, new Environment());

        });

        

    }

}
