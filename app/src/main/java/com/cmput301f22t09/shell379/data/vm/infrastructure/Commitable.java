package com.cmput301f22t09.shell379.data.vm.infrastructure;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * Allows for an attribute within the Environment object to be
 * flagged as ready to be uploaded to the Database using Observers.
 */
public class Commitable extends ViewModel {
    private final MutableLiveData<Boolean> changed = new MutableLiveData<>();

    public Commitable() {
        changed.setValue(false);
    }

    /**
     * Flags object for commit. Observers will commit even if 'changed' was already true.
     */
    public void commit() {
        changed.setValue(true);
    }

    public MutableLiveData<Boolean> isCommitNeeded() {
        return changed;
    }
}
