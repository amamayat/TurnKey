package com.mobilap.turnKey.services;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.mobilap.turnKey.models.PasswordModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PasswordService extends Database{

    private SQLiteDatabase database;
    private String table_name = "Password";

    public PasswordService(Context pContext) {
        super(pContext);
        Database d = new Database(pContext);
        d.open();
        database = d.getDatabase();
    }

    public long insertPassword(String title, String user, String password, String url, String informations, String image, String folder, String dateCreation, String dateModification){
        ContentValues values = new ContentValues();
        values.put(SqlLite.TITLE, title);
        values.put(SqlLite.PASSWORD, password);
        values.put(SqlLite.USER, user);
        values.put(SqlLite.URL, url);
        values.put(SqlLite.INFORMATIONS, informations);
        values.put(SqlLite.IMAGE, image);
        values.put(SqlLite.FOLDER, folder);
        values.put(SqlLite.DATECREATION, dateCreation);
        values.put(SqlLite.DATEMODIFICATION, dateModification);
        return database.insert(table_name, null, values);
    }

    public List<PasswordModel> getListPasswords(){
        List<PasswordModel>list = new ArrayList<>();
        Cursor cursor = database.rawQuery("Select * from "+ table_name + " ORDER BY title ASC;", null);
        PasswordModel passwordModel;

        while(cursor.moveToNext()){
            passwordModel = new PasswordModel(cursor.getString(1));
            passwordModel.setId(cursor.getInt(0));
            passwordModel.setUser(cursor.getString(2));
            passwordModel.setPassword(cursor.getString(3));
            passwordModel.setUrl(cursor.getString(4));
            passwordModel.setInformations(cursor.getString(5));
            passwordModel.setImage(cursor.getString(6));
            passwordModel.setFolder(cursor.getString(7));

            try{

                passwordModel.setDateCreation(new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(8)));
                if(cursor.getString(9) != null) {
                    passwordModel.setDateModification(new SimpleDateFormat("dd/MM/yyyy").parse(cursor.getString(9)));
                }
            }catch (Exception e){

                String erreur = e.toString();
            }
            list.add(passwordModel);
        }
        return list;
    }

    public long UpdatePassword(int id, PasswordModel psw){
        ContentValues values = new ContentValues();
        values.put(SqlLite.TITLE, psw.getTitle());
        values.put(SqlLite.PASSWORD, psw.getPassword());
        values.put(SqlLite.USER, psw.getUser());
        values.put(SqlLite.URL, psw.getUrl());
        values.put(SqlLite.INFORMATIONS, psw.getInformations());
        values.put(SqlLite.IMAGE, psw.getImage());
        values.put(SqlLite.FOLDER, psw.getFolder());
        values.put(SqlLite.DATECREATION,new SimpleDateFormat("dd/MM/yyyy").format(psw.getDateCreation()));
        values.put(SqlLite.DATEMODIFICATION, new SimpleDateFormat("dd/MM/yyyy").format(psw.getDateModification()));
        return database.update(table_name, values, "password_id = " + id, null);
    }
    public boolean DeletePassword(int id){

        return database.delete(table_name,  "password_id ='" + id+"'", null) > 0;
    }
}
