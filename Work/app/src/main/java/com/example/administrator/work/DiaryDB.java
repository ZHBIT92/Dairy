package com.example.administrator.work;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ImpacN-Ho on 2017/5/5.
 */

public class DiaryDB extends SQLiteOpenHelper{

    private final static String DATABASE_NAME = "DIARY.db";
    private final static int DATABASE_VERSION = 1;
    private final static String TABLE_NAME = "diary_table";
    public final static String DIARY_ID = "diary_id";
    public final static String DIARY_TITLE = "diary_title";
    public final static String DIARY_MAINTEXT = "diary_maintext";
    public final static String DIARY_DATE = "diary_date";

    public DiaryDB(Context context){
        super(context, DATABASE_NAME, null,DATABASE_VERSION);
    }
    //创建table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE " + TABLE_NAME + "(" + DIARY_ID
                + " INTEGER primary key autoincrement, " + DIARY_TITLE + " text, " + DIARY_MAINTEXT +" text, "
                + DIARY_DATE +" text);";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql="DROP TABLE IF EXISTS " + TABLE_NAME;
        db.execSQL(sql);
        onCreate(db);
    }

    public Cursor select(){
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.query(TABLE_NAME,null,null,null,null,null,null);
        return cursor;
    }

    //增加操作
    public void insert(String title,String maintext,String date){

        SQLiteDatabase db=this.getWritableDatabase();
        /* ContentValues */
        ContentValues cv=new ContentValues();
        cv.put(DIARY_TITLE,title);
        cv.put(DIARY_MAINTEXT,maintext);
        cv.put(DIARY_DATE,date);

        //zhuyi!!

        db.insert(TABLE_NAME,null,cv);
    }

    //删除操作
    public void delete(int id){
        SQLiteDatabase db=this.getWritableDatabase();
        String where=DIARY_ID +" =?";
        String[] whereValue={ Integer.toString(id)};
        db.delete(TABLE_NAME,where,whereValue);
    }

    //修改操作
    public void update(int id, String title,String maintext,String date)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        String where = DIARY_ID + " = ?";
        String[] whereValue = { Integer.toString(id) };

        ContentValues cv = new ContentValues();
        cv.put(DIARY_TITLE, title);
        cv.put(DIARY_MAINTEXT, maintext);
        cv.put(DIARY_DATE,date);
        db.update(TABLE_NAME, cv, where, whereValue);
    }

}
