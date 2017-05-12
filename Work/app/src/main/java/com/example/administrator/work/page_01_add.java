package com.example.administrator.work;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class page_01_add extends Activity implements View.OnClickListener{

    private TextView tv_date;//??
    private EditText et_content;
    private Button btnSave,btnCancel;

    private NoteDataBaseHelper DBHelper;

    public int enter_state=0;
    public String last_content="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.page_01_add);
        InitView();
    }

    private void InitView(){
        tv_date= (TextView) findViewById(R.id.tv_date);
        et_content= (EditText) findViewById(R.id.et_content);
        btnSave= (Button) findViewById(R.id.btnSave);
        btnCancel= (Button) findViewById(R.id.btnCancel);

        DBHelper=new NoteDataBaseHelper(this);

        //接受内容、id
        Bundle mBundle=this.getIntent().getExtras();
        last_content=mBundle.getString("info");
        enter_state=mBundle.getInt("enter_state");
        et_content.setText(last_content);

        //监听器
        btnSave.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btnSave:
                SQLiteDatabase db=DBHelper.getReadableDatabase();
                //获取edittext内容
                String content=et_content.getText().toString();

                //添加新日记
                if(enter_state==0){
                    if(!content.equals("")){
                        //获取时间
                        Date date=new Date();
                        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd");
                        String dateString=sdf.format(date);
                        //像数据库添加信息
                        ContentValues values=new ContentValues();
                        values.put("content",content);
                        values.put("date",dateString);

                        db.insert("note",null,values);
                        finish();
                    }else{
                        Toast.makeText(this,"Please input",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //查看修改日记
                    ContentValues values=new ContentValues();
                    values.put("content",content);
                    db.update("note",values,"content = ?",new String[]{last_content});
                    finish();
                }
                Toast.makeText(this,"Finished",Toast.LENGTH_SHORT).show();

                break;
            case R.id.btnCancel:
                Toast.makeText(this, "Cancled", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

    }
}