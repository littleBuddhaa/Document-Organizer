package com.bellatrix.aditi.documentorganizer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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

    public static long insertFolder(Context c, String name, String color) {
        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contract.Folders.COLUMN_FOLDER_NAME, name);
        cv.put(Contract.Folders.COLUMN_FOLDER_COLOR,color);
        long retVal= sdb.insert(Contract.Folders.TABLE_NAME,null,cv);

        sdb.close();
        return retVal;
    }

    public static Cursor getFolders(Context c) {
        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        return sdb.query(Contract.Folders.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null,
                null);
    }

    public static Cursor getImageByFolder(Context c, String folderName) {
        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        String[] where={folderName};
        Cursor cursor = sdb.query(Contract.Documents.TABLE_NAME,
                null,
                Contract.Documents.COLUMN_CATEGORY+"=?",
                where,
                null,
                null,
                null,
                null);
        return cursor;
    }
}
