package com.bellatrix.aditi.documentorganizer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.text.SimpleDateFormat;

/**
 * Created by Aditi on 19-02-2019.
 */

public class DBQueries {
    public static long insertDocument(Context c, byte[] img, String title, String folderName) {

        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contract.Documents.COLUMN_TITLE, title);
        cv.put(Contract.Documents.COLUMN_IMAGE,img);
        cv.put(Contract.Documents.COLUMN_CATEGORY,folderName);
        long retVal= sdb.insert(Contract.Documents.TABLE_NAME,null,cv);
        sdb.close();
        return retVal;
    }

    public static long insertBNR(Context c, long id, String date, String receiptType,
                                 String productName, String total, String enterprise) {
        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contract.BNR.COLUMN_ID, id);

        if(!date.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            cv.put(Contract.BNR.COLUMN_PURCHASE_DATE, date);
        }

        if(!receiptType.equals(""))
            cv.put(Contract.BNR.COLUMN_RECEIPT_TYPE, receiptType);
        if(!productName.equals(""))
            cv.put(Contract.BNR.COLUMN_PRODUCT_NAME, productName);
        if(!total.equals(""))
            cv.put(Contract.BNR.COLUMN_TOTAL, total);
        if(!enterprise.equals(""))
            cv.put(Contract.BNR.COLUMN_ENTERPRISE, enterprise);
        long retVal= sdb.insert(Contract.BNR.TABLE_NAME,null,cv);
        sdb.close();
        return retVal;
    }

    public static long insertMedical(Context c, long id, String date, String type, String patientName, String institution) {
        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contract.Medical.COLUMN_ID, id);

        if(!date.equals("")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            cv.put(Contract.Medical.COLUMN_ISSUED_DATE, date);
        }

        if(!type.equals(""))
            cv.put(Contract.Medical.COLUMN_TYPE, type);
        if(!patientName.equals(""))
            cv.put(Contract.Medical.COLUMN_PATIENT, patientName);
        if(!institution.equals(""))
            cv.put(Contract.Medical.COLUMN_INSTITUTION, institution);
        long retVal= sdb.insert(Contract.Medical.TABLE_NAME,null,cv);
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

    public static int getTotalImageByFolder(Context c, String folderName) {
        Cursor cursor = getImageByFolder(c, folderName);
        int total = cursor.getCount();
        cursor.close();
        return total;
    }
}
