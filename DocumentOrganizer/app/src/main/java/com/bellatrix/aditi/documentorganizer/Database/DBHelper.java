package com.bellatrix.aditi.documentorganizer.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bellatrix.aditi.documentorganizer.Utilities.Constants;
import com.bellatrix.aditi.documentorganizer.Database.Contract.*;
import com.bellatrix.aditi.documentorganizer.Utilities.Folder;

/**
 * Created by Aditi on 16-02-2019.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DocumentsDatabase.db";
    private static final int DATABASE_VERSION = 2;

    public DBHelper(Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_DOCUMENT_TABLE="CREATE TABLE " + Documents.TABLE_NAME + " (" +
                Documents.COLUMN_TITLE + " TEXT NOT NULL," +
                Documents.COLUMN_DATE + " DATETIME DEFAULT CURRENT_TIMESTAMP," +
                Documents.COLUMN_IMAGE + " BLOB NOT NULL," +
                Documents.COLUMN_CATEGORY + " TEXT" +"); ";

        final String SQL_CREATE_FOLDER_TABLE="CREATE TABLE " + Folders.TABLE_NAME + " (" +
                Folders.COLUMN_FOLDER_NAME + " TEXT NOT NULL," +
                Folders.COLUMN_FOLDER_COLOR + " TEXT NOT NULL" +"); ";

        final String SQL_CREATE_BNR_TABLE="CREATE TABLE " + BNR.TABLE_NAME + " (" +
                BNR.COLUMN_ID + " INTEGER PRIMARY KEY," +
                BNR.COLUMN_PURCHASE_DATE + " DATE," +
                BNR.COLUMN_RECEIPT_TYPE + " TEXT," +
                BNR.COLUMN_PRODUCT_NAME + " TEXT," +
                BNR.COLUMN_TOTAL + " TEXT," +
                BNR.COLUMN_ENTERPRISE + " TEXT," +
                BNR.COLUMN_CUSTOM_TAGS + " TEXT" +"); ";

        final String SQL_CREATE_MEDICAL_TABLE="CREATE TABLE " + Medical.TABLE_NAME + " (" +
                Medical.COLUMN_ID + " INTEGER PRIMARY KEY," +
                Medical.COLUMN_ISSUED_DATE + " DATE," +
                Medical.COLUMN_TYPE + " TEXT," +
                Medical.COLUMN_PATIENT + " TEXT," +
                Medical.COLUMN_INSTITUTION + " TEXT," +
                Medical.COLUMN_CUSTOM_TAGS + " TEXT" +"); ";

        final String SQL_CREATE_GID_TABLE="CREATE TABLE " + GID.TABLE_NAME + " (" +
                GID.COLUMN_ID + " INTEGER PRIMARY KEY," +
                GID.COLUMN_TYPE + " TEXT," +
                GID.COLUMN_HOLDER_NAME + " TEXT," +
                GID.COLUMN_CUSTOM_TAGS + " TEXT" +"); ";

        final String SQL_CREATE_HANDWRITTEN_TABLE="CREATE TABLE " + Handwritten.TABLE_NAME + " (" +
                Handwritten.COLUMN_ID + " INTEGER PRIMARY KEY," +
                Handwritten.COLUMN_CUSTOM_TAGS + " TEXT" +"); ";

        final String SQL_CREATE_CERTIFICATES_TABLE="CREATE TABLE " + Certificates.TABLE_NAME + " (" +
                Certificates.COLUMN_ID + " INTEGER PRIMARY KEY," +
                Certificates.COLUMN_TYPE + " TEXT," +
                Certificates.COLUMN_HOLDER_NAME + " TEXT," +
                Certificates.COLUMN_INSTITUTION + " TEXT," +
                Certificates.COLUMN_ACHIEVEMENT + " TEXT," +
                Certificates.COLUMN_CUSTOM_TAGS + " TEXT" +"); ";

        db.execSQL(SQL_CREATE_DOCUMENT_TABLE);
        db.execSQL(SQL_CREATE_FOLDER_TABLE);
        db.execSQL(SQL_CREATE_BNR_TABLE);
        db.execSQL(SQL_CREATE_MEDICAL_TABLE);
        db.execSQL(SQL_CREATE_GID_TABLE);
        db.execSQL(SQL_CREATE_HANDWRITTEN_TABLE);
        db.execSQL(SQL_CREATE_CERTIFICATES_TABLE);

        // Insert values for predefined database
        for(Folder folder: Constants.FOLDERS) {
            final String SQL_INSERT_FOLDER_TABLE = "INSERT INTO " + Folders.TABLE_NAME + " VALUES ("+
                    "\"" + folder.folderName + "\"," +
                    "\"" + folder.color + "\")";
            db.execSQL(SQL_INSERT_FOLDER_TABLE);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.Documents.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.Folders.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.BNR.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.Medical.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.GID.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.Handwritten.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.Certificates.TABLE_NAME);
        onCreate(db);
    }
}
