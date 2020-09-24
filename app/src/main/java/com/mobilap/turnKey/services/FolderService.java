package com.mobilap.turnKey.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mobilap.turnKey.models.FolderModel;

import java.util.ArrayList;
import java.util.List;

public class FolderService extends Database{
    private String folder_name = "folder_name";
    private String folder_id = "folder_id";
    private SQLiteDatabase database;
    private String table_name = "Folder";


    public FolderService(Context pContext) {
        super(pContext);
        Database d = new Database(pContext);
        d.open();
        database = d.getDatabase();
    }

    public long insertFolder(String folder){

        ContentValues values = new ContentValues();
        values.put(SqlLite.FOLDER_NAME, folder);
        return database.insert(table_name, null, values);
    }
    public List<FolderModel> getFolders(){
        List<FolderModel> folderModels = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from " + table_name + " ORDER BY folder_name ASC;", null);
        FolderModel folderModel;
        while(cursor.moveToNext()){

            folderModel = new FolderModel(cursor.getString(1));
            folderModel.setId(cursor.getInt(0));
            folderModels.add(folderModel);
        }
        cursor.close();
        return folderModels;
    }

    public boolean DeleteFolder(String name){

        return database.delete(table_name, this.folder_name + "='" + name+"'", null) > 0;
    }

}
