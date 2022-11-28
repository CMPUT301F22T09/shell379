package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import android.graphics.PixelFormat;
import android.media.Image;
import android.media.ImageReader;
import android.util.Log;

import com.cmput301f22t09.shell379.data.Recipe;

import org.junit.jupiter.api.Test;

public class RecipeLogicTest {

    String template_title;
    Long template_prep;
    Integer template_servings;
    String template_cat;
    String template_com;

    public RecipeLogicTest() {
        template_title = "Title";
        template_prep = 1L;
        template_servings = 1;
        template_cat = "Food";
        template_com = "Comments";
    }

    @Test
    public void testConstructor_000() {
        Recipe rec = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        assertEquals(rec.getTitle(), template_title);
        assertEquals(rec.getPreparationTime(), template_prep);
        assertEquals(rec.getServings(), template_servings);
        assertEquals(rec.getCategory(), template_cat);
        assertEquals(rec.getComments(), template_com);
    }

    /**
     * Title Setter Test
     * Input: "new title"
     * Expected output: "new title"
     */
    @Test
    public void testSetTitle_000() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        recipe.setTitle("new title");
        assertEquals(recipe.getTitle(), "new title");
    }

    /**
     * Title setter test
     * Input: "new title"
     * Expected result: "new title"
     */
    @Test
    public void testSetTitle_001() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        recipe.setTitle("new title");
        assertEquals(recipe.getTitle(), "new title");
    }

    /**
     * Testing setPreparationTime with valid value
     * Input: 1L
     * Expected Behaviour: No Action
     */
    @Test
    public void testSetPreparationTime_000() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        try{
            recipe.setPreparationTime(1L);
        } catch(Exception e) {
            fail("Exception should not have been thrown.");
        }

    }

    /**
     * Testing setPreparationTime with zero value
     * Input: 0L
     * Expected Behaviour: IllegalArgumentException
     */
    @Test
    public void testSetPreparationTime_001() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        recipe.setPreparationTime(1000000L);
        assertEquals(recipe.getPreparationTime().longValue(), 1000000L);
    }

    /**
     * Testing setServings method with valid value
     * Input: 1
     * Expected Behaviour: No Action
     */
    @Test
    public void testSetServings_000() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        try {
            recipe.setServings(1);
        } catch (Exception e) {
            fail("Exception should not have been thrown!");
        }
    }

    /**
     * Testing setServings method with zero value
     * Input: 0
     * Expected Output: 0
     */
    @Test
    public void testSetServings_001() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        recipe.setServings(0);
        assertEquals(recipe.getServings().intValue(), 0);
    }

    /**
     * Testing setCategory method with valid string
     * Input: "valid cat"
     * Expected Behaviour: No Action
     */
    @Test
    public void testSetCategory_000() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        try {
            recipe.setCategory("valid cat");
        } catch (Exception e) {
            fail("No exception should have been thrown.");
        }
    }
    /**
     * Testing setCategory method with empty string
     * Input: ""
     * Expected Behaviour: IllegalArgumentException
     */
    @Test
    public void testSetCategory_001() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        recipe.setCategory("cat");
        assertEquals(recipe.getCategory(), "cat");
    }
}
