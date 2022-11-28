package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.*;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.util.IngredientDiffUtil;
import com.cmput301f22t09.shell379.data.wrapper.MealPlanWrapper;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

class MealPlanWrapperTest {

    @Test
    void convertRecipeToIngredientListTest() {
        ArrayList<IngredientStub> recIngs = new ArrayList<>();
        recIngs.add(new IngredientStub("celery", 40,  "kg", "shell"));
        recIngs.add(new IngredientStub("broccoli",200, "g", "shell"));
        Recipe recipe = new Recipe("Rec1", 20L, 1, "Veg", "");
        recipe.setIngredients(recIngs);
        MealPlanWrapper<Recipe> mealPlan = new MealPlanWrapper<Recipe>(recipe, new Date((new Date()).getTime()+1000000), 12);

        ArrayList<IngredientStub> ingredients = MealPlanWrapper.convertRecipeToIngredientList(mealPlan);

        assertFalse(ingredients.size()==0);
        HashMap<String, Integer> answers = new HashMap<>();
        answers.put("celery", 40*12);
        answers.put("broccoli", 200*12);

        ingredients
            .forEach(e->{
                Integer answer = answers.get(e.getDescription());
                assertEquals(answer, e.getAmount());
            });
    }

    @Test
    void convertToIngredient() {
        ArrayList<MealPlanWrapper<Ingredient>> ingredients = new ArrayList<>();
        HashMap<String, Ingredient> answers = new HashMap<>();

        Ingredient ing = new Ingredient("celery", "Pantry", 40,  "kg", "shell");
        ingredients.add(new MealPlanWrapper<>(ing, new Date(), ing.getAmount()));
        answers.put("celery", ing);

        ing = new Ingredient("broccoli","Pantry", 10, "kg", "shell");
        ingredients.add(new MealPlanWrapper<>(ing, new Date(), ing.getAmount()));
        answers.put("broccoli", ing);

        ingredients.stream()
                .map(MealPlanWrapper::convertToIngredient)
                .forEach(e->{
                    assertEquals(answers.get(e.getDescription()).getDescription(), e.getDescription());
                    assertEquals(answers.get(e.getDescription()).getAmount(), e.getAmount());
                    assertEquals(answers.get(e.getDescription()), e);
                });
    }
}