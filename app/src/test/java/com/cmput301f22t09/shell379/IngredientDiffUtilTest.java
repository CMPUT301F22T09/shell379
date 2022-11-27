package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


import android.util.Log;

import androidx.core.util.Pair;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.util.IngredientDiffUtil;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.collections.CategorySet;
import com.cmput301f22t09.shell379.data.vm.collections.LiveCollection;
import com.cmput301f22t09.shell379.data.vm.collections.ShoppingCart;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;


@ExtendWith(InstantExecutorExtension.class)
public class IngredientDiffUtilTest {

    @Test
    public void testGetIngredientsNeeded() {
        ArrayList<IngredientStub> recIngs = new ArrayList<>();
        ArrayList<Ingredient> mpIngs = new ArrayList<>();

        recIngs.add(new IngredientStub("Celery", 40,  "kg", "shell"));
        recIngs.add(new IngredientStub("Broccoli",200, "g", "shell"));

        mpIngs.add(new Ingredient("Celery", "Pantry", 40,  "kg", "shell"));
        mpIngs.add(new Ingredient("Celery","Pantry", 10, "kg", "shell"));

        HashMap<String, Integer> answers = new HashMap<String, Integer>();
        answers.put("Celery", 522);
        answers.put("Broccoli", 2600);

        Recipe r = new Recipe("Rec1", 20L, 13, "Veg", "");
        r.setIngredients(recIngs);
        ArrayList<Recipe> recs = new ArrayList<>();
        recs.add(r);

        // all best-befores should be within the range
        MealPlan mp = new MealPlan("Test1",
                recs,
                mpIngs,
                new Date(2020, 12, 31),
                new Date(2030, 1, 3),
                "",
                0);
        ArrayList<MealPlan> mealPlans = new ArrayList<>();
        mealPlans.add(mp);
        Map<String, CartIngredient> result = IngredientDiffUtil.flattenMealPlans(mealPlans);

        for (String s: result.keySet()) {
            assertEquals(answers.get(s), result.get(s).getAmount());
        }

    }

    @Test
    public void testCleanCart() {

        ArrayList<CartIngredient> cart = new ArrayList<>();
        cart.add(new CartIngredient("ingr1", "cat", 10, "g"));
        cart.add(new CartIngredient("ingr2", "cat", 10, "g"));
        cart.add(new CartIngredient("ingr3", "cat", 10, "g"));
        cart.add(new CartIngredient("ingr4", "cat", 10, "g"));
        cart.add(new CartIngredient("ingr5", "cat", 10, "g"));

        ArrayList<CartIngredient> cleanedCart = IngredientDiffUtil.cleanCart(cart);
        assertEquals(cleanedCart.size(), 0);

        cart.get(0).setPickedUp(true);
        cart.get(3).setPickedUp(true);

        cleanedCart = IngredientDiffUtil.cleanCart(cart);
        assertEquals(cleanedCart.size(), 2);
        assertEquals(cart.get(0).getDescription(), cleanedCart.get(0).getDescription());
        assertEquals(cart.get(3).getDescription(), cleanedCart.get(1).getDescription());

    }

