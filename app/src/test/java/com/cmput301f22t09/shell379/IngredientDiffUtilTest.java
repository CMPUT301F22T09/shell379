package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.assertEquals;

import android.util.Log;


import androidx.core.util.Pair;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.ShoppingCart;
import com.cmput301f22t09.shell379.data.util.IngredientDiffUtil;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.collections.CategorySet;
import com.cmput301f22t09.shell379.data.vm.collections.LiveCollection;

import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


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
        answers.put("celery", 90);
        answers.put("broccoli", 200);


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

        HashMap<String, Ingredient> result = IngredientDiffUtil.getIngredientsNeeded(mp);

        for (String s: result.keySet()) {
             assertEquals(answers.get(s), result.get(s).getAmount());
        }

    }


    @Test
    public void testGetIngredientsInStock() {

        HashMap<String, Integer> answers = new HashMap<String, Integer>();
        answers.put("celery", 160);
        answers.put("broccoli", 200);

        Environment env = new EnvironmentMock();

        HashMap<String, ArrayList<Ingredient> > results = IngredientDiffUtil.getIngredientsInStock(env);

        for (String s: results.keySet()) {

            Integer amount = 0;

            for (int i = 0; i < results.get(s).size(); i++) {
                amount += results.get(s).get(i).getAmount();
            }
            assertEquals(answers.get(s), amount);
        }

    }

    @Test
    public void testCoalesce() {
        Ingredient matcher = new Ingredient("broccoli", new Date(2022, 12, 31), "",  20, "g", "");
        ArrayList<Ingredient> toMatch = new ArrayList<>();

        // not expired
        toMatch.add( new Ingredient("crobboli", new Date(2023, 12, 31), "",  20, "g", ""));
        toMatch.add( new Ingredient("broccoli", new Date(2023, 12, 31), "",  40, "g", ""));

        // expired
        toMatch.add( new Ingredient("broccoli", new Date(2021, 12, 31), "",  20, "g", ""));


        Ingredient result = IngredientDiffUtil.coalesce(matcher, toMatch);

        assertEquals(result.getAmount(), new Integer(60));

    }



    @Test
    public void testComputeSubtractAndBuyList () {

        HashMap<String, Ingredient> needed = new HashMap<>();

        {
            needed.put("broccoli", new Ingredient(
                    "Broccoli",
                    new Date(2022, 12, 31),
                    "", 50, "g", ""));

            needed.put("celery", new Ingredient(
                    "cElery",
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

        HashMap <String, ArrayList<Ingredient> > have = new HashMap <String, ArrayList<Ingredient> >();

        ArrayList<Ingredient> broccoli = new ArrayList<Ingredient>();
        ArrayList<Ingredient> celery = new ArrayList<Ingredient>();
        ArrayList<Ingredient> yogurt = new ArrayList<Ingredient>();

        // we have 150 broccoli, however 50 are expired
        {
            broccoli.add(new Ingredient(
                    "Broccoli",
                    new Date(2023, 12, 31),
                    "", 50, "g", ""));
            broccoli.add(new Ingredient(
                    "Broccoli",
                    new Date(2023, 12, 31),
                    "", 50, "g", ""));
            broccoli.add(new Ingredient(
                    "Broccoli",
                    new Date(2021, 12, 31),
                    "", 50, "g", ""));
        }

        // we have 100 celery
        {
            celery.add(new Ingredient(
                    "cElery",
                    new Date(2023, 12, 31),
                    "", 100, "g", ""));
        }

        // we dont have bread

        // we have 100 yogurt
        {
            yogurt.add(new Ingredient(
                    "yogurT",
                    new Date(2023, 12, 31),
                    "", 100, "g", ""));

        }



        have.put("broccoli", broccoli);
        have.put("celery", celery);
        have.put("yogurt", yogurt);

        Pair<ArrayList<Ingredient>, ArrayList<Ingredient>> result
                = IngredientDiffUtil.computeSubtractAndBuyList(needed, have);



    }




    @Test
    public void testMPHasEnoughIngredients () {


        ArrayList<IngredientStub> recIngs = new ArrayList<>();
        ArrayList<Ingredient> mpIngs = new ArrayList<>();


        recIngs.add(new IngredientStub("Celery", 40,  "kg", "shell"));
        recIngs.add(new IngredientStub("Broccoli",200, "g", "shell"));

        mpIngs.add(new Ingredient("Celery", "Pantry", 40,  "kg", "shell"));
        mpIngs.add(new Ingredient("Celery","Pantry", 10, "kg", "shell"));


        Recipe r = new Recipe("Rec1", 20L, 13, "Veg", "");
        r.setIngredients(recIngs);
        ArrayList<Recipe> recs = new ArrayList<>();
        recs.add(r);


        MealPlan mp = new MealPlan("Test1",
                recs,
                mpIngs,
                new Date(2020, 12, 31),
                new Date(2022, 1, 3),
                "",
                0);

        Environment env = new EnvironmentMock();



        Pair<ArrayList<Ingredient>, ArrayList<Ingredient>> variableName
                = IngredientDiffUtil.computeDiff(mp, env);

        ArrayList<Ingredient> subFromInv = variableName.first;

        for (int i = 0; i < subFromInv.size(); i++) {
            Ingredient ing = subFromInv.get(i);
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
            ingredients.add(new Ingredient("Celery",
                    new Date(2023, 12, 30),
                    "Pantry",
                    10,
                    "kg",
                    "Vegetables"));

            // different ingredient
            ingredients.add(new Ingredient("Broccoli",
                    new Date(2023, 12, 30),
                    "Pantry",
                    200,
                    "g",
                    "Vegetables"));

            // dup of first, except with different bb date
            ingredients.add(new Ingredient("Celery",
                    new Date(2023, 12, 31),
                    "Pantry",
                    30,
                    "kg",
                    "Vegetables"));

            // dup of first, with same bb date
            ingredients.add(new Ingredient("Celery",
                    new Date(2023, 12, 30),
                    "Pantry",
                    30,
                    "kg",
                    "Vegetables"));

            // dup of first, expired
            ingredients.add(new Ingredient("Celery",
                    new Date(2021, 12, 31),
                    "Pantry",
                    60,
                    "kg",
                    "Vegetables"));

            // dup of first, except with different cases in description
            ingredients.add(new Ingredient("celEry",
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

//    public class LiveCollection<T> {
//
//        ArrayList<T> lst;
//        public LiveCollection() {
//            ArrayList<T> lst = new ArrayList<T>();
//        }
//
//
//        public ArrayList<T> getList() {
//            return lst;
//        }
//
//
//        public void add(T obj) {
//            lst.add(obj);
//        }
//
//        public void add(ArrayList<T> lst) {
//            this.lst.addAll(lst);
//        }
//
//
//        public void removeAtIdx(int i) {
//            this.lst.remove(i);
//        }
//
//        public void setList(ArrayList<T> lst) {
//            this.lst.clear();
//            this.lst.addAll(lst);
//        }
//    }


}

