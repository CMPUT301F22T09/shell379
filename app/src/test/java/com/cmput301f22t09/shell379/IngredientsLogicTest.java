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

    String template_desc;
    Date template_date;
    String template_loc;
    Integer template_amount;
    String template_unit;
    String template_cat;

    public IngredientsLogicTest() {

        template_desc = "Description";
        try  {
            sdf = new SimpleDateFormat("MM/dd/yyyy");
            template_date = sdf.parse("12/31/2031");
        } catch(Exception e) {
            Log.e("template date fail", "Template Date Failure");
        }

        template_loc = "Pantry";
        template_amount = 1;
        template_unit = "1g";
        template_cat = "Food";

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
                    template_desc,
                    template_date,
                    template_loc,
                    -1,
                    template_unit,
                    template_cat);
        }, "Did not throw IllegalArgumentException!");
    }


    /**
     * Tests Constructor with amount = 0
     * Input: amount = 0
     * Output: No Exception
     */
    @Test
    public void testConstructor_001() {
        try {
            Ingredient ing = new Ingredient(
                    template_desc,
                    template_date,
                    template_loc,
                    0,
                    template_unit,
                    template_cat);
        } catch (Exception e){
            fail("No exception should have been thrown.");
        }

    }


    /**
     * Tests Constructor with amount > 0
     * Input: amount = 2
     * Output: No Exception
     */
    @Test
    public void testConstructor_002() {
        try {
            Ingredient ing = new Ingredient(
                    template_desc,
                    template_date,
                    template_loc,
                    2,
                    template_unit,
                    template_cat);
        } catch (Exception e){
            fail("No exception should have been thrown.");
        }
    }

    /**
     * Tests Constructor with unit containing numeral values
     * that evaluates to a float greater than 0.
     *
     * Input: unit = "670g"
     * Output: No Exception
     */
    @Test
    public void testConstructor_005() {
        try {
            Ingredient ing = new Ingredient(template_desc,
                    template_date,
                    template_loc,
                    template_amount,
                    "670g",
                    template_cat);
        } catch (Exception e) {
            fail("Exception should not have been thrown!");
        }

    }


    /**
     * Tests Constructor with unit containing numeral values
     * that evaluates to a float greater than 0, as well as
     * no unit type. Having no unit type should evaluate to unit-less,
     * and should not throw exception.
     *
     * Input: unit = "1"
     * Output: No Exception
     */
    @Test
    public void testConstructor_006() {
        try {
            Ingredient ing = new Ingredient(template_desc,
                    template_date,
                    template_loc,
                    template_amount,
                    "1",
                    template_cat);
        } catch (Exception e) {
            fail("Exception should not have been thrown!");
        }

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

        ing = new Ingredient("Descr",
                template_date,
                template_loc,
                template_amount,
                template_unit,
                template_cat);

        assertEquals(ing.getDescription(), "Descr");
    }
}
