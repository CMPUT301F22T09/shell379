package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.cmput301f22t09.shell379.data.util.ArraySortUtil;
import com.cmput301f22t09.shell379.data.wrapper.CartIngredient;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class ArraySortUtilCartIngredientUnitTest {

    ArrayList<CartIngredient> testCartIngredients;

    void clearTestCartIngredients(){
        testCartIngredients = new ArrayList<CartIngredient>();
        CartIngredient milkIng =  new CartIngredient("Milk","Dairy",3,"A");
        CartIngredient bearIng = new CartIngredient("Bear","Meat",1,"C");
        CartIngredient popIng = new CartIngredient("Pop","Drink",2,"B");
        testCartIngredients.add(milkIng);
        testCartIngredients.add(bearIng);
        testCartIngredients.add(popIng);
    }

    /**
     * Test the title sort of cart ingredients
     */
    @Test
    void testTitleSortCartIngredients() {
        clearTestCartIngredients();
        ArraySortUtil.sortByStringProp(testCartIngredients, CartIngredient.getStringPropGetter(0));
        assertEquals(testCartIngredients.get(0).getDescription(),"Bear");
        assertEquals(testCartIngredients.get(1).getDescription(),"Milk");
        assertEquals(testCartIngredients.get(2).getDescription(),"Pop");
    }

    /**
     * Test the Category sort of cart ingredients
     */
    @Test
    void testCategorySortCartIngredients() {
        clearTestCartIngredients();
        ArraySortUtil.sortByStringProp(testCartIngredients, CartIngredient.getStringPropGetter(1));
        assertEquals(testCartIngredients.get(0).getCategory(),"Dairy");
        assertEquals(testCartIngredients.get(1).getCategory(),"Drink");
        assertEquals(testCartIngredients.get(2).getCategory(),"Meat");
    }
}

