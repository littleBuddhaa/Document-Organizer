package com.bellatrix.aditi.documentorganizer;

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
}
