package com.example.administrator.work;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;


public class page_03_setting extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    //个人信息修改界面
    private CheckBoxPreference wifi,gps;       //打开wifi
    private ListPreference language;          //部门设置
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.page_03_setting);

        //根据key值找到控件
        wifi = (CheckBoxPreference) findPreference("wifi");
        gps = (CheckBoxPreference) findPreference("gps");
        language = (ListPreference) findPreference("language");

        // 设置监听器
        wifi.setOnPreferenceChangeListener(this);
        gps.setOnPreferenceChangeListener(this);
        language.setOnPreferenceChangeListener(this);
    }

    protected void onResume(){
        super.onResume();
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        language.setSummary(sp.getString("language", ""));
    }

    //onPreferenceChange的方法独立于其他的运行，总会运行(最先被调用)
    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.getKey().equals("language")) {
            preference.setSummary(String.valueOf(newValue));
        }
        return true;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
                                         Preference preference) {
        return true;
    }
}
