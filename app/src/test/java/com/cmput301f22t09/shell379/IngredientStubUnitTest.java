package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;

import org.junit.jupiter.api.Test;

import java.util.Date;

public class IngredientStubUnitTest {
    private IngredientStub mockIngredientStub() {
        return new IngredientStub("Milk",1,"Litres","Liquid");
    }

    /**
     * Test the equals function of ingredients
     */
    @Test
    void testEquals() {
        IngredientStub newI = mockIngredientStub();
        IngredientStub secondI = mockIngredientStub();
        assertTrue(newI.equals(secondI));
    }

    /**
     * Test the getDateFormatted function for ingredient class
     */
    @Test
    void testLooseEquals() {
        IngredientStub newI = mockIngredientStub();
        IngredientStub secondI = mockIngredientStub();
        secondI.setAmount(2);
        assertTrue(newI.looseEquals(secondI));
        assertFalse(newI.equals(secondI));
    }
}
