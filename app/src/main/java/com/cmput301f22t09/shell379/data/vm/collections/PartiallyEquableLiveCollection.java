package com.cmput301f22t09.shell379.data.vm.collections;

import com.cmput301f22t09.shell379.data.Ingredient;

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
        for (Object item : lst.getValue()) {
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
                seenBefore.add(tItem);
                addItem = true;
            }
        }

        return filteredCollection;
    }

        /**
     * returns a full only filtered list of elements of type T
     * @return list of full elements of type T
     */
    public ArrayList<T> getFullList() {
        ArrayList<T> filteredList = new ArrayList<T>();
        ArrayList<T> list = lst.getValue();
        for (int i = 0; i < list.size();i++){
            if(list.get(i).isFull()){
                filteredList.add(list.get(i));
            }
        }
        return filteredList;
    }
    /**
     * returns a all elements of type T
     * @return list of all elements of type T
     */
    public ArrayList<T> getAll() {
        return lst.getValue();
    }
}
