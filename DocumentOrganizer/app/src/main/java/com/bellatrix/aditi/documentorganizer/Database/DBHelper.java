package com.bellatrix.aditi.documentorganizer.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.bellatrix.aditi.documentorganizer.Database.Contract.*;

/**
 * Created by Aditi on 16-02-2019.
 */

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Documents.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context c){
        super(c, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_USER_TABLE="CREATE TABLE " + Documents.TABLE_NAME + " (" +
                Documents.COLUMN_TITLE + " TEXT NOT NULL," +
                Documents.COLUMN_DATE + " DATE NOT NULL," +
                Documents.COLUMN_IMAGE + " BLOB NOT NULL," +
                Documents.COLUMN_CATEGORY + " TEXT" +"); ";
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ Contract.Documents.TABLE_NAME);
        onCreate(db);
    }
}
