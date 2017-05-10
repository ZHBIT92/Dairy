package com.example.administrator.work;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Administrator on 2017/5/6.
 */

public class information_change extends Activity implements View.OnClickListener {
    private EditText name, motto, mail;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:
                name = (EditText) findViewById(R.id.nameText);
                motto = (EditText) findViewById(R.id.mottoText);
                mail = (EditText) findViewById(R.id.mailText);
                // 创建一个DatabaseHelper对象
                DatabaseHelper dbHelper = new DatabaseHelper(information_change.this,
                        "test_my_db", 1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                db.execSQL("update information set name=?,motto=?,mail=?" ,
                        new Object[]{name.getText().toString(),motto.getText().toString(),mail.getText().toString()});
                /*db.execSQL("insert into information(name,motto,mail) values(?,?,?)" ,
                        new Object[]{name.getText().toString(),motto.getText().toString(),mail.getText().toString()});*/
                setResult(20, getIntent());
                break;
        }
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_information_change);

        name = (EditText) findViewById(R.id.nameText);
        motto = (EditText) findViewById(R.id.mottoText);
        mail = (EditText) findViewById(R.id.mailText);

        String name1 = getIntent().getStringExtra("name");
        String motto1 = getIntent().getStringExtra("motto");
        String mail1 = getIntent().getStringExtra("mail");
        name.setText(name1);
        motto.setText(motto1);
        mail.setText(mail1);

        Button save = (Button) findViewById(R.id.btn_save);
        save.setOnClickListener(this);
    }
}
