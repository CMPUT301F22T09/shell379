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
     * Test the amount sort of ingredient stubs
     */
    @Test
    void testAmountSortIngredients() {
        clearTestIngs();
        ArraySortUtil.sortByStringProp(testIngStubs, IngredientStub.getStringPropGetter(1));
        assertEquals(java.util.Optional.ofNullable(testIngStubs.get(0).getAmount()),1);
        assertEquals(java.util.Optional.ofNullable(testIngStubs.get(1).getAmount()),22);
        assertEquals(java.util.Optional.ofNullable(testIngStubs.get(2).getAmount()),30);
    }

    /**
     * Test the unit sort of ingredient stubs
     */
    @Test
    void testUnitSortIngredients() {
        clearTestIngs();
        ArraySortUtil.sortByStringProp(testIngStubs, IngredientStub.getStringPropGetter(2));
        assertEquals(testIngStubs.get(0).getUnit(),"G");
        assertEquals(testIngStubs.get(1).getUnit(),"Kg");
        assertEquals(testIngStubs.get(2).getUnit(),"Litres");
    }

    /**
     * Test the category sort of ingredient stubs
     */
    @Test
    void testCategorySortIngredients() {
        clearTestIngs();
        ArraySortUtil.sortByStringProp(testIngStubs, IngredientStub.getStringPropGetter(3));
        assertEquals(testIngStubs.get(0).getUnit(),"Junk");
        assertEquals(testIngStubs.get(1).getUnit(),"Meat");
        assertEquals(testIngStubs.get(2).getUnit(),"Liquid");
    }
}

