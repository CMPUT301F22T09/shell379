package com.cmput301f22t09.shell379.data.vm.infrastructure;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Commitable extends ViewModel {
    private final MutableLiveData<Boolean> changed = new MutableLiveData<>();

    public Commitable() {
        changed.setValue(false);
    }

    protected void commit() {
        changed.setValue(true);
    }

    public MutableLiveData<Boolean> isCommitNeeded() {
        return changed;
    }
}
