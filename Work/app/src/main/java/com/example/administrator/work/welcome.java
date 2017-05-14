package com.example.administrator.work;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class welcome extends Activity {

    String pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final View view=View.inflate(this,R.layout.activity_welcome,null);
        setContentView(view);
        //
        AlphaAnimation aa=new AlphaAnimation(1,0);
        aa.setDuration(3000);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                skip();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        //字体
        AssetManager mgr=getAssets();
        Typeface tf=Typeface.createFromAsset(mgr,"fonts/BrannbollSmal.ttf");

        TextView tv_name,tv_words1,tv_words2;

        tv_name= (TextView) findViewById(R.id.tv_name);
        tv_words1= (TextView) findViewById(R.id.tv_words1);
        tv_words2= (TextView) findViewById(R.id.tv_words2);

        tv_name.setTypeface(tf);
        tv_words1.setTypeface(tf);
        tv_words2.setTypeface(tf);
    }

    private void skip(){

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        pwd=sp.getString("password", "");
        if(pwd==""){
            //跳转到OperationActivity
            Intent intent=new Intent(welcome.this,MainActivity.class);
            startActivity(intent);
            //关闭当前页面
            welcome.this.finish();
        }
        else{
            showWaiterAuthorizationDialog();
        }

    }

    //显示对话框
    public void showWaiterAuthorizationDialog() {
        //LayoutInflater是用来找layout文件夹下的xml布局文件，并且实例化
        LayoutInflater factory = LayoutInflater.from(welcome.this);
        //把activity_login中的控件定义在View中(获取焦点)
        final View textEntryView = factory.inflate(R.layout.activity_login, null);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        pwd=sp.getString("password", "");
        //将LoginActivity中的控件显示在对话框中
        new AlertDialog.Builder(this)
                //对话框的标题
                .setTitle("登陆")
                //设定显示的View
                .setView(textEntryView)
                //对话框中的“登陆”按钮的点击事件
                .setPositiveButton("登陆", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {

                        //获取用户输入的“密码”
                        //注意：textEntryView.findViewById很重要，因为上面factory.inflate(R.layout.activity_login, null)将页面布局赋值给了textEntryView了
                        final EditText etPassword = (EditText)textEntryView.findViewById(R.id.et_pwd);

                        //将页面输入框中获得的“用户名”，“密码”转为字符串
                        String password = etPassword.getText().toString().trim();

                        //现在为止已经获得了字符型的用户名和密码了，接下来就是根据自己的需求来编写代码了
                        //这里做一个简单的测试，假定输入的用户名和密码都是1则进入其他操作页面（OperationActivity）

                        if(password.equals(pwd)){
                            //跳转到OperationActivity
                            Intent intent=new Intent(welcome.this,MainActivity.class);
                            startActivity(intent);
                            //关闭当前页面
                            welcome.this.finish();
                        }else{
                            Toast.makeText(welcome.this, "密码错误", Toast.LENGTH_SHORT).show();
                            showWaiterAuthorizationDialog();
                        }
                    }
                })
                //对话框的“退出”单击事件
                .setNegativeButton("退出", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        welcome.this.finish();
                    }
                })
                //对话框的创建、显示
                .create().show();
    }

}
