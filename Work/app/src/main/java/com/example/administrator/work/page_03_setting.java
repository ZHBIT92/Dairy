package com.example.administrator.work;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


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

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        preference.setSummary(String.valueOf(newValue));
        return true;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
                                         Preference preference) {
        SharedPreferences sp = preference.getSharedPreferences();
        boolean wifiChecked = sp.getBoolean("wifi", false);
        boolean gpsChecked = sp.getBoolean("gps", false);
        Log.i("lenve", wifiChecked+""+gpsChecked);
        return true;

    }
}
