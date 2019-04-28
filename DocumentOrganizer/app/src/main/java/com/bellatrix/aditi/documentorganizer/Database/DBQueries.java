package com.bellatrix.aditi.documentorganizer.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Aditi on 19-02-2019.
 */

public class DBQueries {

    public static Cursor searchImage(Context c, String key) {

        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        String query0 = "SELECT "+ Contract.Documents.COLUMN_ID + " FROM " + Contract.Documents.TABLE_NAME +
                " WHERE " + Contract.Documents.COLUMN_DATE + " LIKE '%" + key + "%'";
//                " OR " + Contract.Documents.COLUMN_TITLE + " LIKE '%" + key + "%'" +
//                " OR " + Contract.Documents.COLUMN_CATEGORY + " LIKE '%" + key + "%'";

        String query1 = "SELECT "+ Contract.BNR.COLUMN_ID + " FROM " + Contract.BNR.TABLE_NAME +
                " WHERE " + Contract.BNR.COLUMN_PURCHASE_DATE + " LIKE '%" + key + "%'" +
                " OR " + Contract.BNR.COLUMN_RECEIPT_TYPE + " LIKE '%" + key + "%'" +
                " OR " + Contract.BNR.COLUMN_PRODUCT_NAME + " LIKE '%" + key + "%'" +
                " OR " + Contract.BNR.COLUMN_TOTAL + " LIKE '%" + key + "%'" +
                " OR " + Contract.BNR.COLUMN_ENTERPRISE + " LIKE '%" + key + "%'" +
                " OR " + Contract.BNR.COLUMN_CUSTOM_TAGS + " LIKE '%" + key + "%'";
        String query2 = "SELECT "+ Contract.Medical.COLUMN_ID + " FROM " + Contract.Medical.TABLE_NAME +
                " WHERE " + Contract.Medical.COLUMN_ISSUED_DATE + " LIKE '%" + key + "%'" +
                " OR " + Contract.Medical.COLUMN_TYPE + " LIKE '%" + key + "%'" +
                " OR " + Contract.Medical.COLUMN_PATIENT + " LIKE '%" + key + "%'" +
                " OR " + Contract.Medical.COLUMN_INSTITUTION + " LIKE '%" + key + "%'" +
                " OR " + Contract.Medical.COLUMN_CUSTOM_TAGS + " LIKE '%" + key + "%'";
        String query3 = "SELECT "+ Contract.GID.COLUMN_ID + " FROM " + Contract.GID.TABLE_NAME +
                " WHERE " + Contract.GID.COLUMN_TYPE + " LIKE '%" + key + "%'" +
                " OR " + Contract.GID.COLUMN_HOLDER_NAME + " LIKE '%" + key + "%'" +
                " OR " + Contract.GID.COLUMN_CUSTOM_TAGS + " LIKE '%" + key + "%'";
        String query4 = "SELECT "+ Contract.Certificates.COLUMN_ID + " FROM " + Contract.Certificates.TABLE_NAME +
                " WHERE " + Contract.Certificates.COLUMN_TYPE1 + " LIKE '%" + key + "%'" +
                " OR " + Contract.Certificates.COLUMN_TYPE2 + " LIKE '%" + key + "%'" +
                " OR " + Contract.Certificates.COLUMN_HOLDER_NAME + " LIKE '%" + key + "%'" +
                " OR " + Contract.Certificates.COLUMN_INSTITUTION + " LIKE '%" + key + "%'" +
                " OR " + Contract.Certificates.COLUMN_ACHIEVEMENT + " LIKE '%" + key + "%'" +
                " OR " + Contract.Certificates.COLUMN_CUSTOM_TAGS + " LIKE '%" + key + "%'";
        String query5 = "SELECT "+ Contract.Handwritten.COLUMN_ID + " FROM " + Contract.Handwritten.TABLE_NAME +
                " WHERE " + Contract.Handwritten.COLUMN_CUSTOM_TAGS + " LIKE '%" + key + "%'";

        String query = "SELECT * FROM " + Contract.Documents.TABLE_NAME +
                " WHERE " + Contract.Documents.COLUMN_ID +" IN (" + query0 + ")" +
                " OR " + Contract.Documents.COLUMN_ID +" IN (" + query1 + ")" +
                " OR " + Contract.Documents.COLUMN_ID +" IN (" + query2 + ")" +
                " OR " + Contract.Documents.COLUMN_ID +" IN (" + query3 + ")" +
                " OR " + Contract.Documents.COLUMN_ID +" IN (" + query4 + ")" +
                " OR " + Contract.Documents.COLUMN_ID +" IN (" + query5 + ")";

        Cursor folderCursor = getFolders(c);
        int i = 5;
        while(folderCursor.moveToPosition(i)) {
            String queryi = "SELECT ID FROM " +
                    folderCursor.getString(folderCursor.getColumnIndex(Contract.Folders.COLUMN_FOLDER_NAME)) +
                    " WHERE CustomTags LIKE '%" + key + "%'";
            query += " OR " + Contract.Documents.COLUMN_ID +" IN (" + queryi + ")";
            i++;
        }
        folderCursor.close();

        return sdb.rawQuery(query, null);
    }

