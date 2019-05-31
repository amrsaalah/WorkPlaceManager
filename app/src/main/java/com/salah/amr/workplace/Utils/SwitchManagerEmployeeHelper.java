package com.salah.amr.workplace.Utils;

import android.util.Log;

import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.Model.UserDatabase;

/**
 * Created by user on 1/11/2018.
 */

public class SwitchManagerEmployeeHelper {
    private static final String TAG = "SwitchManagerEmployeeHe";

    public static void switchManagerEmployee(UserDatabase userDatabase , EmployeeSharedPreferences preferences , SwitchView view){
        Log.d(TAG, "switchManagerEmployee: is manager "+preferences.isManager());
        Log.d(TAG, "switchManagerEmployee:  temp id "+preferences.getTempEmployeeId());
        Log.d(TAG, "switchManagerEmployee: employee id"+preferences.getEmployeeId());

        if(preferences.isManager()){
            Log.d(TAG, "switchManagerEmployee: prefrence manager");
            if(preferences.isLoginChatRoomDialog()){
                preferences.setManagerOrEmployee(1);
                preferences.setEmployeeId(preferences.getTempEmployeeId());
                preferences.setChatRoomName(preferences.getEmployeeChatRoomName());
                userDatabase.signOut();
                view.navigateToLoginActivity();
            }else{
                preferences.setManagerOrEmployee(1);
                preferences.setEmployeeId(-2);
                preferences.setChatRoomName("");
                preferences.setSignedOut(false);
                view.navigateToChatRoomActivity();
            }
        }
        else{
            Log.d(TAG, "switchManagerEmployee:  prefrence employee");
            if(preferences.isSetupChatRoomDialog()){
                preferences.setTempEmployeeId(preferences.getEmployeeId());
                preferences.setManagerOrEmployee(0);
                preferences.setEmployeeId(-1);
                preferences.setChatRoomName(preferences.getManagerChatRoomName());
                userDatabase.signOut();
                view.navigateToLoginActivity();
            }else{
                Log.d(TAG, "switchManagerEmployee:  setup false");
                preferences.setTempEmployeeId(preferences.getEmployeeId());
                preferences.setManagerOrEmployee(0);
                preferences.setEmployeeId(-2);
                preferences.setChatRoomName("");
                preferences.setSignedOut(false);
                view.navigateToEmployeeListActivity();
            }
        }

    }
}
