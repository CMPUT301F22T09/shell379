package com.cmput301f22t09.shell379.data.vm.collections;

import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;

import java.util.ArrayList;
import java.util.HashSet;

public class CategorySet extends Commitable {
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
