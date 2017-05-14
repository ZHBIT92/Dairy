package com.example.administrator.work;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class page_01_add extends Activity implements View.OnClickListener{

    private ImageView iv_tree;

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

        View page_02 = getLayoutInflater().inflate( R.layout.page_02, null);
        this.iv_tree = (ImageView)page_02. findViewById(R.id.iv_tree);

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
                //从数据库读取信息
                int i=0;
                Cursor cursor=db.query("note", null, null, null, null, null, null);
                startManagingCursor(cursor);
                while (cursor.moveToNext()){
                     i = cursor.getInt(cursor.getColumnIndex("num"));
                }
                //添加新日记
                if(enter_state==0){
                    if(!content.equals("")){
                        //获取时间
                        Date date=new Date();
                        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd HH-mm-ss");
                        String dateString=sdf.format(date);

                        //像数据库添加信息
                        ContentValues values=new ContentValues();
                        values.put("content",content);
                        values.put("date",dateString);

                        i++;
                        if(i==1){
                            values.put("tree","书写你的第一篇日记成功，获得种子");
                        }
                        else if(i==3){
                            values.put("tree","书写日记达到3篇，获得小树苗");
                        }
                        else if(i==5){
                            values.put("tree","书写日记达到5篇，小树苗长成小树");
                            iv_tree.setImageResource(R.drawable.tree4);
                        }
                        else if(i==80){
                            values.put("tree","书写日记达到80篇，小树成长为大树");
                            iv_tree.setImageResource(R.drawable.tree5);
                        }
                        else {
                            values.put("tree","书写一篇心得日记，能量+1");
                        }
                        values.put("num",i);
                        db.insert("note",null,values);
                        setResult(i, getIntent());
                        finish();
                    }else{
                        Toast.makeText(this,"写些什么吧!",Toast.LENGTH_SHORT).show();
                    }
                }else{
                    //查看修改日记
                    ContentValues values=new ContentValues();
                    values.put("content",content);
                    db.update("note",values,"content = ?",new String[]{last_content});
                    finish();
                }
                Toast.makeText(this,"已完成",Toast.LENGTH_SHORT).show();
                break;
            case R.id.btnCancel:
                Toast.makeText(this, "已取消", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }

    }
}
