package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cmput301f22t09.shell379.data.Ingredient;

import org.junit.jupiter.api.Test;

import java.util.Date;

public class IngredientUnitTest {
    private Ingredient mockIngredient() {
        return new Ingredient("Milk",new Date(123,1,12),"Fridge",1,"Litres","liquid");
    }

    /**
     * Test the equals function of ingredients
     */
    @Test
    void testEquals() {
        Ingredient newI = mockIngredient();
        Ingredient secondI = mockIngredient();
        assertTrue(newI.equals(secondI));
    }


    /**
     * Test PartialEqual function of Ingredient class
     */
    @Test
    void testPartialEquals() {
        Ingredient newI = mockIngredient();
        // instance where the category and description is not matching
        Ingredient SecondI = new Ingredient("Water",new Date(123,11,12),null,1,"Litres","liquid");
        assertFalse(newI.partialEquals(SecondI));
        // instance where the category and description is matching
        Ingredient ThirdI = new Ingredient("Milk",new Date(123,2,12),null,1,"Litres","liquid");
        assertTrue(newI.partialEquals(ThirdI));
    }

    /**
     * Test the isFull function for ingredient class
     */
    @Test
    void testIsFull() {
        Ingredient newI = mockIngredient();
        // instance when all attributes are full(filled)
        assertTrue(newI.isFull());
        // instance when all attributes are not full(filled)
        Ingredient SecondI = new Ingredient("Milk",new Date(123,11,12),null,1,"Litres","liquid");
        assertFalse(SecondI.isFull());
    }

    /**
     * Test the getDateFormatted function for ingredient class
     */
    @Test
    void testGetDateFormatted() {
        Ingredient newI = mockIngredient();
        assertEquals(newI.getBestBeforeFormatted(), "12/02/2023");
    }
}
