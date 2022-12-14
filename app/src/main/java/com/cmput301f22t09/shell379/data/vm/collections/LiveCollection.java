package com.cmput301f22t09.shell379.data.vm.collections;

import androidx.lifecycle.MutableLiveData;

import com.cmput301f22t09.shell379.data.vm.infrastructure.Commitable;

import java.util.ArrayList;

/**
 * Collection of T entity objects stored in a MutableLiveData object.
 */
public class LiveCollection<T> extends Commitable {
    protected final MutableLiveData<ArrayList<T>> lst = new MutableLiveData<>();

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

    public void setAtIdxOrAdd(Integer idx, T obj) {
        if (idx==null || idx<0 || idx >= lst.getValue().size()) {
            lst.getValue().add(obj);
        }
        else {
            lst.getValue().set(idx, obj);
        }
    }
}
