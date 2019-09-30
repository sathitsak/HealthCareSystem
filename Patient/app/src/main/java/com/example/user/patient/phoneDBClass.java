package com.example.user.patient;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 1/27/2016.
 */
public class phoneDBClass extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "phoneNumberDB";
    public  static final String TABLE_NAME ="phoneDatabase";
    public  static final String COL_TEL = "TEL";

    public static final String DATABASE_CREATE =
            "CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY, " + COL_TEL + " TEXT NOT NULL);";

    public phoneDBClass(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        // TODO Auto-generated constructor stub
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

}
