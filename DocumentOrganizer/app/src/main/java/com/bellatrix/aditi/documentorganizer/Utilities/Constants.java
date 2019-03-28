package com.bellatrix.aditi.documentorganizer.Utilities;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Aditi on 13-03-2019.
 */

public class Constants {

    public static ArrayList<Folder> FOLDERS = new ArrayList<Folder>(
            Arrays.asList(new Folder("Bills & Receipts", "#4B236E"),
                    new Folder("Medical records","#EF971C"),
                    new Folder("Government issued documents","#A5E6BA"),
                    new Folder("Handwritten","#9AC6C5"),
                    new Folder("Certificates", "#A38DBA")));

    // TODO: Add colors for folder
    public static int colorIndex=0;
    public static final String[] COLORS = {"#F88B7B","#848373"};

    public static ArrayList<String> BNR_SUB_CATEGORIES_1 = new ArrayList<>(
            Arrays.asList("Electronic Goods",
                    "Household Items",
                    "Clothing",
                    "Medical",
                    "Electricity Bill",
                    "Gas Bill",
                    "Water Bill"));

    public static ArrayList<String> BNR_SUB_CATEGORIES_2 = new ArrayList<>(
            Arrays.asList("Television",
                    "Refrigerator",
                    "Mobile phone",
                    "Earphones & Headphones",
                    "Laptop & Desktop",
                    "Calculator",
                    "AC & cooler",
                    "Geyser",
                    "Washing Machine",
                    "Speakers",
                    "Heater",
                    "Furniture"));
}