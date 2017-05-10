package com.example.administrator.work;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity implements View.OnClickListener, ViewPager.OnPageChangeListener {

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

    //我的界面
    private LinearLayout ll_information;
    private TextView name,motto;

    // 中间内容区域
    private ViewPager viewPager;
    // ViewPager适配器ContentAdapter
    private ContentAdapter adapter;
    private List<View> views;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化控件
        initView();
        // 初始化底部按钮事件
        initEvent();

    }

    private void initEvent() {
        // 设置按钮监听
        ll_home.setOnClickListener(this);
        ll_address.setOnClickListener(this);
        ll_setting.setOnClickListener(this);
        //设置ViewPager滑动监听
        viewPager.setOnPageChangeListener(this);

        ll_information.setOnClickListener(this);
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

        //我的界面
        this.ll_information = (LinearLayout) page_03.findViewById(R.id.ll_information);
        this.name = (TextView) page_03.findViewById(R.id.name);
        this.motto = (TextView) page_03.findViewById(R.id.motto);

        show();

        views.add(page_01);
        views.add(page_02);
        views.add(page_03);

        this.adapter = new ContentAdapter(views);
        viewPager.setAdapter(adapter);
    }

    private void show(){
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this,
                "test_my_db",1);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from information ", null);
        while (cursor.moveToNext()) {
            name.setText(cursor.getString(0));
            motto.setText(cursor.getString(1));
        }
    }

    @Override
    public void onClick(View v) {
        // 在每次点击后将所有的底部按钮(ImageView,TextView)颜色改为灰色，然后根据点击着色
        restartBotton();
        // ImageView和TetxView置为绿色，页面随之跳转
        switch (v.getId()) {
            case R.id.ll_home:
                iv_home.setImageResource(R.drawable.tab_write_pressed);
                tv_home.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_address:
                iv_address.setImageResource(R.drawable.tab_address_pressed);
                tv_address.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(1);
                break;

            case R.id.ll_setting:
                iv_setting.setImageResource(R.drawable.tab_setting_pressed);
                tv_setting.setTextColor(0xff1B940A);
                viewPager.setCurrentItem(2);
                break;
            case R.id.ll_information:
                Intent intent = new Intent(MainActivity.this,information.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    private void restartBotton() {
        // ImageView置为灰色
        //iv_home.setImageResource(R.drawable.tab_weixin_normal);
        //iv_address.setImageResource(R.drawable.tab_address_normal);
        //iv_setting.setImageResource(R.drawable.tab_settings_normal);
        // TextView置为白色
        tv_home.setTextColor(0xd9d9d9d9);
        tv_address.setTextColor(0xd9d9d9d9);
        tv_setting.setTextColor(0xd9d9d9d9);
    }

    public void paly_login(View view)
    {
        Log.i("___________________", "_____________________");
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
                iv_home.setImageResource(R.drawable.tab_write_pressed);
                tv_home.setTextColor(0xff1B940A);
                break;
            case 1:
                iv_address.setImageResource(R.drawable.tab_address_pressed);
                tv_address.setTextColor(0xff1B940A);
                break;

            case 2:
                iv_setting.setImageResource(R.drawable.tab_setting_pressed);
                tv_setting.setTextColor(0xff1B940A);
                break;

            default:
                break;
        }

    }
}
