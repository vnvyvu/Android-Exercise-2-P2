package com.vyvu.th2b2.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.vyvu.th2b2.model.QuyenGop;

import java.util.ArrayList;

public class QuyenGopDAO extends SQLiteOpenHelper {

    public QuyenGopDAO(@Nullable Context context, @Nullable String name) {
        super(context, name, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "CREATE TABLE If NOT EXISTS product (" +
                        "  id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        "  name VARCHAR(20)," +
                        "  city INTEGER," +
                        "  price DOUBLE," +
                        "  date VARCHAR(30)" +
                        ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long add(QuyenGop p){
        ContentValues cv=new ContentValues();
        cv.put("name", p.getName());
        cv.put("city", p.getCity());
        cv.put("price", p.getPrice());
        cv.put("date", p.getDate());
        return getWritableDatabase().insert("product", null, cv);
    }

    public long update(QuyenGop p){
        ContentValues cv=new ContentValues();
        cv.put("name", p.getName());
        cv.put("city", p.getCity());
        cv.put("price", p.getPrice());
        cv.put("date", p.getDate());
        return getWritableDatabase().update("product", cv, "id=?",
                new String[]{String.valueOf(p.getId())});
    }

    public long delete(QuyenGop p){
        return getWritableDatabase().delete("product", "id=?",
                new String[]{String.valueOf(p.getId())});
    }

    public ArrayList<QuyenGop> getAll(){
        ArrayList<QuyenGop> p=new ArrayList<>();
        Cursor c=getReadableDatabase().rawQuery("SELECT * FROM product", null);
        if(c.moveToFirst()){
            do{
                p.add(new QuyenGop(
                        c.getInt(c.getColumnIndex("id")),
                        c.getString(c.getColumnIndex("name")),
                        c.getInt(c.getColumnIndex("city")),
                        c.getDouble(c.getColumnIndex("price")),
                        c.getString(c.getColumnIndex("date"))
                ));
            }while(c.moveToNext());
        }
        return p;
    }

}