package com.cmput301f22t09.shell379.data.vm.collections;

import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
/**
 * Set of entity categories to choose from.
 * Used for ingredient categories, recipe categories and location categories.
 */
public class CategorySet extends Commitable implements Serializable {
    private HashSet<String> set;

    public CategorySet() {
        this.set = new HashSet<String>();
    }

    public HashSet<String> getCategories() {
        return set;
    }

    public void setCategories(HashSet<String> set) {
        this.set = set;
    }

    public void addCategory(String category) {
        set.add(category);
    }

    public void addCategories(ArrayList<String> categories) {
        set.addAll(categories);
    }
}
