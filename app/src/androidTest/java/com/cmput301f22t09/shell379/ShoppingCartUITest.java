package com.cmput301f22t09.shell379;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import android.util.Log;

import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;

import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

public class ShoppingCartUITest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void testSetUp() {

        while (true) {
            try {

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException f) {
                    continue;
                }

                onView(withId(R.id.shopping_list_button)).perform(click());
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
            Environment env = new Environment();

            ArrayList<Ingredient> initIngs = new ArrayList<>();
            initIngs.add(new Ingredient("Ing1", new Date(2020, 12, 31), "Location", 10, "KG", "Get"));

            ArrayList<Recipe> initRec = new ArrayList<>();
            Recipe rec1 = new Recipe("Rec1", 20L, 3, "Good food", "This is good food");
            rec1.addIngredient(new IngredientStub("recIng1", 2, "kg", "Get"));
            rec1.addIngredient(new IngredientStub("recIng2", 2, "kg", "Get"));

            HashSet<String> cats = new HashSet<>();
            cats.add("Get");

            env.getRecipeCategories().setCategories(cats);
            env.getIngredientCategories().setCategories(cats);

            initRec.add(rec1);

            env.getIngredients().setList(initIngs);
            env.getRecipes().setList(initRec);
            Environment.of(activity, env);

        });
    }

    @Test
    public void test() {


    }

}
