package com.example.administrator.work;

        import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper{
    private static final int VERSION = 1;
    private String TABLE_NAME = "information";

    //四个参数的构造方法
    public DatabaseHelper(Context context, String name, CursorFactory factory,
                          int version) {
        super(context, name, factory, version);
    }
    //三个参数
    public DatabaseHelper(Context context, String name,
                          int version){
        this(context, name, null, version);
    }
    //两个参数
    public DatabaseHelper(Context context, String name){
        this(context, name,VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println("create a Database");
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("CREATE TABLE [" + TABLE_NAME  + "] (");    //创建表和定义表明
        sBuffer.append("[name] TEXT PRIMARY KEY,");   //设置name为主键，类型为text
        sBuffer.append("[motto] TEXT,");
        sBuffer.append("[mail] CHAR)");

        // 执行创建表的SQL语句
        db.execSQL(sBuffer.toString());
    }

    //更新表
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( "DROP TABLE IF EXISTS " + TABLE_NAME );
        onCreate(db);
    }


}
