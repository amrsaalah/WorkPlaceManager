package com.salah.amr.workplace.Settings;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.salah.amr.workplace.ChatRoom.Activity.ChatRoomActivity;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.EmployeeList.EmployeeListActivity;
import com.salah.amr.workplace.Login.LoginActivity;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.Model.UserDatabase;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;
import com.salah.amr.workplace.Utils.SwitchView;
import com.salah.amr.workplace.Utils.SwitchManagerEmployeeHelper;

import javax.inject.Inject;

/**
 * Created by user on 12/1/2017.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener , SwitchView {

    private static final String TAG = "SettingsFragment";

    @Inject
    UserDatabase userDatabase;

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        Log.d(TAG, "onCreate: userdatabase "+userDatabase);
        PreferenceManager.setDefaultValues(getActivity() ,R.xml.preferences , false);
        PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);

        Preference button = findPreference("switch_employee");
        button.setOnPreferenceClickListener(preference -> {
            SwitchManagerEmployeeHelper.switchManagerEmployee(userDatabase, new EmployeeSharedPreferences(getActivity()) , this);

            return true;
        });

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

    }


    public void navigateToEmployeeListActivity() {
        Intent intent = new Intent(getActivity() , EmployeeListActivity.class);
        startActivity(intent);
    }

    public void navigateToChatRoomActivity() {
        Intent intent = new Intent(getActivity() , ChatRoomActivity.class);
        startActivity(intent);
    }

    public void navigateToLoginActivity() {
        Intent intent  = new Intent(getActivity() , LoginActivity.class);
        startActivity(intent);
    }
}
