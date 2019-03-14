package com.bellatrix.aditi.documentorganizer.Database;

import android.provider.BaseColumns;

/**
 * Created by Aditi on 16-02-2019.
 */

public class Contract {
    private Contract(){}

    public static class Documents implements BaseColumns {
        public static final String TABLE_NAME = "Documents";
        public static final String COLUMN_TITLE = "Title";
        public static final String COLUMN_DATE = "Time";
        public static final String COLUMN_IMAGE = "Image";
        public static final String COLUMN_CATEGORY = "Category";
    }

    public static class Folders implements BaseColumns {
        public static final String TABLE_NAME = "Folders";
        public static final String COLUMN_FOLDER_NAME = "Name";
        public static final String COLUMN_FOLDER_COLOR = "Color";
    }
}
