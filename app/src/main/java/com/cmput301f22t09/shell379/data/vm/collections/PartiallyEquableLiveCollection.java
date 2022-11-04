package com.cmput301f22t09.shell379.data.vm.collections;

import java.util.ArrayList;

// TODO: cite https://stackoverflow.com/questions/897935/when-do-java-generics-require-extends-t-instead-of-t-and-is-there-any-down
// for the <T extends PartiallyEquable> notation
public class PartiallyEquableLiveCollection<T extends PartiallyEquable> extends LiveCollection<T> {
    public PartiallyEquableLiveCollection() {
        super();
    }

    // TODO: sorting algorithm
    public LiveCollection<T> getFilteredCollection() {
        LiveCollection<T> filteredCollection = new LiveCollection<T>();
        ArrayList<T> seenBefore = new ArrayList<T>();
        int seenBeforeLength;
        boolean addItem = true;

        for (Object item : this.getList()) {
            T tItem = (T) item;
            seenBeforeLength = seenBefore.size();
            for (int i = 0; i < seenBeforeLength; i++) {
                if (seenBefore.get(i).partialEquals(tItem)) {
                    addItem = false;
                    break;
                }
            }
            if (addItem) {
                filteredCollection.add(tItem);
                addItem = false;
            }
        }

        return filteredCollection;
    }
}
