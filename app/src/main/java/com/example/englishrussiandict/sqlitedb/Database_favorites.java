package com.example.englishrussiandict.sqlitedb;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.englishrussiandict.entity.MyObjDictionary;

import java.util.ArrayList;
import java.util.List;

public class Database_favorites extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Favorites_manager";
    private static final String TABLE_NAME = "favoritelist";
    public static final String _ID = "_id";
    public static final String WORD = "word";
    public static final String DEFINITION = "definition";
    public static final String STATUS = "status";
    public static final String USER_CREATED = "user_created";
    private static final int VERSION = 1;
    private Context context;

    private String SQLQuery = "CREATE TABLE " + TABLE_NAME + " (" +
            _ID + " TEXT, " +
            WORD + " TEXT primary key, "
            + DEFINITION + " TEXT, "
            + STATUS + " TEXT, "
            + USER_CREATED + " TEXT)";

    public Database_favorites(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQLQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void addfavorite(MyObjDictionary dictionary) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(_ID, dictionary.get_id());
        values.put(WORD, dictionary.getWord());
        values.put(DEFINITION, dictionary.getDefinition());
        values.put(STATUS, dictionary.getStatus());
        values.put(USER_CREATED, dictionary.getUser_created());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    public List<MyObjDictionary> getalldictionaryList() {
        List<MyObjDictionary> dictionaryList = new ArrayList<>();
        String selectquery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(selectquery, null);
        if (cursor.moveToFirst()) {
            do {

                MyObjDictionary dictionary = new MyObjDictionary();
                dictionary.set_id(cursor.getString(0));
                dictionary.setWord(cursor.getString(1));
                dictionary.setDefinition(cursor.getString(2));
                dictionary.setStatus(cursor.getString(3));
                dictionary.setUser_created(cursor.getString(4));
                dictionaryList.add(dictionary);
            } while (cursor.moveToNext());
        }
        db.close();
        return dictionaryList;
    }

    public void deletedata(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + _ID + " = '" + id + "'" ;
        db.execSQL(query);
    }
}
