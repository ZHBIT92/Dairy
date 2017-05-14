package com.example.administrator.work;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ImpacN-Ho on 2017/5/12.
 */

public class NoteDataBaseHelper extends SQLiteOpenHelper{

    public static final String CreateNote="create table note("
            + "id integer primary key autoincrement,"
            + "content text, "
            +"tree text,"
            +"num int,"
            +"date text)";


    public NoteDataBaseHelper(Context context) {
        super(context, "note", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CreateNote);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
