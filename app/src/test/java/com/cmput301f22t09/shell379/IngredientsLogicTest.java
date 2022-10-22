package com.cmput301f22t09.shell379;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import android.util.Log;

import com.cmput301f22t09.shell379.data.Ingredient;


import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;

public class IngredientsLogicTest {

    SimpleDateFormat sdf;
    Date template_date;

    public IngredientsLogicTest() {

        try  {
            sdf = new SimpleDateFormat("MM/dd/yyyy");
            template_date = sdf.parse("12/31/2001");
        } catch(Exception e) {
            Log.e("template date fail", "Template Date Failure");
        }

    }


    /**
     * Tests Constructor with incorrect amount
     * Input: amount=-1
     * expected: IllegalArgumentException
     */
    @Test
    public void testConstructor_000() {
        assertThrows(IllegalArgumentException.class, () -> {
            Ingredient ing = new Ingredient(
                    "testConstructor_000",
                    template_date, "pantry",
                    -1,
                    "670g",
                    "Food");
        });
    }


    /**
     * Tests Constructor with amount = 0
     * Input: amount = 0
     * Output: None
     */
    @Test
    public void testConstructor_001() {
        try {
            Ingredient ing = new Ingredient("testConstructor_001",
                    template_date,
                    "pantry",
                    0,
                    "670g",
                    "Food");
        } catch (Exception e){
            fail("No exception should have been thrown.");
        }

    }

    @Test
    public void testConstructor_002() {

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


    /**
     * Tests getDescription
     * Input: Instantiate Ingredient class with description="Descr"
     * Output: "Descr"
     */
    @Test
    public void testGetDescription_001() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Ingredient ing;
        try {
            ing = new Ingredient("Descr",
                    sdf.parse("12/31/2001"),
                    "Pantry",
                    10,
                    "750g",
                    "Food");
        } catch (Exception e) {
            Log.e("testGetDescription:001", "date parse failure");
            assertEquals(1, 0);
            return;
        }
        assertEquals(ing.getDescription(), "Descr");
    }
}
