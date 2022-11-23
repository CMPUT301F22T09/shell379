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
        Recipe milkIng =  new Recipe("Milk",new Long(20),3,"Fun","A");
        Recipe bearIng = new Recipe("Bear",new Long(1),1,"Fast","C");
        Recipe popIng = new Recipe("Pop",new Long(3),2,"Bad","B");
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
        assertTrue(java.util.Optional.ofNullable(testRecipes.get(0).getPreparationTime()).equals( Optional.of(1)));
        assertTrue(java.util.Optional.ofNullable(testRecipes.get(1).getPreparationTime()).equals( Optional.of(3)));
        assertTrue(java.util.Optional.ofNullable(testRecipes.get(2).getPreparationTime()).equals(Optional.of(20)));
    }

    /**
     * Test the Category sort of recipes
     */
    @Test
    void testCategorySortRecipes() {
        clearTestRecipes();
        ArraySortUtil.sortByStringProp(testRecipes, Recipe.getStringPropGetter(2));
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
        ArraySortUtil.sortByStringProp(testRecipes, Recipe.getStringPropGetter(3));
        assertEquals(java.util.Optional.ofNullable(testRecipes.get(0).getCategory()),"A");
        assertEquals(java.util.Optional.ofNullable(testRecipes.get(1).getCategory()),"B");
        assertEquals(java.util.Optional.ofNullable(testRecipes.get(2).getCategory()),"C");
    }

}

