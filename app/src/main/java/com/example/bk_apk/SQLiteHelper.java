package com.example.bk_apk;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class SQLiteHelper extends SQLiteOpenHelper {
    public SQLiteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public  void queryData(String sql){SQLiteDatabase db = getWritableDatabase(); db.execSQL(sql);}
    public void recordItem(String PRODUCT_NAME, String PRODUCT_PRICE, String PRODUCT_DESCRIPTION, String PRODUCT_CATEGORY, String PRODUCT_MFGDATE, byte[] PRODUCT_IMAGE){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "INSERT INTO product_table VALUES(NULL, ?,?,?,?,?,?)";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindString(1, PRODUCT_NAME);
        statement.bindString(2, PRODUCT_PRICE);
        statement.bindString(3, PRODUCT_DESCRIPTION);
        statement.bindString(4, PRODUCT_CATEGORY);
        statement.bindString(5, PRODUCT_MFGDATE);
        statement.bindBlob(6,PRODUCT_IMAGE);

        statement.executeInsert();

    }
    public void updateData(String PRODUCT_NAME, String PRODUCT_PRICE, String PRODUCT_DESCRIPTION, String PRODUCT_CATEGORY, String PRODUCT_MFGDATE, byte[] PRODUCT_IMAGE, int ID)
    {
        SQLiteDatabase db = getWritableDatabase();
        String sql = "SELECT PRODUCT_NAME,PRODUCT_PRICE,PRODUCT_DESCRIPTION,PRODUCT_CATEGORY,PRODUCT_MFGDATE,PRODUCT_IMAGE WHERE ID=? ";

//        String sql = " UPDATE product_table SET "+" PRODUCT_NAME=?,PRODUCT_PRICE=?,PRODUCT_DESCRIPTION=?,PRODUCT_CATEGORY=?,PRODUCT_MFGDATE=?,PRODUCT_IMAGE=? WHERE ID=? ";
        SQLiteStatement statement = db.compileStatement(sql);
        statement.bindString(1, PRODUCT_NAME);
        statement.bindString(2, PRODUCT_PRICE);
        statement.bindString(3, PRODUCT_DESCRIPTION);
        statement.bindString(4, PRODUCT_CATEGORY);
        statement.bindString(5, PRODUCT_MFGDATE);
        statement.bindBlob(6,PRODUCT_IMAGE);
        statement.bindDouble(7,(double) ID);

        statement.execute();
        db.close();


    }
    public Cursor getData(String sql){
        SQLiteDatabase db = getReadableDatabase();
        return  db.rawQuery(sql, null);
    }

    public void deleteData(int id){
        SQLiteDatabase db = getWritableDatabase();
        String sql = "DELETE FROM PRODUCT_DB WHERE ID=?";

        SQLiteStatement statement = db.compileStatement(sql);
        statement.clearBindings();

        statement.bindDouble(1,(double) id);

        statement.execute();
        db.close();

    }


    @Override
    public void onCreate(android.database.sqlite.SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(android.database.sqlite.SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public class CursorFactory {
    }
}
