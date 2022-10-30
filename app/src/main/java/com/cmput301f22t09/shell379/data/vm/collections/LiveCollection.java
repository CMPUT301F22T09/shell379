package com.cmput301f22t09.shell379.data.vm.collections;

import androidx.lifecycle.MutableLiveData;

import com.cmput301f22t09.shell379.data.Ingredient;
import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;

import java.util.ArrayList;
import java.util.HashSet;

public class LiveCollection<T> extends Commitable {
    private final MutableLiveData<ArrayList<T>> lst = new MutableLiveData<>();

    public LiveCollection() {
        lst.setValue(new ArrayList<T>());
    }

    public MutableLiveData<ArrayList<T>> getListLive() {
        return lst;
    }

    public ArrayList<T> getList() {
        return lst.getValue();
    }

    public void add(T obj) {
        lst.getValue().add(obj);
        setList(lst.getValue());
    }

    public void add(ArrayList<T> lst) {
        this.lst.getValue().addAll(lst);
        setList(this.lst.getValue());
    }

    public void removeAtIdx(int i) {
        this.lst.getValue().remove(i);
        setList(lst.getValue());
    }

    public void setList(ArrayList<T> lst) {
        this.lst.setValue(lst);
    }
}