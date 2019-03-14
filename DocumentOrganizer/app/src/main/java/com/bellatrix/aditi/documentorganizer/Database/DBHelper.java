package com.bellatrix.aditi.documentorganizer.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.bellatrix.aditi.documentorganizer.Constants;
import com.bellatrix.aditi.documentorganizer.Database.Contract.*;
import com.bellatrix.aditi.documentorganizer.Folder;

/**
 * Created by Aditi on 16-02-2019.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DocumentsDatabase.db";
    private static final int DATABASE_VERSION = 1;

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

        db.execSQL(SQL_CREATE_DOCUMENT_TABLE);
        db.execSQL(SQL_CREATE_FOLDER_TABLE);

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
        onCreate(db);
    }
}
