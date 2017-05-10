package com.example.administrator.work;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/5/6.
 */

public class information extends Activity implements View.OnClickListener {
    //个人信息修改界面
    private Button btn_change;
    private TextView name,nameTop,motto,mail;
    private TextView name1,motto1,mail1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_information);

        this.btn_change = (Button) findViewById(R.id.btn_change);
        btn_change.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change:
                Intent intent = new Intent(information.this, information_change.class);
                name1 = (TextView) findViewById(R.id.nameText);
                motto1 = (TextView) findViewById(R.id.mottoText);
                mail1 = (TextView) findViewById(R.id.mailText);
                intent.putExtra("name", name1.getText().toString());
                intent.putExtra("motto", motto1.getText().toString());
                intent.putExtra("mail", mail1.getText().toString());
                startActivityForResult(intent, R.layout.page_information_change);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case R.layout.page_information_change:
                if (resultCode == 20) {
                    name = (TextView) findViewById(R.id.nameText);
                    nameTop = (TextView) findViewById(R.id.nameTextTop);
                    motto = (TextView) findViewById(R.id.mottoText);
                    mail = (TextView) findViewById(R.id.mailText);

                    DatabaseHelper dbHelper = new DatabaseHelper(information.this,
                            "test_my_db",1);
                    SQLiteDatabase db = dbHelper.getReadableDatabase();
                    Cursor cursor = db.rawQuery("select * from information ", null);
                    while (cursor.moveToNext()) {
                        name.setText(cursor.getString(0));
                        nameTop.setText(cursor.getString(0));
                        motto.setText(cursor.getString(1));
                        mail.setText(cursor.getString(2));
                    }
                    Toast toast = Toast.makeText(this, "执行", Toast.LENGTH_LONG);
                    toast.show();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
