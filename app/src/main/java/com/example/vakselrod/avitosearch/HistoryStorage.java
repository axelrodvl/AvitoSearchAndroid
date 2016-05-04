package com.example.vakselrod.avitosearch;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class HistoryStorage extends SQLiteOpenHelper implements BaseColumns {
    private static final String DATABASE_NAME = "advertisement.db";
    private static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "advertisements";
    public static final String URL = "url";
    public static final String HEADER = "header";
    public static final String DATE = "date";
    public static final String PRICE = "price";
    public static final String OWNER_NAME = "owner_name";
    public static final String PHONE = "phone";
    public static final String LOCATION = "location";
    public static final String DESCRIPTION = "description";

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + TABLE_NAME + " (" + HistoryStorage._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + URL + " VARCHAR(255) UNIQUE ON CONFLICT IGNORE,"
            + HEADER + " VARCHAR(255),"
            + DATE + " VARCHAR(255),"
            + PRICE + " INTEGER,"
            + OWNER_NAME + " VARCHAR(255),"
            + PHONE + " VARCHAR(255),"
            + LOCATION + " VARCHAR(255),"
            + DESCRIPTION + " VARCHAR(255));";

    //CREATE TABLE a (i INT, j INT, UNIQUE(i, j) ON CONFLICT REPLACE);

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS "
            + TABLE_NAME;

    public HistoryStorage(Context context) {
        // TODO Auto-generated constructor stub
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        Log.w("LOG_TAG", "Обновление базы данных с версии " + oldVersion
                + " до версии " + newVersion + ", которое удалит все старые данные");
        // Удаляем предыдущую таблицу при апгрейде
        db.execSQL(SQL_DELETE_ENTRIES);
        // Создаём новый экземпляр таблицы
        onCreate(db);
    }

    public static void saveList(SQLiteDatabase db, List<Advertisement> adList) {
        ContentValues cv = new ContentValues();

        for (Advertisement ad : adList) {
            cv.put(HistoryStorage.URL, ad.url);
            cv.put(HistoryStorage.HEADER, ad.header);
            cv.put(HistoryStorage.PRICE, ad.price);
            cv.put(HistoryStorage.DATE, ad.date);
            db.insert(HistoryStorage.TABLE_NAME, null, cv);
        }
    }

    public static List<Advertisement> getList(SQLiteDatabase db) {
        List<Advertisement> adList = new ArrayList<Advertisement>();

        Cursor cursor = db.query(HistoryStorage.TABLE_NAME, new String[] {
                        HistoryStorage._ID, HistoryStorage.HEADER, HistoryStorage.PRICE, HistoryStorage.DATE, HistoryStorage.URL },
                null, // The columns for the WHERE clause
                null, // The values for the WHERE clause
                null, // don't group the rows
                null, // don't filter by row groups
                null // The sort order
        );

        while (cursor.moveToNext()) {
            Advertisement ad = new Advertisement();

            // GET COLUMN INDICES + VALUES OF THOSE COLUMNS
            //int id = cursor.getInt(cursor.getColumnIndex(HistoryStorage._ID));
            ad.url = cursor.getString(cursor.getColumnIndex(HistoryStorage.URL));
            ad.header = cursor.getString(cursor.getColumnIndex(HistoryStorage.HEADER));
            ad.date = cursor.getString(cursor.getColumnIndex(HistoryStorage.DATE));
            ad.price = cursor.getInt(cursor.getColumnIndex(HistoryStorage.PRICE));
            adList.add(ad);
        }
        cursor.close();

        return adList;
    }
}