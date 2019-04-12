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
    public static long insertDocument(Context c,long id,byte[] img, String title, String folderName, String uri) {

        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();


        cv.put(Contract.Documents.COLUMN_TITLE, title);
        cv.put(Contract.Documents.COLUMN_IMAGE,img);
        cv.put(Contract.Documents.COLUMN_CATEGORY,folderName);
        cv.put(Contract.Documents.COLUMN_URI, uri);
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
        long retVal= sdb.insert("\'"+Contract.BNR.TABLE_NAME+"\'",null,cv);
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
        long retVal= sdb.insert("\'"+Contract.Medical.TABLE_NAME+"\'",null,cv);
        sdb.close();
        return retVal;
    }

    public static long insertGID(Context c, long id, String type, String holderName) {
        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contract.GID.COLUMN_ID, id);

        if(!type.equals(""))
            cv.put(Contract.GID.COLUMN_TYPE, type);
        if(!holderName.equals(""))
            cv.put(Contract.GID.COLUMN_HOLDER_NAME, holderName);
        long retVal= sdb.insert("\'"+Contract.GID.TABLE_NAME+"\'",null,cv);
        sdb.close();
        return retVal;
    }

    public static long insertCertificate(Context c, long id, String type1, String type2,
                                         String holderName, String institution, String achievement) {
        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contract.Certificates.COLUMN_ID, id);

        if(!type1.equals(""))
            cv.put(Contract.Certificates.COLUMN_TYPE1, type1);
        if(!type2.equals(""))
            cv.put(Contract.Certificates.COLUMN_TYPE2, type2);
        if(!holderName.equals(""))
            cv.put(Contract.Certificates.COLUMN_HOLDER_NAME, holderName);
        if(!institution.equals(""))
            cv.put(Contract.Certificates.COLUMN_INSTITUTION, institution);
        if(!achievement.equals(""))
            cv.put(Contract.Certificates.COLUMN_ACHIEVEMENT, achievement);
        long retVal= sdb.insert("\'"+Contract.Certificates.TABLE_NAME+"\'",null,cv);
        sdb.close();
        return retVal;
    }

    public static long insertIntoFolder(Context c, String folderName, long id, String tags) {
        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put("ID", id);

        if(!tags.equals(""))
            cv.put("CustomTags", tags);

        long retVal= sdb.insert("\'"+folderName+"\'",null,cv);
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

    public static Cursor getImageById(Context c, String tableName,long id ) {
        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();

        Cursor resultSet = sdb.rawQuery("Select * from "+"\'"+tableName+"\'"+" where ID = "+id+";",null);
        resultSet.moveToFirst();
        return resultSet;
    }
    public static long getLastId(Context c) {
        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        Cursor cursor = sdb.rawQuery("select * from Documents;",null);
        cursor.moveToLast();
        long id = cursor.getLong(cursor.getColumnIndex("ID"));
        cursor.close();
        return id;
    }
}
