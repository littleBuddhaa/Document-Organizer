package com.bellatrix.aditi.documentorganizer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Aditi on 19-02-2019.
 */

public class DBQueries {
    public static long insertDocument(Context c, byte[] img) {

        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        // TODO: Remove hard coded column values
//        cv.put(Contract.Documents.COLUMN_TITLE, "HEllo");
        cv.put(Contract.Documents.COLUMN_IMAGE,img);
        cv.put(Contract.Documents.COLUMN_CATEGORY,"Bills");
        long retVal= sdb.insert(Contract.Documents.TABLE_NAME,null,cv);
        sdb.close();
        return retVal;
    }
}
