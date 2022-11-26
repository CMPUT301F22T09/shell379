package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.util.ArraySortUtil;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

public class ArraySortUtilIngredientsUnitTest {

    ArrayList<Ingredient> testIngs;
    ArrayList<IngredientStub> testIngStubs;
    ArrayList<Recipe> testRecipes;

    void clearTestIngs(){
        testIngs = new ArrayList<Ingredient>();
        Ingredient milkIng =  new Ingredient("Milk",new Date(123,1,12),"Fridge",1,"Litres","Liquid");
        Ingredient bearIng = new Ingredient("Bear",new Date(399,1,12),"Store",30,"Kg","Meat");
        Ingredient popIng = new Ingredient("Pop",new Date(399,12,12),"Pantry",22,"G","Junk");
        testIngs.add(milkIng);
        testIngs.add(bearIng);
        testIngs.add(popIng);
    }

    /**
     * Test the description sort of ingredients
     */
    @Test
    void testDescriptionSortIngredients() {
        clearTestIngs();
        ArraySortUtil.sortByStringProp(testIngs, Ingredient.getStringPropGetter(0));
        assertEquals(testIngs.get(0).getDescription(),"Bear");
        assertEquals(testIngs.get(1).getDescription(),"Milk");
        assertEquals(testIngs.get(2).getDescription(),"Pop");
    }

    /**
     * Test the Date sort of ingredients
     */
    @Test
    void testDateSortIngredients() {
        clearTestIngs();
        ArraySortUtil.sortByStringProp(testIngs, Ingredient.getStringPropGetter(1));
        assertEquals(testIngs.get(0).getDescription(),"Milk");
        assertEquals(testIngs.get(1).getDescription(),"Bear");
        assertEquals(testIngs.get(2).getDescription(),"Pop");
    }

    /**
     * Test the location sort of ingredients
     */
    @Test
    void testLocationSortIngredients() {
        clearTestIngs();
        ArraySortUtil.sortByStringProp(testIngs, Ingredient.getStringPropGetter(2));
        assertEquals(testIngs.get(0).getLocation(),"Fridge");
        assertEquals(testIngs.get(1).getLocation(),"Pantry");
        assertEquals(testIngs.get(2).getLocation(),"Store");
    }

    /**
     * Test the category sort of ingredients
     */
    @Test
    void testCategorySortIngredients() {
        clearTestIngs();
        ArraySortUtil.sortByStringProp(testIngs, Ingredient.getStringPropGetter(3));
        assertEquals(testIngs.get(0).getCategory(),"Junk");
        assertEquals(testIngs.get(1).getCategory(),"Liquid");
        assertEquals(testIngs.get(2).getCategory(),"Meat");
    }
}

