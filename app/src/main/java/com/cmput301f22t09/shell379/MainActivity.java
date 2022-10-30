package com.cmput301f22t09.shell379;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.navigation.Navigation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
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
        Ingredient ing1 = new Ingredient("description1", d, "location", 1, "100kg", "Foodstuffs");
//        Recipe rec = new Recipe("title", 20L, 10, "category", "Lorem Ipsum Dolor Sit Amet");
        Recipe rec = new Recipe("title", 20L, 10, "category", "Lorem Ipsum Dolor Sit Amet", BitmapFactory.decodeResource(getResources(), R.drawable.arrow_back_48px));
        MealPlan mp = new MealPlan();
        ShoppingCart sc = new ShoppingCart();

        env.getIngredients().add(ing);
        env.getIngredients().add(ing1);
        env.getRecipes().add(rec);

        env.getRecipeCategories().addCategory("Italian Food");
        env.getRecipeCategories().addCategory("Mexican Food");

        env.getIngredientCategories().addCategory("Snacks");
        env.getIngredientCategories().addCategory("Meats");


        env.getIngredients().commit();

        DatabaseManager dbm = new DatabaseManager(this);

//        dbm.pull();
        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (Exception e) {

        }
//        dbm.push(env);




    }

}