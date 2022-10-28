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
            Recipe rec = new Recipe(null, template_prep, template_servings, template_cat, template_com);
        }, "Title should not be null.");
    }




    @Test
    public void testConstructor_003() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recipe rec = new Recipe(template_title, 0L, template_servings, template_cat, template_com);
        }, "Preparation time cannot be negative.");
    }
    @Test
    public void testConstructor_004() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recipe rec = new Recipe(template_title, -1L, template_servings, template_cat, template_com);
        }, "Preparation time must be greater than 0.");
    }





    @Test
    public void testConstructor_005() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recipe rec = new Recipe(template_title, template_prep, 0, template_cat, template_com);
        }, "Servings must be greater than 0");
    }
    @Test
    public void testConstructor_006() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recipe rec = new Recipe(template_title, template_prep, -1, template_cat, template_com);
        }, "Servings must be greater than 0");
    }





    @Test
    public void testConstructor_007() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recipe rec = new Recipe(template_title, template_prep, template_servings, "", template_com);
        }, "Category must be defined and non-empty");
    }
    @Test
    public void testConstructor_008() {
        assertThrows(IllegalArgumentException.class, () -> {
            Recipe rec = new Recipe(template_title, template_prep, template_servings, null, template_com);
        }, "Category must be defined and non-empty");
    }





    @Test
    public void testConstructor_009() {
        try {
            assertEquals(1, 0);
            Recipe rec = new Recipe(template_title, template_prep, template_servings, template_cat, template_com, null);
        } catch (Exception e) {
            fail("This should not throw an exception");
        }
    }





    /**
     * Title must be non-empty and non-null
     * Input: ""
     * Expected result: IllegalArgumentException
     */
    @Test
    public void testSetTitle_000() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        assertThrows(IllegalArgumentException.class, () -> {
            recipe.setTitle("");
        });
    }

    /**
     * Title must be non-empty and non-null
     * Input: null
     * Expected result: IllegalArgumentException
     */
    @Test
    public void testSetTitle_001() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        assertThrows(IllegalArgumentException.class, () -> {
            recipe.setTitle(null);
        });
    }

    /**
     * Set title valid case
     * Input: "valid title"
     * Expected result: No Action
     */
    @Test
    public void testSetTitle_002() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        try {
            recipe.setTitle("valid title");
        } catch (Exception e) {
            fail("No exception should have been thrown");
        }
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
        assertThrows(IllegalArgumentException.class, ()->{
            recipe.setPreparationTime(0L);
        });

    }
    /**
     * Testing setPreparationTime with negative value
     * Input: -1L
     * Expected Behaviour: IllegalArgumentException
     */
    @Test
    public void testSetPreparationTime_002() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        assertThrows(IllegalArgumentException.class, ()->{
            recipe.setPreparationTime(-1L);
        });

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
     * Expected Behaviour: IllegalArgumentException
     */
    @Test
    public void testSetServings_001() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        assertThrows(IllegalArgumentException.class, ()->{
            recipe.setServings(0);
        });
    }
    /**
     * Testing setServings method with negative value
     * Input: 0
     * Expected Behaviour: IllegalArgumentException
     */
    @Test
    public void testSetServings_002() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        assertThrows(IllegalArgumentException.class, ()->{
            recipe.setServings(-1);
        });
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
        assertThrows(IllegalArgumentException.class, ()->{
            recipe.setCategory("");
        });
    }
    /**
     * Testing setCategory method with null
     * Input: null
     * Expected Behaviour: IllegalArgumentException
     */
    @Test
    public void testSetCategory_002() {
        Recipe recipe = new Recipe(template_title, template_prep, template_servings, template_cat, template_com);
        assertThrows(IllegalArgumentException.class, ()->{
            recipe.setCategory(null);
        });
    }


}