    @Test
    public void testComputeSubtractAndBuyList () {

        HashMap<String, Ingredient> needed = new HashMap<>();

        {
            needed.put("broccoli", new Ingredient(
                    "broccoli",
                    new Date(2022, 12, 31),
                    "", 50, "g", ""));

            needed.put("celery", new Ingredient(
                    "celery",
                    new Date(2022, 12, 31),
                    "", 100, "g", ""));

            needed.put("bread", new Ingredient(
                    "bread",
                    new Date(2022, 12, 31),
                    "", 200, "g", ""));

            needed.put("yogurt", new Ingredient(
                    "yogurt",
                    new Date(2022, 12, 31),
                    "", 200, "g", ""));

        }

        ArrayList<Ingredient> broccoli = new ArrayList<Ingredient>();
        ArrayList<Ingredient> celery = new ArrayList<Ingredient>();
        ArrayList<Ingredient> yogurt = new ArrayList<Ingredient>();

        Date past = new Date(new Date().getTime()-1000000);
        Date future = new Date(new Date().getTime()+1000000);
        {
            broccoli.add(new Ingredient(
                    "broccoli",
                    past,
                    "", 100, "g", ""));

            broccoli.add(new Ingredient(
                    "broccoli",
                    future,
                    "", 50, "g", ""));
            broccoli.add(new Ingredient(
                    "broccoli",
                    future,
                    "", 50, "g", ""));
        }

        // we have 100 celery
        {
            celery.add(new Ingredient(
                    "celery",
                    future,
                    "", 100, "g", ""));
        }

        // we dont have bread

        // we have 100 yogurt
        {
            yogurt.add(new Ingredient(
                    "yogurt",
                    future,
                    "", 100, "g", ""));

        }
        ArrayList<Ingredient> totalHave = new ArrayList<>();
        totalHave.addAll(broccoli);
        totalHave.addAll(celery);
        totalHave.addAll(yogurt);

        Map<String, CartIngredient> neededConverted = needed.values().stream()
                .map(CartIngredient::convertIngredient)
                .collect(Collectors.toMap(CartIngredient::getDescription, Function.identity()));

        HashMap<String, Integer> addToCartAnswers = new HashMap<String, Integer>();
        addToCartAnswers.put("yogurt", 100);
        addToCartAnswers.put("bread", 200);
        addToCartAnswers.put("broccoli", -50);
        addToCartAnswers.put("celery", 0);

        ArrayList<CartIngredient> result
                = new ArrayList<>(IngredientDiffUtil.subtractIngredientStorage(neededConverted, totalHave).values());

        for (int i = 0; i < result.size(); i++) {
            assertEquals(addToCartAnswers.get(result.get(i).getDescription().toLowerCase()), result.get(i).getAmount());
        }
    }

    @Test
    public void testTransferCartData() {
        ArrayList<CartIngredient> cart = new ArrayList<>();
        cart.add(new CartIngredient("ingr1", "cat", 10, "g"));
        cart.get(0).setPickedUp(true);
        cart.add(new CartIngredient("ingr2", "cat4", 10, "g"));
        cart.get(1).setPickedUp(true);
        cart.add(new CartIngredient("ingr3", "cat", 10, "g"));
        cart.add(new CartIngredient("ingr4", "cat", 10, "g"));
        cart.add(new CartIngredient("ingr5", "cat", 10, "g"));

        Map<String, CartIngredient> inputMap = new HashMap<>();
        CartIngredient ingr = new CartIngredient("ingr1", "cat1", 10, "g");
        inputMap.put("ingr1", ingr);
        ingr = new CartIngredient("ingr2", "cat1", 10, "g");
        inputMap.put("ingr2", ingr);

        ArrayList<CartIngredient> newCart = IngredientDiffUtil.transferCartData(inputMap, cart);
        CartIngredient cartIngredient = newCart.stream().filter(e->e.getDescription()=="ingr1").findFirst().get();
        assertEquals("cat", cartIngredient.getCategory());
        assertEquals(true, cartIngredient.getPickedUp());
        cartIngredient = newCart.stream().filter(e->e.getDescription()=="ingr2").findFirst().get();
        assertEquals("cat4", cartIngredient.getCategory());
        assertEquals(true, cartIngredient.getPickedUp());
    }

