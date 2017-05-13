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

public class page_03_password extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    //个人信息修改界面
    private EditTextPreference password;       //密码

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.layout.page_03_password);

        //根据key值找到控件
        password = (EditTextPreference) findPreference("password");

        // 设置监听器
        password.setOnPreferenceChangeListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        return true;
    }

    @Override
    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen,
                                         Preference preference) {
        return true;
    }
}
