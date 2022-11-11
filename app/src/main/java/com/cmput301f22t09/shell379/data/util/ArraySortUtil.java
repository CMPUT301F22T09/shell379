package com.cmput301f22t09.shell379.data.util;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class ArraySortUtil {

    public interface StringPropGetter{
        public String getString(Object object);
    }

//    /**
//     * sorts an array list by the specified string property on the object using the property's compareTo
//     * method. Uses a quicksort internally.
//     * The arraylist 'objects' list will be mutated and returned.
//     * @param objects list of objects to sort. Will be mutated!
//     * @param propertyName property to sort on
//     */
//    public static <T> ArrayList<T> sortByStringProp(ArrayList<T> objects, String propertyName, StringPropGetter stringPropGetter){
//        // https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html
//        // Dynamic prop referencing from https://stackoverflow.com/questions/13128194/java-how-can-i-dynamically-reference-an-objects-property
//        // Generics documentation https://docs.oracle.com/javase/tutorial/extra/generics/methods.html
//
//        // check type and if property exists
//        if(objects.size() == 0){
//            return objects;
//        }
//        Field field;
//        try{
//
//            field = objects.get(0).getClass().getDeclaredField(propertyName);
//        }catch (Exception e){
//            throw new IllegalArgumentException("object does not contain property: " +propertyName);
//        }
//        try{
//            if(!(field.get(objects.get(0)) instanceof String)){
//                throw new IllegalArgumentException("property to sort on is not of type string");
//            }
//        }catch (Exception e){
//            throw new IllegalArgumentException("object does not contain property: " +propertyName);
//        }
//
//        quickSortOnString(objects,propertyName,0,objects.size() - 1);
//
//        return objects;
//    }
//
//    private static <T> void quickSortOnString(ArrayList<T> objects, String propertyName, int low, int high) {
//        // Quick sort algorithm from https://www.geeksforgeeks.org/quick-sort/
//        if (low < high) {
//
//            /* pi is partitioning index, arr[pi] is now at right place */
//            int pi = partitionOnString(objects, propertyName, low, high);
//
//            quickSortOnString(objects,propertyName, low, pi-1);  // Before pi
//
//            quickSortOnString(objects, propertyName,pi + 1, high); // After pi
//
//        }
//    }
//
//    private static <T> int partitionOnString (ArrayList<T> objects, String propertyName, int low, int high)
//    {
//        // Quick sort algorithm from https://www.geeksforgeeks.org/quick-sort/
//        // pivot (Element to be placed at right position)
//        T pivot = objects.get(high);
//
//        int i = (low - 1);  // Index of smaller element and indicates the
//        // right position of pivot found so far
//
//        for (int j = low; j <= high - 1; j++){
//
//            // If current element is smaller than the pivot
//            if (isSmallerString(objects.get(j), objects.get(i),propertyName)){
//                i++;    // increment index of smaller element
//                T tempI = objects.get(i);
//                objects.set(i,objects.get(j));
//                objects.set(j,tempI);
//            }
//        }
//        T tempI = objects.get(i + 1);
//        objects.set(i,objects.get(high));
//        objects.set(high,tempI);
//
//        return (i + 1);
//    }
//
//    private static <T> boolean isSmallerString(T left,T right,String propertyName){
//        Field field;
//        String leftValue;
//        String rightValue;
//        try{
//            // https://stackoverflow.com/questions/13128194/java-how-can-i-dynamically-reference-an-objects-property
//            field = left.getClass().getDeclaredField(propertyName);
//            leftValue = (String)field.get(left);
//            rightValue = (String)field.get(right);
//        }catch (Exception e){
//            throw new IllegalArgumentException("T does not contain property: " +propertyName);
//        }
//
//        if (left == null && right == null ||
//                left != null && right == null){
//            return false;
//        }else if(left == null && right != null ||
//                leftValue.compareTo(rightValue) < 0){
//            return true;
//        }else{
//            return  false;
//        }
//    }


        /**
         * sorts an array list by the specified string property on the object using the property's compareTo
         * method. Uses a quicksort internally.
         * The arraylist 'objects' list will be mutated and returned.
         * @param objects list of objects to sort. Will be mutated!
//         * @param propertyName property to sort on
         */
        public static <T> ArrayList<T> sortByStringProp(ArrayList<T> objects, StringPropGetter stringPropGetter){
            // https://docs.oracle.com/javase/8/docs/api/java/lang/reflect/Field.html
            // Dynamic prop referencing from https://stackoverflow.com/questions/13128194/java-how-can-i-dynamically-reference-an-objects-property
            // Generics documentation https://docs.oracle.com/javase/tutorial/extra/generics/methods.html

            // check type and if property exists
            if(objects.size() == 0){
                return objects;
            }
//            Field field;
//            try{
//
//                field = objects.get(0).getClass().getDeclaredField(propertyName);
//            }catch (Exception e){
//                throw new IllegalArgumentException("object does not contain property: " +propertyName);
//            }
//            try{
//                if(!(field.get(objects.get(0)) instanceof String)){
//                    throw new IllegalArgumentException("property to sort on is not of type string");
//                }
//            }catch (Exception e){
//                throw new IllegalArgumentException("object does not contain property: " +propertyName);
//            }

            quickSortOnString(objects,stringPropGetter,0,objects.size() - 1);

            return objects;
        }

        private static <T> void quickSortOnString(ArrayList<T> objects, StringPropGetter stringPropGetter, int low, int high) {
            // Quick sort algorithm from https://www.geeksforgeeks.org/quick-sort/
            if (low < high) {

                /* pi is partitioning index, arr[pi] is now at right place */
                int pi = partitionOnString(objects, stringPropGetter, low, high);

                quickSortOnString(objects,stringPropGetter, low, pi-1);  // Before pi

                quickSortOnString(objects, stringPropGetter,pi + 1, high); // After pi

            }
        }

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
