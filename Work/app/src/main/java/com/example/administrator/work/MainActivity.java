package com.example.administrator.work;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener,AdapterView.OnItemClickListener,AdapterView.OnItemLongClickListener{

    //双击返回键退出
    private long exitTime=0;
    // 底部菜单3个Linearlayout
    private LinearLayout ll_home;
    private LinearLayout ll_address;
    private LinearLayout ll_setting;
    // 底部菜单3个ImageView
    private ImageView iv_home;
    private ImageView iv_address;
    private ImageView iv_setting;
    // 底部菜单3个菜单标题
    private TextView tv_home;
    private TextView tv_address;
    private TextView tv_setting;

    //日记界面
    private Calendar c=Calendar.getInstance();//获取日期
    private ListView lv_note;
    private SimpleAdapter simple_Adapter;
    private List<Map<String,Object>> dataList;
    private Button btnAdd;
    private Button btnClearAll;//5-13
    private TextView tv_date;
    private TextView tv_content;
    private NoteDataBaseHelper DBHelper;
    private SQLiteDatabase DB;

    //种树界面
    int i=1;
    private TextView tv_tree;
    private TextView tv_date1;
    private List<Map<String,Object>> treeList;
    private ImageView iv_tree;
    private ListView lv_tree;

    //我的界面
    private LinearLayout ll_information,ll_set,ll_pwd,ll_help;
    private TextView name,motto;

    // 中间内容区域
    private ViewPager viewPager;
    // ViewPager适配器ContentAdapter
    private ContentAdapter adapter;
    private List<View> views;

    protected void onStart() {
        super.onStart();
        RefreshNoteList();
        RefreshTreeList();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        name.setText(sp.getString("name", ""));
        motto.setText(sp.getString("motto", ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化控件
        initView();
        // 初始化底部按钮事件
        initEvent();
    }

    //双击退出
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode==KeyEvent.KEYCODE_BACK && event.getAction()==KeyEvent.ACTION_DOWN){
            exit();
            return true;
        }
        return super.onKeyDown(keyCode,event);
    }

    public void exit(){
        if(System.currentTimeMillis()-exitTime>2000){
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime=System.currentTimeMillis();
        }else{
            finish();
            System.exit(0);
        }
    }

    private void initEvent() {
        // 设置按钮监听
        ll_home.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        //设置ViewPager滑动监听
        viewPager.setOnPageChangeListener(this);

        //日记界面监听
        lv_note.setOnItemClickListener(this);
        lv_note.setOnItemLongClickListener(this);
        btnAdd.setOnClickListener(this);
        btnClearAll.setOnClickListener(this);

        //种树界面

        //我的界面监听
        ll_information.setOnClickListener(this);
        ll_pwd.setOnClickListener(this);
        ll_set.setOnClickListener(this);
        ll_help.setOnClickListener(this);
    }

    private void initView() {
        // 底部菜单3个Linearlayout
        this.ll_home = (LinearLayout) findViewById(R.id.ll_home);
        this.ll_address = (LinearLayout) findViewById(R.id.ll_address);
        this.ll_setting = (LinearLayout) findViewById(R.id.ll_setting);

        // 底部菜单3个ImageView
        this.iv_home = (ImageView) findViewById(R.id.iv_home);
        this.iv_address = (ImageView) findViewById(R.id.iv_address);
        this.iv_setting = (ImageView) findViewById(R.id.iv_setting);

        // 底部菜单3个菜单标题
        this.tv_home = (TextView) findViewById(R.id.tv_home);
        this.tv_address = (TextView) findViewById(R.id.tv_address);
        this.tv_setting = (TextView) findViewById(R.id.tv_setting);

        // 中间内容区域ViewPager
        this.viewPager = (ViewPager) findViewById(R.id.vp_content);

        views = new ArrayList<View>();
        // 适配器
        View page_01 = getLayoutInflater().inflate(R.layout.page_01, null);
        View page_02 = getLayoutInflater().inflate( R.layout.page_02, null);
        View page_03 = getLayoutInflater().inflate( R.layout.page_03, null);

        //日记界面
        //设置时间
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("MM-dd HH:mm:ss");
        String dateString=sdf.format(date);
        this.tv_date= (TextView) page_01.findViewById(R.id.tv_date);
        this.tv_content= (TextView) page_01.findViewById(R.id.tv_content);
        this.lv_note= (ListView) page_01.findViewById(R.id.lv_note);
        dataList=new ArrayList<Map<String,Object>>();
        this.btnAdd= (Button) page_01.findViewById(R.id.btnAdd);
        this.btnClearAll= (Button) page_01.findViewById(R.id.btnClearAll);//5-13
        DBHelper=new NoteDataBaseHelper(this);
        DB=DBHelper.getReadableDatabase();

        ////种树界面
        this.tv_tree= (TextView) page_02.findViewById(R.id.tv_tree);
        this.tv_date1= (TextView) page_02.findViewById(R.id.tv_date1);
        treeList=new ArrayList<Map<String,Object>>();
        this.iv_tree = (ImageView)page_02. findViewById(R.id.iv_tree);
        this.lv_tree= (ListView) page_02.findViewById(R.id.lv_tree);

        //我的界面
        this.ll_information = (LinearLayout) page_03.findViewById(R.id.ll_information);
        this.ll_pwd = (LinearLayout) page_03.findViewById(R.id.ll_pwd);
        this.ll_help = (LinearLayout) page_03.findViewById(R.id.ll_help);
        this.ll_set = (LinearLayout) page_03.findViewById(R.id.ll_set);

        this.name = (TextView) page_03.findViewById(R.id.name);
        this.motto = (TextView) page_03.findViewById(R.id.motto);

        views.add(page_01);
        views.add(page_02);
        views.add(page_03);

        this.adapter = new ContentAdapter(views);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        // 在每次点击后将所有的底部按钮(ImageView,TextView)颜色改为灰色，然后根据点击着色
        restartBotton();
        // ImageView和TetxView置为蓝色，页面随之跳转
        switch (v.getId()) {
            case R.id.ll_home:
                iv_home.setImageResource(R.drawable.nav_note);
                tv_home.setTextColor(Color.parseColor("#146ef5"));
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_address:
                iv_address.setImageResource(R.drawable.nav_mid);
                tv_address.setTextColor(Color.parseColor("#146ef5"));
                viewPager.setCurrentItem(1);
                break;

            case R.id.ll_setting:
                iv_setting.setImageResource(R.drawable.nav_me);
                tv_setting.setTextColor(Color.parseColor("#146ef5"));
                viewPager.setCurrentItem(2);
                break;

            case R.id.btnAdd:
                iv_home.setImageResource(R.drawable.nav_note);
                tv_home.setTextColor(Color.parseColor("#146ef5"));
                Intent intent1=new Intent(MainActivity.this,page_01_add.class);
                Bundle bundle=new Bundle();
                bundle.putString("info","");
                bundle.putInt("enter_state",0);
                intent1.putExtras(bundle);
                startActivityForResult(intent1,R.layout.page_01_add);
                break;
            case R.id.btnClearAll:
                iv_home.setImageResource(R.drawable.nav_note);
                tv_home.setTextColor(Color.parseColor("#146ef5"));
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("清空便签");
                builder.setMessage("是否确定清空便签？");

                builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        //清空sql，刷新
                        DB.execSQL("delete from note");
                        RefreshNoteList();
                        RefreshTreeList();
                    }
                });

                builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {

                    }
                });
                builder.create();
                builder.show();
                break;

            case R.id.ll_information:
                iv_setting.setImageResource(R.drawable.nav_me);
                tv_setting.setTextColor(Color.parseColor("#146ef5"));
                Intent intent3 = new Intent(MainActivity.this,page_03_information.class);
                startActivity(intent3);
                break;
            case R.id.ll_set:
                iv_setting.setImageResource(R.drawable.nav_me);
                tv_setting.setTextColor(Color.parseColor("#146ef5"));
                Intent intent31 = new Intent(MainActivity.this,page_03_setting.class);
                startActivity(intent31);
                break;
            case R.id.ll_pwd:
                iv_setting.setImageResource(R.drawable.nav_me);
                tv_setting.setTextColor(Color.parseColor("#146ef5"));
                Intent intent32 = new Intent(MainActivity.this,page_03_password.class);
                startActivity(intent32);
                break;
            case R.id.ll_help:
                iv_setting.setImageResource(R.drawable.nav_me);
                tv_setting.setTextColor(Color.parseColor("#146ef5"));
                new AlertDialog.Builder(this)
                        .setTitle("帮助")
                        .setMessage("出门右转知乎")
                        .setPositiveButton("确定",null)
                        .show();
            default:
                Toast.makeText(this,"Error",Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case R.layout.page_01_add:
                if (resultCode == 1) {
                    iv_tree.setImageResource(R.drawable.tree2);
                    new AlertDialog.Builder(this)
                            .setMessage("书写第一篇日记，解锁种子成功")
                            .setPositiveButton("解锁",null)
                            .show();
                }
                else if (resultCode == 3) {
                    iv_tree.setImageResource(R.drawable.tree3);
                    new AlertDialog.Builder(this)
                            .setMessage("书写日记达到3篇，获得小树苗")
                            .setPositiveButton("解锁",null)
                            .show();
                }
                else if (resultCode == 5) {
                    iv_tree.setImageResource(R.drawable.tree4);
                    new AlertDialog.Builder(this)
                            .setMessage("书写日记达到5篇，获取小树")
                            .setPositiveButton("解锁",null)
                            .show();
                }
                else if (resultCode == 80) {
                    iv_tree.setImageResource(R.drawable.tree5);
                    new AlertDialog.Builder(this)
                            .setMessage("解锁大树成功")
                            .setPositiveButton("解锁",null)
                            .show();
                }
                break;
            default:
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void restartBotton() {
        // ImageView置为灰色
        iv_home.setImageResource(R.drawable.nav_note_press);
        iv_address.setImageResource(R.drawable.nav_mid_press);
        iv_setting.setImageResource(R.drawable.nav_me_press);
        // TextView置为白色
        tv_home.setTextColor(Color.parseColor("#CCCCCC"));
        tv_address.setTextColor(Color.parseColor("#CCCCCC"));
        tv_setting.setTextColor(Color.parseColor("#CCCCCC"));
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int arg0) {
        restartBotton();
        //当前view被选择的时候,改变底部菜单图片，文字颜色
        switch (arg0) {
            case 0:
                iv_home.setImageResource(R.drawable.nav_note);
                tv_home.setTextColor(Color.parseColor("#146ef5"));
                break;
            case 1:
                iv_address.setImageResource(R.drawable.nav_mid);
                tv_address.setTextColor(Color.parseColor("#146ef5"));
                break;
            case 2:
                iv_setting.setImageResource(R.drawable.nav_me);
                tv_setting.setTextColor(Color.parseColor("#146ef5"));

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
                name.setText(sp.getString("name", "").toString());
                motto.setText(sp.getString("motto", ""));
                break;

            default:
                break;
        }
    }

   @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        //获取listview中item内容
        String content=lv_note.getItemAtPosition(arg2) + "";
        String content1=content.substring(content.indexOf("=") + 1, content.indexOf(","));
       //test
       Log.d("item click ",content);
       Log.d("item click ",content1);

        Intent mIntent=new Intent(MainActivity.this, page_01_add.class);
        Bundle bundle=new Bundle();
        bundle.putString("info",content1);
        bundle.putInt("enter_state", 1);
        mIntent.putExtras(bundle);
        startActivity(mIntent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2, long arg3) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("删除便签");
        builder.setMessage("是否确定删除选中项？");

        builder.setPositiveButton("是", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                //获取listview中item中的内容
                //删除该行后刷新listvi内容
                String content=lv_note.getItemAtPosition(arg2) + "";
                String content1=content.substring(content.indexOf("=") + 1, content.indexOf(","));
                String content2=content.substring(content.lastIndexOf("=") + 1,content.indexOf("}"));
                //test
                Log.d("item long click ",content);
                Log.d("item long click ",content1);
                Log.d("item long click ",content2);

                DB.delete("note","content = ? and date = ?",new String[]{content1,content2});
                //test
                Log.d("item delete: ","delete from note where content=" + content1+" and date=" + content2);
                RefreshNoteList();
                RefreshTreeList();
            }
        });

        builder.setNegativeButton("否", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {

            }
        });
        builder.create();
        builder.show();
        return true;
    }

    //刷新listview
    public void RefreshTreeList(){
        //如果treeList已有内容 全部删除 求更新simple_adapter
        int size=treeList.size();
        if(size>0){
            treeList.removeAll(treeList);
            simple_Adapter.notifyDataSetChanged();
        }

        //从数据库读取信息
        Cursor cursor=DB.query("note", null, null, null, null, null, null);
        startManagingCursor(cursor);

        while (cursor.moveToNext()){
            String tree = cursor.getString(cursor.getColumnIndex("tree"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            Map<String ,Object> map=new HashMap<String, Object>();
            String date2=date.substring(0,date.indexOf(" "));
            map.put("tv_tree",tree);
            map.put("tv_date1",date2);
            treeList.add(map);
        }
        simple_Adapter=new SimpleAdapter(this,treeList, R.layout.tree_list,
                new String[]{"tv_tree","tv_date1"}, new int[]{R.id.tv_tree, R.id.tv_date1});
        lv_tree.setAdapter(simple_Adapter);

    }

    //刷新listview
    public void RefreshNoteList(){
        //如果datalist已有内容 全部删除 求更新simple_adapter
        int size=dataList.size();
        if(size>0){
            dataList.removeAll(dataList);
            simple_Adapter.notifyDataSetChanged();
        }

        //从数据库读取信息
        Cursor cursor=DB.query("note", null, null, null, null, null, null);
        startManagingCursor(cursor);

        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("content"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            Map<String ,Object> map=new HashMap<String, Object>();
            String date2=date.substring(0,date.indexOf(" "));
            map.put("tv_content",name);
            map.put("tv_date",date2);
            dataList.add(map);
        }
        simple_Adapter=new SimpleAdapter(this,dataList, R.layout.item,
                new String[]{"tv_content","tv_date"}, new int[]{R.id.tv_content, R.id.tv_date});
        lv_note.setAdapter(simple_Adapter);

    }

}
