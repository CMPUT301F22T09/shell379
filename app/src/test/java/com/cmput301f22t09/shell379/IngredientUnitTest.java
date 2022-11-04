package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cmput301f22t09.shell379.data.Ingredient;

import org.junit.jupiter.api.Test;

import java.util.Date;

public class IngredientUnitTest {
    private Ingredient mockIngredient() {
        return new Ingredient("Milk",new Date(2023,11,12),"fridge",1,"Litres","liquid");
    }

    @Test
    void testEquals() {
        Ingredient newI = mockIngredient();
        Ingredient secondI = mockIngredient();
        assertTrue(newI.equals(secondI));
    }
}
