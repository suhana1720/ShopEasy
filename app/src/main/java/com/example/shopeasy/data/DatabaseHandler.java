package com.example.shopeasy.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.shopeasy.model.Content;
import com.example.shopeasy.util.util;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    private final Context context;

    public DatabaseHandler(@Nullable Context context) {
        super(context, util.DATABASE_NAME, null, util.DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + util.TABLE_NAME + "("
                + util.KEY_ID + " INTEGER PRIMARY KEY ,"
                + util.KEY_ITEM_NAME+ " INTEGER,"
                + util.KEY_BRAND + " TEXT,"
                + util.KEY_QUANTITY + " INTEGER,"
                + util.KEY_PRICE + " INTEGER,"
                + util.KEY_DATE_NAME + " LONG);";

        db.execSQL(CREATE_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + util.TABLE_NAME);

        onCreate(db);
    }

    // CRUD operations
    public void addItem(Content content) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(util.KEY_ITEM_NAME, content.getItem());
        values.put(util.KEY_BRAND, content.getItemBrand());
        values.put(util.KEY_QUANTITY, content.getItemQuantity());
        values.put(util.KEY_PRICE, content.getItemPrice());
        values.put(util.KEY_DATE_NAME, java.lang.System.currentTimeMillis());//timestamp of the system

        //Inset the row
         db.insert(util.TABLE_NAME, null, values);

        Log.d("DBHandler", "added Item: ");
        db.close();
    }

    //Get an Item
    public Content getItem(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(util.TABLE_NAME,
                new String[]{util.KEY_ID,
                        util.KEY_ITEM_NAME,
                        util.KEY_BRAND,
                        util.KEY_QUANTITY,
                        util.KEY_PRICE,
                        util.KEY_DATE_NAME},
                util.KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Content item = new Content();
        if (cursor != null) {
            item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(util.KEY_ID))));
            item.setItem(cursor.getString(cursor.getColumnIndex(util.KEY_ITEM_NAME)));
            item.setItemBrand(cursor.getString(cursor.getColumnIndex(util.KEY_BRAND)));
            item.setItemQuantity(cursor.getString(cursor.getColumnIndex(util.KEY_QUANTITY)));
            item.setItemPrice(cursor.getInt(cursor.getColumnIndex(util.KEY_PRICE)));

            //convert Timestamp to something readable
            DateFormat dateFormat = DateFormat.getDateInstance();
            String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(util.KEY_DATE_NAME)))
                    .getTime()); // Feb 23, 2020

            item.setDateItemAdded(formattedDate);


        }

        return item;
    }

    //Get all Items
    public List<Content> getAllItems() {


        List<Content> itemList = new ArrayList<>();
        String selectQuery= " SELECT  * FROM  " + util.TABLE_NAME + " ORDER BY  " + java.lang.System.currentTimeMillis() + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Content item = new Content();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(util.KEY_ID))));
                item.setItem(cursor.getString(cursor.getColumnIndex(util.KEY_ITEM_NAME)));
                item.setItemBrand(cursor.getString(cursor.getColumnIndex(util.KEY_BRAND)));
                item.setItemQuantity(cursor.getString(cursor.getColumnIndex(util.KEY_QUANTITY)));
                item.setItemPrice(cursor.getInt(cursor.getColumnIndex(util.KEY_PRICE)));

                //convert Timestamp to something readable
                DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(cursor.getLong(cursor.getColumnIndex(util.KEY_DATE_NAME)))
                        .getTime()); // Feb 23, 2020
                item.setDateItemAdded(formattedDate);

                //Add to arraylist
                itemList.add(item);
            } while (cursor.moveToNext());
        }
        db.close();
        return itemList;

    }

    //Todo: Add updateItem
    public int updateItem(Content item) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(util.KEY_ITEM_NAME, item.getItem());
        values.put(util.KEY_BRAND, item.getItemBrand());
        values.put(util.KEY_QUANTITY, item.getItemQuantity());
        values.put(util.KEY_PRICE, item.getItemPrice());
        values.put(util.KEY_DATE_NAME, java.lang.System.currentTimeMillis());//timestamp of the system

        //update row
        return db.update(util.TABLE_NAME, values,
                util.KEY_ID + "=?",
                new String[]{String.valueOf(item.getId())});

    }

    //Todo: Add Delete Item
    public void deleteItem(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(util.TABLE_NAME,
                util.KEY_ID + "=?",
                new String[]{String.valueOf(id)});

        //close
        db.close();

    }

    //Todo: getItemCount
    public int getItemsCount() {
        String countQuery = "SELECT * FROM " + util.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(countQuery, null);

        int count =cursor.getCount();
        cursor.close();
        return count;


    }

}

