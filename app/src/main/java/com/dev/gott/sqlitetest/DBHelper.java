package com.dev.gott.sqlitetest;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Admin on 07-06-2016.
 */
public class DBHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "UOCT.sqlite";
    private static final int DB_SCHEME_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_SCHEME_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(DataBaseManager.CREATE_PUNTOS);
        db.execSQL(DataBaseManager.CREATE_RUTAS);
        db.execSQL(DataBaseManager.CREATE_PUNTOSRUTAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
