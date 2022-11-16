package com.cmput301f22t09.shell379.data.util;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 *  sorting utility functions
 */
public class ArraySortUtil {

    public interface StringPropGetter{
        public String getString(Object object);
    }

    /**
     * sorts an array list by the specified string property on the object using the string
     * property's compareTo method. Uses a quicksort internally.
     * The arraylist 'objects' list will be mutated and returned.
     * @param objects list of objects to sort. Will be mutated!
     * @param stringPropGetter Class that contains the getString method to get the string representation of
     *              the property to sort on.
     */
    public static <T> ArrayList<T> sortByStringProp(ArrayList<T> objects, StringPropGetter stringPropGetter){
        // Field reflection from https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html
        // Dynamic prop referencing from Trevor Dixon, oct 29 2012,
        // https://stackoverflow.com/questions/13128194/java-how-can-i-dynamically-reference-an-objects-property
        // Generics documentation https://docs.oracle.com/javase/tutorial/extra/generics/methods.html

        // check type and if property exists
        if(objects.size() == 0){
            return objects;
        }

        quickSortOnString(objects,stringPropGetter,0,objects.size() - 1);

        return objects;
    }

    /**
     * recursive quick sort on an object's string property.
     * @param objects objects to sort
     * @param stringPropGetter implements the way to get the string prop from the object
     * @param low index to start sorting
     * @param high index to end sorting
     * @param <T> type of objects to sort
     */
    private static <T> void quickSortOnString(ArrayList<T> objects, StringPropGetter stringPropGetter, int low, int high) {
        // Quick sort algorithm from https://www.geeksforgeeks.org/quick-sort/
        if (low < high) {

            /* pi is partitioning index, arr[pi] is now at right place */
            int pi = partitionOnString(objects, stringPropGetter, low, high);

            quickSortOnString(objects,stringPropGetter, low, pi-1);  // Before pi

            quickSortOnString(objects, stringPropGetter,pi + 1, high); // After pi

        }
    }

    /**
     *  part of the quicksort algorithm. partitions the array
     * @param objects objects to partition
     * @param stringPropGetter implements the way to get the string prop from the object
     * @param low index to start partition
     * @param high index to end partition
     * @param <T> type of objects to sort
     * @return partition index
     */
    private static <T> int partitionOnString (ArrayList<T> objects, StringPropGetter stringPropGetter, int low, int high)
    {
        // Quick sort algorithm from https://www.geeksforgeeks.org/quick-sort/
        // pivot (Element to be placed at right position)
        T pivot = objects.get(high);

        int i = (low - 1);  // Index of smaller element and indicates the
        // right position of pivot found so far

        for (int j = low; j <= high - 1; j++){

            // If current element is smaller than the pivot
            if (isSmallerString(objects.get(j), pivot, stringPropGetter)){
                i++;    // increment index of smaller element
                T tempI = objects.get(i);
                objects.set(i,objects.get(j));
                objects.set(j,tempI);
            }
        }
        T tempI = objects.get(i + 1);
        objects.set(i + 1,objects.get(high));
        objects.set(high,tempI);

        return (i + 1);
    }

    /**
     * part of the quicksort algorithm. compares the string fields of two objects.Nulls are ok.
     * @param left left object with a string param
     * @param right right object with a string param
     * @param stringPropGetter defines how to get the string param from the object
     * @param <T> type of object
     * @return true if left is smaller than right, false otherwise.
     * A null param is always considered smaller than a non null param.
     */
    private static <T> boolean isSmallerString(T left,T right,StringPropGetter stringPropGetter){
        String leftValue = stringPropGetter.getString(left);
        String rightValue = stringPropGetter.getString(right);

        if (left == null && right == null ||
                left != null && right == null){
            return false;
        }else if(left == null && right != null ||
                leftValue.compareTo(rightValue) < 0){
            return true;
        }else{
            return  false;
        }
    }
}
