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
        assertEquals(testIngs.get(0).getDescription(),"Pop");
        assertEquals(testIngs.get(1).getDescription(),"Bear");
        assertEquals(testIngs.get(2).getDescription(),"Milk");
    }

    /**
     * Test the location sort of ingredients
     */
    @Test
    void testLocationSortIngredients() {
        clearTestIngs();
        ArraySortUtil.sortByStringProp(testIngs, Ingredient.getStringPropGetter(1));
        assertEquals(testIngs.get(0).getLocation(),"Fridge");
        assertEquals(testIngs.get(1).getLocation(),"Pantry");
        assertEquals(testIngs.get(2).getLocation(),"Store");
    }

    /**
     * Test the amount sort of ingredients
     */
    @Test
    void testAmountSortIngredients() {
        clearTestIngs();
        ArraySortUtil.sortByStringProp(testIngs, Ingredient.getStringPropGetter(1));
        assertEquals(testIngs.get(0).getAmount(),1);
        assertEquals(testIngs.get(1).getAmount(),22);
        assertEquals(testIngs.get(2).getAmount(),30);
    }

    /**
     * Test the unit sort of ingredients
     */
    @Test
    void testUnitSortIngredients() {
        clearTestIngs();
        ArraySortUtil.sortByStringProp(testIngs, Ingredient.getStringPropGetter(1));
        assertEquals(testIngs.get(0).getUnit(),"G");
        assertEquals(testIngs.get(1).getUnit(),"Kg");
        assertEquals(testIngs.get(2).getUnit(),"Litres");
    }

    /**
     * Test the category sort of ingredients
     */
    @Test
    void testCategorySortIngredients() {
        clearTestIngs();
        ArraySortUtil.sortByStringProp(testIngs, Ingredient.getStringPropGetter(1));
        assertEquals(testIngs.get(0).getUnit(),"Junk");
        assertEquals(testIngs.get(1).getUnit(),"Meat");
        assertEquals(testIngs.get(2).getUnit(),"Liquid");
    }
}

