package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.IngredientStub;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;

import org.junit.jupiter.api.Test;

import java.util.Date;

public class CartIngredientUnitTest {
    private CartIngredient mockCartIngredient() {
        return new CartIngredient("Milk", "Dairy", 22, "Litres");
    }

    /**
     * Test the equals function of ingredients
     */
    @Test
    void testEquals() {
        CartIngredient newI = mockCartIngredient();
        CartIngredient secondI = mockCartIngredient();
        assertTrue(newI.equals(secondI));
    }

    /**
     * Test the convertIngredient function of ingredients
     */
    @Test
    void testConvertIngredient() {
        Ingredient ingredient = new Ingredient("Milk",new Date(123,1,12),"Fridge",1,"Litres","liquid");
        CartIngredient newI =
                mockCartIngredient().convertIngredient(
                        ingredient
                );
        assertEquals(newI.getDescription(), ingredient.getDescription() );
        assertEquals(newI.getCategory(), ingredient.getCategory() );
        assertEquals(newI.getAmount(), ingredient.getAmount() );
        assertEquals(newI.getUnit(), ingredient.getUnit() );
    }

    /**
     * Test the convertIngredientStub function of ingredients
     */
    @Test
    void testConvertIngredientStub() {
        IngredientStub ingredientStub = new IngredientStub("Milk",1,"Litres","Liquid");
        CartIngredient newI =
                mockCartIngredient().convertIngredientStub(
                        ingredientStub
                );
        assertEquals(newI.getDescription(), ingredientStub.getDescription() );
        assertEquals(newI.getCategory(), ingredientStub.getCategory() );
        assertEquals(newI.getAmount(), ingredientStub.getAmount() );
        assertEquals(newI.getUnit(), ingredientStub.getUnit() );
    }
}
