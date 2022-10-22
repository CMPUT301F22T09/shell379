package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

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
        }, "Preparation time must be positive definite.");
    }
    @Test
    public void testConstructor_003() {

    }
    @Test
    public void testConstructor_004() {

    }
    @Test
    public void testConstructor_005() {

    }


}
