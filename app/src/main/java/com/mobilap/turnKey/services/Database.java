package com.mobilap.turnKey.services;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class Database {
    protected final static int VERSION = 1;
    protected final static String NOM = "database.db";
    protected SQLiteDatabase database = null;
    protected SqlLite mHandler = null;

    public Database(Context pContext) {

        this.mHandler = new SqlLite(pContext, NOM, null, VERSION);
    }

    public SQLiteDatabase open(){

        database = mHandler.getWritableDatabase();
        return database;
    }
    public void close(){
        database.close();
    }
    public SQLiteDatabase getDatabase(){
        return database;
    }
}
