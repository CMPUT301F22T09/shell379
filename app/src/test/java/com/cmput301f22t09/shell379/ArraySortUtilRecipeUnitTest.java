package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.util.ArraySortUtil;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;

public class ArraySortUtilRecipeUnitTest {

    ArrayList<Recipe> testRecipes;

    void clearTestRecipes(){
        testRecipes = new ArrayList<Recipe>();
        Recipe milkIng =  new Recipe("Milk",20L,32323,"Fun","A");
        Recipe bearIng = new Recipe("Bear",1L,122,"Fast","C");
        Recipe popIng = new Recipe("Pop",3L,2123,"Bad","B");
        testRecipes.add(milkIng);
        testRecipes.add(bearIng);
        testRecipes.add(popIng);
    }

    /**
     * Test the title sort of recipe
     */
    @Test
    void testTitleSortRecipes() {
        clearTestRecipes();
        ArraySortUtil.sortByStringProp(testRecipes, Recipe.getStringPropGetter(0));
        assertEquals(testRecipes.get(0).getTitle(),"Bear");
        assertEquals(testRecipes.get(1).getTitle(),"Milk");
        assertEquals(testRecipes.get(2).getTitle(),"Pop");
    }

    /**
     * Test the time sort of recipes
     */
    @Test
    void testTimeSortRecipes() {
        clearTestRecipes();
        ArraySortUtil.sortByStringProp(testRecipes, Recipe.getStringPropGetter(1));
        assertTrue(testRecipes.get(0).getPreparationTime().equals(1L));
        assertTrue(testRecipes.get(1).getPreparationTime().equals(3L));
        assertTrue(testRecipes.get(2).getPreparationTime().equals(20L));
    }

    /**
     * Test the Category sort of recipes
     */
    @Test
    void testCategorySortRecipes() {
        clearTestRecipes();
        ArraySortUtil.sortByStringProp(testRecipes, Recipe.getStringPropGetter(3));
        assertEquals(testRecipes.get(0).getCategory(),"Bad");
        assertEquals(testRecipes.get(1).getCategory(),"Fast");
        assertEquals(testRecipes.get(2).getCategory(),"Fun");
    }

    /**
     * Test the comment sort of recipes
     */
    @Test
    void testAmountSortRecipes() {
        clearTestRecipes();
        ArraySortUtil.sortByStringProp(testRecipes, Recipe.getStringPropGetter(2));
        assertTrue(testRecipes.get(0).getServings().equals(122));
        assertTrue(testRecipes.get(1).getServings().equals(2123));
        assertTrue(testRecipes.get(2).getServings().equals(32323));
    }

}

