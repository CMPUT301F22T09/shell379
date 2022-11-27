package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.util.ArraySortUtil;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;

public class ArraySortUtilIngredientStubUnitTest {

    ArrayList<IngredientStub> testIngStubs;
    ArrayList<Recipe> testRecipes;

    void clearTestIngs(){
        testIngStubs = new ArrayList<IngredientStub>();
        IngredientStub milkIng =  new IngredientStub("Milk",1,"Litres","Liquid");
        IngredientStub bearIng = new IngredientStub("Bear",30,"Kg","Meat");
        IngredientStub popIng = new IngredientStub("Pop",22,"G","Junk");
        testIngStubs.add(milkIng);
        testIngStubs.add(bearIng);
        testIngStubs.add(popIng);
    }

    /**
     * Test the description sort of ingredient stubs
     */
    @Test
    void testDescriptionSortIngredients() {
        clearTestIngs();
        ArraySortUtil.sortByStringProp(testIngStubs, IngredientStub.getStringPropGetter(0));
        assertEquals(testIngStubs.get(0).getDescription(),"Bear");
        assertEquals(testIngStubs.get(1).getDescription(),"Milk");
        assertEquals(testIngStubs.get(2).getDescription(),"Pop");
    }

    /**
     * Test the category sort of ingredient stubs
     */
    @Test
    void testCategorySortIngredients() {
        clearTestIngs();
        ArraySortUtil.sortByStringProp(testIngStubs, IngredientStub.getStringPropGetter(1));
        assertEquals(testIngStubs.get(0).getCategory(),"Junk");
        assertEquals(testIngStubs.get(1).getCategory(),"Liquid");
        assertEquals(testIngStubs.get(2).getCategory(),"Meat");
    }
}