    public static Cursor searchImageByFolder(Context c, String key, String folderName) {

        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        String query0 = "SELECT "+ Contract.Documents.COLUMN_ID + " FROM " + Contract.Documents.TABLE_NAME +
                " WHERE " + Contract.Documents.COLUMN_DATE + " LIKE '%" + key + "%'";
//                " OR " + Contract.Documents.COLUMN_DATE + " LIKE '%" + key + "%'";

        String query="";
        if(folderName.equals(Contract.BNR.TABLE_NAME))
            query = "SELECT "+ Contract.BNR.COLUMN_ID + " FROM " + Contract.BNR.TABLE_NAME +
                    " WHERE " + Contract.BNR.COLUMN_PURCHASE_DATE + " LIKE '%" + key + "%'" +
                    " OR " + Contract.BNR.COLUMN_RECEIPT_TYPE + " LIKE '%" + key + "%'" +
                    " OR " + Contract.BNR.COLUMN_PRODUCT_NAME + " LIKE '%" + key + "%'" +
                    " OR " + Contract.BNR.COLUMN_TOTAL + " LIKE '%" + key + "%'" +
                    " OR " + Contract.BNR.COLUMN_ENTERPRISE + " LIKE '%" + key + "%'" +
                    " OR " + Contract.BNR.COLUMN_CUSTOM_TAGS + " LIKE '%" + key + "%'";
        else if(folderName.equals(Contract.Medical.TABLE_NAME))
            query = "SELECT "+ Contract.Medical.COLUMN_ID + " FROM " + Contract.Medical.TABLE_NAME +
                    " WHERE " + Contract.Medical.COLUMN_ISSUED_DATE + " LIKE '%" + key + "%'" +
                    " OR " + Contract.Medical.COLUMN_TYPE + " LIKE '%" + key + "%'" +
                    " OR " + Contract.Medical.COLUMN_PATIENT + " LIKE '%" + key + "%'" +
                    " OR " + Contract.Medical.COLUMN_INSTITUTION + " LIKE '%" + key + "%'" +
                    " OR " + Contract.Medical.COLUMN_CUSTOM_TAGS + " LIKE '%" + key + "%'";
        else if(folderName.equals(Contract.GID.TABLE_NAME))
            query = "SELECT "+ Contract.GID.COLUMN_ID + " FROM " + Contract.GID.TABLE_NAME +
                    " WHERE " + Contract.GID.COLUMN_TYPE + " LIKE '%" + key + "%'" +
                    " OR " + Contract.GID.COLUMN_HOLDER_NAME + " LIKE '%" + key + "%'" +
                    " OR " + Contract.GID.COLUMN_CUSTOM_TAGS + " LIKE '%" + key + "%'";
        else if(folderName.equals(Contract.Certificates.TABLE_NAME))
            query = "SELECT "+ Contract.Certificates.COLUMN_ID + " FROM " + Contract.Certificates.TABLE_NAME +
                    " WHERE " + Contract.Certificates.COLUMN_TYPE1 + " LIKE '%" + key + "%'" +
                    " OR " + Contract.Certificates.COLUMN_TYPE2 + " LIKE '%" + key + "%'" +
                    " OR " + Contract.Certificates.COLUMN_HOLDER_NAME + " LIKE '%" + key + "%'" +
                    " OR " + Contract.Certificates.COLUMN_INSTITUTION + " LIKE '%" + key + "%'" +
                    " OR " + Contract.Certificates.COLUMN_ACHIEVEMENT + " LIKE '%" + key + "%'" +
                    " OR " + Contract.Certificates.COLUMN_CUSTOM_TAGS + " LIKE '%" + key + "%'";
        else
            query = "SELECT ID FROM " +
                    folderName +
                    " WHERE CustomTags LIKE '%" + key + "%'";

        String query1 = "SELECT * FROM " + Contract.Documents.TABLE_NAME +
                " WHERE " + Contract.Documents.COLUMN_CATEGORY + " = '" + folderName + "'" +
                " AND (" + Contract.Documents.COLUMN_ID +" IN (" + query0 + ")" +
                " OR " + Contract.Documents.COLUMN_ID +" IN (" + query + "))";

        return sdb.rawQuery(query1, null);
    }

