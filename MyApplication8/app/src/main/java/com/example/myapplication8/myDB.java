package com.example.myapplication8;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 丁丁 on 2017/12/1 0001.
 */

public class myDB extends SQLiteOpenHelper {
    private static final String TABLE_NAME = "app8Table";
    private static final int DB_VERSION = 1;
    public myDB(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_TABLE = "CREATE TABLE if not exists " + TABLE_NAME + " (_id INTEGER PRIMARY KEY, name TEXT, birthday TEXT, gift TEXT, phone TEXT)";
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int l) {

    }

}