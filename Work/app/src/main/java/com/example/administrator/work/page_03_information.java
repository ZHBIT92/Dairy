package com.example.administrator.work;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;

/**
 * Created by Administrator on 2017/5/6.
 */

public class page_03_information extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    //个人信息修改界面
    private EditTextPreference name,motto, mobile, mail;       //昵称、电话号码、邮箱、密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.page_03_information);

        //根据key值找到控件
        name = (EditTextPreference) findPreference("name");
        motto = (EditTextPreference) findPreference("motto");
        mobile = (EditTextPreference) findPreference("mobile");
        mail = (EditTextPreference) findPreference("mail");

        // 设置监听器
        name.setOnPreferenceChangeListener(this);
        motto.setOnPreferenceChangeListener(this);
        mobile.setOnPreferenceChangeListener(this);
        mail.setOnPreferenceChangeListener(this);

    }

    protected void onResume(){
        super.onResume();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        name.setSummary(sp.getString("name", ""));
        motto.setSummary(sp.getString("motto", ""));
        mobile.setSummary(sp.getString("mobile", ""));
        mail.setSummary(sp.getString("mail", ""));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        preference.setSummary(String.valueOf(newValue));
        return true;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
                                         Preference preference) {
        SharedPreferences sp = preference.getSharedPreferences();
        String name = sp.getString("name", "");
        Log.i("lenve", name+"");
        String motto = sp.getString("motto", "");
        Log.i("lenve", motto+"");
        String mobile = sp.getString("mobile", "");
        Log.i("lenve", mobile+"");
        String mail = sp.getString("mail", "");
        Log.i("lenve", mail+"");
        return true;
    }
}