    /**
     * This function tests the integration of all methods. Also tests the newly implemented MealPlanWrapper
     * used to multiply the ingredient amounts
     */
    @Test
    public void testPrepareCart() {
        ArrayList<IngredientStub> recIngs = new ArrayList<>();
        ArrayList<Ingredient> mpIngs = new ArrayList<>();

        // these are multiplied by 3, so 120 celery and 600 broccoli
        recIngs.add(new IngredientStub("celery", 40,  "kg", "shell"));
        recIngs.add(new IngredientStub("broccoli",200, "g", "shell"));

        // These are all multiplied by 2, so 100 celeries
        mpIngs.add(new Ingredient("celery", "Pantry", 40,  "kg", "shell"));
        mpIngs.add(new Ingredient("celery","Pantry", 10, "kg", "shell"));

        Recipe r = new Recipe("Rec1", 20L, 13, "Veg", "");
        r.setIngredients(recIngs);
        ArrayList<Recipe> recs = new ArrayList<>();
        recs.add(r);

        MealPlan mp = new MealPlan("", recs, mpIngs, new Date(2020, 12, 31), new Date(2022, 12, 31), "String comments", 3);

        ArrayList<MealPlanWrapper<Ingredient>> ingWrapper = mp.getIngredients();
        for (int i = 0; i < ingWrapper.size(); i++) {
            MealPlanWrapper unitIng = ingWrapper.get(i);
            unitIng.setServings(2);
            ingWrapper.set(i, unitIng);
        };

        ArrayList<MealPlanWrapper<Recipe>> recWrapper = mp.getRecipes();
        for (int i = 0; i < recWrapper.size(); i++) {
            MealPlanWrapper unitRec = recWrapper.get(i);
            unitRec.setServings(3);
            recWrapper.set(i, unitRec);
        };

        // TOTAL: NEED 220 celery, 600 broccoli
        mp.setIngredientsRaw(ingWrapper);
        mp.setRecipesRaw(recWrapper);

        // WE HAVE: 200 broccoli, 100 celery
        Environment environmentMock = new EnvironmentMock();

        // SET MOCK ENV MEAL PLANS
        environmentMock.getMealPlans().add(mp);

        HashMap<String, Integer> addToCartAnswers = new HashMap<String, Integer>();
        addToCartAnswers.put("celery", 1404);
        addToCartAnswers.put("broccoli", 7600);

        // we should have 120 celery 400 broccoli in shopping cart
        IngredientDiffUtil.prepareCart(environmentMock);

        ArrayList<CartIngredient> result = environmentMock.getCart().getList();
        assertFalse(result.size()==0);
        for (int i = 0; i < result.size(); i++) {
            System.out.println(result.get(i).getDescription());
            assertEquals(addToCartAnswers.get(result.get(i).getDescription().toLowerCase()), result.get(i).getAmount());
        }
    }

    public class EnvironmentMock extends Environment {

        private LiveCollection<Ingredient> ingredients;
        private LiveCollection<Recipe> recipes;
        private LiveCollection<MealPlan> mealPlans;
        private ShoppingCart cart;
        private CategorySet ingredientCategories;
        private CategorySet recipeCategories;
        private CategorySet locationCategories;

        public EnvironmentMock () {
//            super();
            ingredients = new LiveCollection<Ingredient>();
            recipes = new LiveCollection<Recipe>();
            mealPlans = new LiveCollection<MealPlan>();
            cart = new ShoppingCart();
            ingredientCategories = new CategorySet();
            recipeCategories = new CategorySet();
            locationCategories = new CategorySet();



            // test 1
            ingredients.add(new Ingredient("celery",
                    new Date(2023, 12, 30),
                    "Pantry",
                    10,
                    "kg",
                    "Vegetables"));

            // different ingredient
            ingredients.add(new Ingredient("broccoli",
                    new Date(2023, 12, 30),
                    "Pantry",
                    200,
                    "g",
                    "Vegetables"));

            // dup of first, except with different bb date
            ingredients.add(new Ingredient("celery",
                    new Date(2023, 12, 31),
                    "Pantry",
                    30,
                    "kg",
                    "Vegetables"));

            // dup of first, with same bb date
            ingredients.add(new Ingredient("celery",
                    new Date(2023, 12, 30),
                    "Pantry",
                    30,
                    "kg",
                    "Vegetables"));

            // dup of first, expired
            ingredients.add(new Ingredient("celery",
                    new Date(2021, 12, 31),
                    "Pantry",
                    60,
                    "kg",
                    "Vegetables"));

            ingredients.add(new Ingredient("celery",
                    new Date(2023, 12, 30),
                    "Pantry",
                    30,
                    "kg",
                    "Vegetables"));

            ingredients.commit();
        }



        public LiveCollection<Ingredient> getIngredients() {
            return ingredients;
        }


        public LiveCollection<Recipe> getRecipes() {
            return recipes;
        }


        public ShoppingCart getCart() {
            return cart;
        }


        public CategorySet getIngredientCategories() {
            return ingredientCategories;
        }


        public CategorySet getRecipeCategories() {
            return recipeCategories;
        }


        public CategorySet getLocationCategories() {
            return locationCategories;
        }

        public LiveCollection<MealPlan> getMealPlans() {
            return mealPlans;
        }
    }
}

