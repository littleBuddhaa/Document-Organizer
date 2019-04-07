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
        public static final String COLUMN_URI = "Uri";


    }

    public static class Folders implements BaseColumns {
        public static final String TABLE_NAME = "Folders";
        public static final String COLUMN_FOLDER_NAME = "Name";
        public static final String COLUMN_FOLDER_COLOR = "Color";

    }

    public static class BNR implements BaseColumns {
        public static final String TABLE_NAME = "Bills & Receipts";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_PURCHASE_DATE = "Purchase Date";
        public static final String COLUMN_RECEIPT_TYPE = "Receipt Type";
        public static final String COLUMN_PRODUCT_NAME = "Product Name";
        public static final String COLUMN_TOTAL = "Total";
        public static final String COLUMN_ENTERPRISE = "Enterprise";
        public static final String COLUMN_CUSTOM_TAGS = "Custom Tags";
    }

    public static class Medical implements BaseColumns {
        public static final String TABLE_NAME = "Medical records";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_ISSUED_DATE = "Issued Date";
        public static final String COLUMN_TYPE = "Type";
        public static final String COLUMN_PATIENT = "Patient Name";
        public static final String COLUMN_INSTITUTION = "Institution";
        public static final String COLUMN_CUSTOM_TAGS = "Custom Tags";
    }

    public static class GID implements BaseColumns {
        public static final String TABLE_NAME = "Government issued documents";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_TYPE = "Type";
        public static final String COLUMN_HOLDER_NAME = "Holder Name";
        public static final String COLUMN_CUSTOM_TAGS = "Custom Tags";
    }

    public static class Handwritten implements BaseColumns {
        public static final String TABLE_NAME = "Handwritten";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_CUSTOM_TAGS = "Custom Tags";
    }

    public static class Certificates implements BaseColumns {
        public static final String TABLE_NAME = "Certificates & Marksheets";
        public static final String COLUMN_ID = "ID";
        public static final String COLUMN_TYPE1 = "Type1";
        public static final String COLUMN_TYPE2 = "Type2";
        public static final String COLUMN_HOLDER_NAME = "Holder Name";
        public static final String COLUMN_INSTITUTION = "Institution";
        public static final String COLUMN_ACHIEVEMENT = "Achievement";
        public static final String COLUMN_CUSTOM_TAGS = "Custom Tags";
    }
}
