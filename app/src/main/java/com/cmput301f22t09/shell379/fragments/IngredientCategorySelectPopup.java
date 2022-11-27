package com.cmput301f22t09.shell379.fragments;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.cmput301f22t09.shell379.R;
import com.cmput301f22t09.shell379.adapters.fragmentadapters.CategoriesSelectRecViewAdapter;
import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.collections.CategorySet;

import java.util.HashSet;

/**
 *  Category popup for ingredients
 */
public class IngredientCategorySelectPopup extends CategorySelectPopup {
    protected CategorySet getCollection(Environment env){
        return env.getIngredientCategories();
    };

    public IngredientCategorySelectPopup(SelectListener selectListener, String title){
        super(selectListener, title);
    }
}