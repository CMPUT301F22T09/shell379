package com.cmput301f22t09.shell379.data.util;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * sorts an array list by the specified property on the object using the property's compareTo
 * method. Uses a quicksort internally.
 * The arraylist 'objects' list will be mutated and returned.
 * @param objects list of objects to sort. Will be mutated!
 * @param propertyName property to sort on
 */
public class ArraySortUtil {
//    public static ArrayList<Object> sortBy(ArrayList<Object> objects, String propertyName){
//        if(objects.size() == 0){
//            throw new IllegalArgumentException("Array list cannot be empty for sortBy");
//        }
//        try{
//            // https://stackoverflow.com/questions/13128194/java-how-can-i-dynamically-reference-an-objects-property
//            Field field;
//            field = objects.get(0).getClass().getDeclaredField(propertyName);
//        }catch (Exception e){
//            throw new IllegalArgumentException("Object does not contain property: " +propertyName);
//        }
//        quickSort(objects,propertyName,0,objects.size() - 1);
//
//        return objects;
//    }
//
//    private static ArrayList<Object> quickSort(ArrayList<Object> objects, String propertyName, int low, int high) {
//        // Quick sort algorithm from https://www.geeksforgeeks.org/quick-sort/
//        if (low < high) {
//
//            /* pi is partitioning index, arr[pi] is now at right place */
//            int pi = partition(objects, propertyName, low, high);
//
//            quickSort(objects,propertyName, low, pi-1);  // Before pi
//
//            quickSort(objects, propertyName,pi + 1, high); // After pi
//
//        }
//
//    }
//
//    private static int partition (ArrayList<Object> objects, String propertyName, int low, int high)
//    {
//        // Quick sort algorithm from https://www.geeksforgeeks.org/quick-sort/
//        // pivot (Element to be placed at right position)
//        Object pivot = objects.get(high);
//
//        int i = (low - 1);  // Index of smaller element and indicates the
//        // right position of pivot found so far
//
//        for (int j = low; j <= high - 1; j++){
//
//            // If current element is smaller than the pivot
//            if (objects.get(j) < pivot){
//                i++;    // increment index of smaller element
//                swap arr[i] and arr[j]
//            }
//        }
//        swap arr[i + 1] and arr[high])
//        return (i + 1)
//    }


}
