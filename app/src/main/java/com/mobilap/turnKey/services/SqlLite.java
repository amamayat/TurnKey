package com.mobilap.turnKey.services;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import androidx.annotation.Nullable;

public class SqlLite extends SQLiteOpenHelper{
    public static final String FOLDER_NAME = "folder_name";
    public static final String FOLDER_ID = "folder_id";

    public static final String PASSWORD_ID = "password_id";
    public static final String TITLE = "title";
    public static final String USER = "user";
    public static final String PASSWORD = "password";
    public static final String URL = "url";
    public static final String INFORMATIONS = "informations";
    public static final String IMAGE = "image";
    public static final String FOLDER = "folder";
    public static final String DATECREATION = "dateCreation";
    public static final String DATEMODIFICATION = "dateModification";


    public SqlLite(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CreateTableLabel());
        sqLiteDatabase.execSQL(CreateTablePassword());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {

        sqLiteDatabase.execSQL(DropTableLabel());
        sqLiteDatabase.execSQL(DropTablePassword());
        onCreate(sqLiteDatabase);
    }

    public String CreateTableLabel(){
        String tableLabel = "Create table Folder ("+ FOLDER_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "+ FOLDER_NAME +" TEXT NOT NULL);";
        return tableLabel;
    }

    public String CreateTablePassword(){
        String tablePassword = "Create table Password (password_id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT NOT NULL, user TEXT, password TEXT, url TEXT, " +
                "informations TEXT, image TEXT, folder TEXT, dateCreation TEXT, dateModification TEXT)";
        return tablePassword;
    }


    public String DropTableLabel(){
        String dropLabel = "Drop table if exists Folder";
        return dropLabel;
    }
    public String DropTablePassword(){
        String drop = "Drop table if exists Password";
        return drop;
    }
}
