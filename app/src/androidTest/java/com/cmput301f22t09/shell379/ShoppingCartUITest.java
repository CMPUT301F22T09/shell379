package com.cmput301f22t09.shell379;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import static com.cmput301f22t09.shell379.data.vm.infrastructure.SerializeEnvUtil.deserialize;
import static com.cmput301f22t09.shell379.data.vm.infrastructure.SerializeEnvUtil.serialize;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.PerformException;
import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.cmput301f22t09.shell379.adapters.IngredientAdapter;
import com.cmput301f22t09.shell379.adapters.ShoppingListAdapter;
import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import org.checkerframework.checker.units.qual.A;
import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;

public class ShoppingCartUITest {
    @Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);
    Environment original;

    @Before
    public void setUp() {
        activityRule.getScenario().onActivity(activity -> {
            // use 'activity'.
            original = Environment.of((AppCompatActivity) activity);
            HashMap<String, String> originalMap = serialize(original);
            original = deserialize(originalMap);
        });
    }

    @After
    public void tearDown() {
        activityRule.getScenario().onActivity(activity -> {
            // use 'activity'.
            Environment env = Environment.of(activity, original);
            env.getIngredients().commit();
        });
    }


    /**
     * we have Ing1 10kg expired
     *
     * we need Ing1 5kg
     * we need (recIng1, 10kg,), (recIng2, 10kg)
     *
     * This test sets up the ingredients, recipes, and meal plans to
     * test this scenario...
     */
    @Test
    public void testComputation() {

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


        HashMap<String, Integer> answers = new HashMap<>();
        answers.put("ing1", 5);
        answers.put("recing1", 10);
        answers.put("recing2", 10);



        // we have Ing1 10kg expired
        //
        // we need Ing1 5kg
        // we need (recIng1, 10kg,), (recIng2, 10kg)
        activityRule.getScenario().onActivity(activity -> {
            // use 'activity'.
            Environment env = new Environment();
            env = Environment.of(activity, env);

            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy");
            Date expired = new Date();
            Date mpstart = new Date();
            Date mpend = new Date();
            try {
                expired = sdf.parse("12-31-2020");
                mpstart = sdf.parse("12-20-2022");
                mpend = sdf.parse("12-30-2022");
            } catch (ParseException e) {
                e.printStackTrace();
            }


            ArrayList<Ingredient> initIngs = new ArrayList<>();
            initIngs.add(new Ingredient("Ing1", expired, "Location", 10, "kilograms", "Get"));

            ArrayList<Recipe> initRec = new ArrayList<>();
            Recipe rec1 = new Recipe("Rec1", 20L, 3, "Good food", "This is good food");
            rec1.addIngredient(new IngredientStub("recIng1", 2, "kilograms", "Get"));
            rec1.addIngredient(new IngredientStub("recIng2", 2, "kilograms", "Get"));

            initRec.add(rec1);

            HashSet<String> cats = new HashSet<>();
            cats.add("Get");

            env.getRecipeCategories().setCategories(cats);
//            env.getRecipeCategories().commit();

            env.getIngredientCategories().setCategories(cats);
//            env.getIngredientCategories().commit();

            MealPlan mp = new MealPlan("MP1", initRec, initIngs, mpstart, mpend, "test", 10);

            ArrayList<MealPlanWrapper<Ingredient>> ingWrappers = mp.getIngredients();
            for (int i = 0; i < ingWrappers.size(); i++) {
                MealPlanWrapper<Ingredient> ingWrapper = ingWrappers.get(i);
                ingWrapper.setServings(5);
                ingWrappers.set(i, ingWrapper);
            }
            mp.setIngredientsRaw(ingWrappers);

            ArrayList<MealPlanWrapper<Recipe>> recWrappers = mp.getRecipes();
            for (int i = 0; i < recWrappers.size(); i++) {
                MealPlanWrapper<Recipe> recWrapper = recWrappers.get(i);
                recWrapper.setServings(5);
                recWrappers.set(i, recWrapper);
            }
            mp.setRecipesRaw(recWrappers);

            ArrayList<MealPlan> mps = new ArrayList<>();
            mps.add(mp);


            env.getIngredients().setList(initIngs);
//            env.getIngredients().commit();
            env.getRecipes().setList(initRec);
//            env.getRecipes().commit();
            env.getMealPlans().setList(mps);
            env.getMealPlans().commit();

        });

        onView(withText("Shopping Cart")).check(matches(isDisplayed()));
        onView(withId(R.id.back)).perform(click());



//        onView(withId(R.id.ingredients_list_button)).perform(click());
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException f) {
//        }
//        onView(withId(R.id.floatingActionButton5)).perform(click());
//
//        onView(withId(R.id.recipes_list_button)).perform(click());
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException f) {
//        }
//        onView(withId(R.id.floatingActionButton6)).perform(click());
//
//        onView(withId(R.id.meal_plans_list_button)).perform(click());
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException f) {
//        }
//        onView(withId(R.id.MP_back_button)).perform(click());

        onView(withId(R.id.shopping_list_button)).perform(click());

        try {
            Thread.sleep(500);
        } catch (InterruptedException f) {
        }

        final int[] pos = {0};
        final String[] unit = new String[1];
        // back to fragment_ingredients_list
        onView(withId(R.id.shopping_list_recyclerView)).check(matches(new TypeSafeMatcher<View>() {
            @Override
            protected boolean matchesSafely(View item) {
                RecyclerView recyclerView = (RecyclerView) item;
                ShoppingListAdapter shoppingListAdapter = (ShoppingListAdapter) recyclerView.getAdapter();
                int size = shoppingListAdapter.getItemCount();
                ArrayList<CartIngredient> cartIngredients = shoppingListAdapter.getShoppingList();
                assertTrue("There should definitely be 3 items in the shopping cart.", size != 0);
                for (int i = 0; i < size; i++) {
                    CartIngredient ci = cartIngredients.get(i);
                    String desc = ci.getDescription();
                    Integer amount = ci.getAmount();
                    if (answers.containsKey(desc.toLowerCase())) {
                        assertEquals(answers.get(desc.toLowerCase()), amount);
                        Log.e("Answer", answers.get(desc.toLowerCase()).toString());
                        Log.e("Response", amount.toString());
                    } else {
                        fail();
                        return false;
                    }
                }
                return true;
            }

            @Override
            public void describeTo(Description description) {

            }
        }));



    }

}
