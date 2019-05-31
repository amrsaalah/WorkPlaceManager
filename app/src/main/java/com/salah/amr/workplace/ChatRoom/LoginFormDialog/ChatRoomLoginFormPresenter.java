package com.salah.amr.workplace.ChatRoom.LoginFormDialog;

import android.util.Log;

import com.salah.amr.workplace.Base.BaseDialog;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.Model.UserDatabase;
import com.salah.amr.workplace.Utils.SwitchManagerEmployeeHelper;

import javax.inject.Inject;

/**
 * Created by user on 12/7/2017.
 */

public class ChatRoomLoginFormPresenter implements IChatRoomLoginFormDialog.presenter, UserDatabase.IChatRoomLoginFormCallback {

    @Override
    public void loginCallback(boolean flag) {
        Log.d(TAG, "loginCallback: ");
        if(view != null){
            if (flag) {
                view.dismissDialog();
            } else {
                view.showError();
            }
        }
    }

    private static final String TAG = "ChatRoomLoginFormPrese";
    private IChatRoomLoginFormDialog.view view;
    private EmployeeSharedPreferences preferences;
    private UserDatabase userDatabase;

    @Inject
    public ChatRoomLoginFormPresenter(BaseDialog view, EmployeeSharedPreferences preferences , UserDatabase userDatabase) {
        this.view = (IChatRoomLoginFormDialog.view) view;
        this.preferences = preferences;
        this.userDatabase = userDatabase;

    }


    @Override
    public void login(String name, String password) {
        Log.d(TAG, "login: ");
        preferences.setEmployeeChatRoomName(name);
        preferences.setChatRoomName(name);
        if (name.isEmpty() || password.isEmpty()) {
            view.showMissingDataError();
        } else {
            userDatabase.chatRoomLogin(name + "@amr.salah.com", password, this);
        }
    }

    @Override
    public void onSwitchButtonClicked() {
        SwitchManagerEmployeeHelper.switchManagerEmployee(userDatabase , preferences , view);
    }

}
