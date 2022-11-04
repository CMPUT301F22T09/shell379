package com.cmput301f22t09.shell379.data;

import java.util.ArrayList;

public enum Unit {
    KG,
    G,
    L,
    ML,
    TEASPOON;

    public static String getUnitName(Unit u) {
        switch (u){
            case KG:
                return "kilograms";
            case G:
                return "grams";
            case L:
                return "liters";
            case ML:
                return "milliliters";
            case TEASPOON:
                return "tea spoons";
        }
        return "invalid unit";
    }

    public static Unit getFromString(String string){
        switch (string){
            case "Kilograms":
                return KG;
            case "grams":
                return G;
            case "liters":
                return L;
            case "milliliters":
                return ML;
            case "tea spoons":
                return TEASPOON;
        }
        return null;
    }
    public static ArrayList<String> getAllValuesAsStrings()
    {
        ArrayList<String> strings = new ArrayList<String>();
        for (Unit l : Unit.values()) {
            strings.add(Unit.getUnitName(l));
        }
        return strings;
    }
}