    public static long insertDocument(Context c,long id,byte[] img, String title, String folderName) {

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
                                 String productName, String total, String enterprise,String cTags) {
        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contract.BNR.COLUMN_ID, id);

        if(!date.equals("")) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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

        if(!cTags.equals(""))
            cv.put(Contract.BNR.COLUMN_CUSTOM_TAGS, cTags);

        long retVal= sdb.insert(Contract.BNR.TABLE_NAME,null,cv);
        sdb.close();
        return retVal;
    }

    public static long insertMedical(Context c, long id, String date, String type, String patientName, String institution,String cTags) {
        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(Contract.Medical.COLUMN_ID, id);

        if(!date.equals("")) {
//            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            cv.put(Contract.Medical.COLUMN_ISSUED_DATE, date);
        }

        if(!type.equals(""))
            cv.put(Contract.Medical.COLUMN_TYPE, type);
        if(!patientName.equals(""))
            cv.put(Contract.Medical.COLUMN_PATIENT, patientName);
        if(!institution.equals(""))
            cv.put(Contract.Medical.COLUMN_INSTITUTION, institution);

        if(!cTags.equals(""))
            cv.put(Contract.BNR.COLUMN_CUSTOM_TAGS, cTags);
        long retVal= sdb.insert(Contract.Medical.TABLE_NAME,null,cv);

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

        long retVal= sdb.insert(Contract.GID.TABLE_NAME,null,cv);
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

        long retVal= sdb.insert(Contract.Certificates.TABLE_NAME,null,cv);
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

        long retVal= sdb.insert(folderName,null,cv);

        sdb.close();
        return retVal;
    }

    public static long insertFolder(Context c, String name, String color) {
        DBHelper dbHelper = new DBHelper(c);
        SQLiteDatabase sdb = dbHelper.getWritableDatabase();

        // insertion in the names of folder table
        ContentValues cv = new ContentValues();
        cv.put(Contract.Folders.COLUMN_FOLDER_NAME, name);
        cv.put(Contract.Folders.COLUMN_FOLDER_COLOR,color);
        long retVal= sdb.insert(Contract.Folders.TABLE_NAME,null,cv);

        // creation of a table of the foldername
        final String SQL_CREATE_CUSTOM_TABLE = "CREATE TABLE IF NOT EXISTS " + "\'"+ name +"\'"+
                " (ID INTEGER PRIMARY KEY," +
                " CustomTags TEXT" +"); ";
        sdb.execSQL(SQL_CREATE_CUSTOM_TABLE);

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
                Contract.Documents.COLUMN_ID + " DESC",
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
        long id = cursor.getLong(cursor.getColumnIndex(Contract.Documents._ID));
        cursor.close();
        return id;
    }
}
