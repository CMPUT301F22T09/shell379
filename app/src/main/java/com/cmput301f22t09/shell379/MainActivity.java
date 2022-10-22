package com.cmput301f22t09.shell379;

import androidx.appcompat.app.AppCompatActivity;
//import androidx.navigation.Navigation;

import android.os.Bundle;
import android.util.Log;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.MealPlan;
import com.cmput301f22t09.shell379.data.Recipe;
import com.cmput301f22t09.shell379.data.ShoppingCart;
import com.cmput301f22t09.shell379.data.util.DatabaseManager;
import com.cmput301f22t09.shell379.data.vm.Environment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Environment env = Environment.of(this);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        Date d;
        try {
            d = sdf.parse("12/31/3001");
        } catch (Exception e) {
            Log.e("MainActivity", "Parse Failure");
            return;
        }
        Ingredient ing = new Ingredient("description", d, "location", 1, "100kg", "Foodstuffs");
        Recipe rec = new Recipe("title", 20L, 10, "category", "Lorem Ipsum Dolor Sit Amet");
        MealPlan mp = new MealPlan();
        ShoppingCart sc = new ShoppingCart();

        env.getIngredients().add(ing);
        env.getRecipes().add(rec);


        env.getIngredients().commit();
        env.getRecipes().commit();

        DatabaseManager dbm = new DatabaseManager(this);

        dbm.pull();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {

        }
        dbm.push(env);




    }

}