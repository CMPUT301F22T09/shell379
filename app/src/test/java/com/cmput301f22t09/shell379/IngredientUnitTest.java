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
     * Test the getDateFormatted function for ingredient class
     */
    @Test
    void testGetDateFormatted() {
        Ingredient newI = mockIngredient();
        assertEquals(newI.getBestBeforeFormatted(), "12/02/2023");
    }
}
