package com.projects.jeancarlos.siguemeproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alumno on 8/22/15.
 */
public class DataBaseHelper extends SQLiteOpenHelper{

    private final static String DB_NAME = "unencrypted-position-database";
    private final static int DB_VERSION = 1;

    public DataBaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseManager.CREATE_SCRIPT_POSITION);
        db.execSQL(DataBaseManager.CREATE_SCRIPT_ROUTE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DataBaseManager.DROP_TABLE_POSITION);
        db.execSQL(DataBaseManager.DROP_TABLE_ROUTE);
        onCreate(db);
    }
}
