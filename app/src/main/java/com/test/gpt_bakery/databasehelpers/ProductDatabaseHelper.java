package com.test.gpt_bakery.databasehelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.test.gpt_bakery.models.Cookie;

import java.util.ArrayList;

public class ProductDatabaseHelper extends SQLiteOpenHelper {
    public static final String ID = "ID";
    public static final String NAME = "NAME";
    public static final String PRICE = "PRICE";
    public static final String DESCRIPTION = "DESCRIPTION";
    public static final String IMAGE = "IMAGE";
    public static final String COOKIE = "COOKIE";

    public ProductDatabaseHelper(Context context) {
        super(context, "bakery.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + COOKIE + "(" + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + NAME + " VARCHAR(150), " + PRICE + " REAL, "+ DESCRIPTION + " VARCHAR(150), " + IMAGE + " BLOB)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ COOKIE);
    }

    public boolean add(Cookie cookie)    {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(NAME, cookie.getName());
        cv.put(PRICE, cookie.getPrice());
        cv.put(DESCRIPTION, cookie.getDescription());
        cv.put(IMAGE, cookie.getImage());
        long insert = db.insert(COOKIE, null, cv);
        if (insert != -1)   {
            return true;
        }else {
            return false;
        }
    }

    public ArrayList<Cookie> getList()    {
        SQLiteDatabase db = getReadableDatabase();
        ArrayList<Cookie> returnList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+COOKIE, null);
        if (cursor.moveToFirst())   {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                double price = cursor.getDouble(2);
                String description = cursor.getString(3);
                byte[] image = cursor.getBlob(4);
                returnList.add(new Cookie(id, name, price, description, image));
            }while (cursor.moveToNext());
        }
        return returnList;
    }

//    public boolean delete(int id) {
//        SQLiteDatabase db = getWritableDatabase();
//        int res = db.delete(DOVAT, ID+" = ?", new String[]{String.valueOf(id)});
//        if (res == 1)   {
//            return true;
//        }else   {
//            return false;
//        }
//    }

//    public Item getItemById(int id) {
//        SQLiteDatabase db = getReadableDatabase();
//        Cursor cursor = db.rawQuery("SELECT * FROM " + DOVAT + " WHERE ID = " + id, null);
//        if (cursor.moveToFirst())   {
//            return new Item(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getBlob(3));
//        }
//        return null;
//    }

//    public boolean edit(Item edited) {
//        SQLiteDatabase db = getWritableDatabase();
//        ContentValues cv = new ContentValues();
//        cv.put(ID, edited.getId());
//        cv.put(TEN, edited.getName());
//        cv.put(MOTA, edited.getDescription());
//        cv.put(HINHANH, edited.getImg());
//        int update = db.update(DOVAT, cv, ID + " = ?", new String[]{String.valueOf(edited.getId())});
//        if (update == 1)    {
//            return true;
//        }else   {
//            return false;
//        }
//    }
}
