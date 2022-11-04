package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cmput301f22t09.shell379.data.Recipe;

import org.junit.jupiter.api.Test;

public class RecipeUnitTest {
    private Recipe mockRecipe() {
        Recipe recipe = new Recipe("Milk", Integer.toUnsignedLong(10), 2, "Drink","yummy");
        return recipe;
    }

    /**
     * Test the Equals function for recipe class
     */
    @Test
    void testEquals() {
        Recipe newR = mockRecipe();
        Recipe secondR = mockRecipe();
        assertTrue(newR.equals(secondR));
    }

}
