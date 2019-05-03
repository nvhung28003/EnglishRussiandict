package com.example.englishrussiandict.sqlitedb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.englishrussiandict.entity.MyObjDictionary;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class MyDatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dictionary.sqlite";
    public static final String TABLE_NAME = "words";
    public static final int VERSION = 1;
    public static final String _ID = "_id";
    public static final String WORD = "word";
    public static final String DEFINITION = "definition";
    public static final String STATUS = "status";
    public static final String USER_CREATED = "user_created";
    private Context context;
    private String duongdandatabase = "";

    public MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        duongdandatabase = context.getFilesDir().getParent() + "/databases/" + DATABASE_NAME;

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase opendatabase() {
        return SQLiteDatabase.openDatabase(duongdandatabase, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void copyDatabase() {
        try {
            InputStream is = context.getAssets().open("databases/" + DATABASE_NAME);
            OutputStream os = new FileOutputStream(duongdandatabase);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
            os.flush();
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createDataBase() {
        boolean kt = KiemtraDB();
        if (kt) {
            Log.d("ket noi", "may da co database");
        } else {
            Log.d("ket noi", "may chua co database");
            this.getWritableDatabase();
            copyDatabase();
        }

    }

    public boolean KiemtraDB() {
        SQLiteDatabase kiemtraDB = null;
        try {
            kiemtraDB = SQLiteDatabase.openDatabase(duongdandatabase, null, SQLiteDatabase.OPEN_READONLY);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (kiemtraDB != null) {
            kiemtraDB.close();
        }
        return kiemtraDB != null ? true : false;


    }

    public List<MyObjDictionary> getalldictionary() {
        List<MyObjDictionary> dictionaryList = new ArrayList<>();
        String selecquery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selecquery, null);

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
}
