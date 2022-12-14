package com.cmput301f22t09.shell379.fragments;

import androidx.fragment.app.Fragment;

import com.cmput301f22t09.shell379.data.vm.Environment;
import com.cmput301f22t09.shell379.data.vm.collections.CategorySet;

/**
 * Category select for locations within the ingredient save fragment.
 */
public class LocationCategorySelectPopup extends CategorySelectPopup {
    protected CategorySet getCollection(Environment env){
        return env.getLocationCategories();
    };

    public LocationCategorySelectPopup(SelectListener selectListener,String title){
        super(selectListener,title);
    }
}