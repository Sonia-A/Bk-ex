package com.example.bk_apk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.sql.Blob;

public class SQLiteDBHelper extends SQLiteOpenHelper {

private static  final String DATABASE_NAME = "USERS";
private static final String USER_TABLE_NAME = "user_table";
private static  final String COL_1 = "ID";
private static final String COL_2="USERNAME";
private static final String COL_3="PASSWORD";



    public SQLiteDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME , null, 1);
    }



    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+USER_TABLE_NAME +
                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, USERNAME TEXT, PASSWORD TEXT)");
//        db.execSQL("CREATE TABLE IF NOT EXISTS "+PRODUCT_TABLE_NAME +
//                "(ID INTEGER PRIMARY KEY AUTOINCREMENT, PRODUCT_NAME TEXT, PRODUCT_PRICE TEXT, PRODUCT_DESCRIPTION TEXT, PRODUCT_CATEGORY TEXT,PRODUCT_MFGDATE TEXT,PRODUCT_IMAGE BLOB)");
    }






    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+USER_TABLE_NAME);
        onCreate(db);


    }

    public  boolean registerUser(String username, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, username);
        values.put(COL_3, password);

        long result = db.insert(USER_TABLE_NAME,null, values);
        if (result == -1)
                return false;

        else
            return true;


    }
    public boolean checkUser(String username, String password){
        SQLiteDatabase db = this. getWritableDatabase();
        String[] columns = {COL_1};
        String selection = COL_2 +"=?" + " and "+ COL_3 + "=?";
        String[] selectionargs = {username, password};
        Cursor cursor = db.query(USER_TABLE_NAME, columns, selection, selectionargs, null, null, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();
        if(count >0)
            return true;
        else
            return false;

    }
}
