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
        try {
            Recipe rec = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        } catch (Exception e) {
            fail("This test should not throw any exceptions!");
        }
    }
    @Test
    public void testConstructor_001() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recipe rec = new Recipe("", template_prep, template_servings, template_cat, template_com);
        }, "Title should not be empty.");
    }



    @Test
    public void testConstructor_002() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recipe rec = new Recipe(template_title, 0L, template_servings, template_cat, template_com);
        }, "Preparation time cannot be negative.");
    }
    @Test
    public void testConstructor_003() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recipe rec = new Recipe(template_title, -1L, template_servings, template_cat, template_com);
        }, "Preparation time must be greater than 0.");
    }





    @Test
    public void testConstructor_004() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recipe rec = new Recipe(template_title, template_prep, 0, template_cat, template_com);
        }, "Servings must be greater than 0");
    }
    @Test
    public void testConstructor_005() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recipe rec = new Recipe(template_title, template_prep, -1, template_cat, template_com);
        }, "Servings must be greater than 0");
    }





    @Test
    public void testConstructor_006() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recipe rec = new Recipe(template_title, template_prep, template_servings, "", template_com);
        }, "Category must be defined and non-empty");
    }




    @Test
    public void testConstructor_007() {
        try {
            assertEquals(1, 0);
            Recipe rec = new Recipe(template_title, template_prep, template_servings, template_cat, template_com, null);
        } catch (Exception e) {
            fail("This should not throw an exception");
        }
    }

}
