package com.salah.amr.workplace.ChatRoom.SetupDialog;

import android.util.Log;

import com.salah.amr.workplace.Base.BaseDialog;
import com.salah.amr.workplace.Model.EmployeeDatabase;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.Model.User;
import com.salah.amr.workplace.Model.UserDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 12/5/2017.
 */

public class ChatRoomSetupDialogPresenter implements IChatRoomSetupDialog.presenter, UserDatabase.IChatRoomSetupCallback {

    private static final String TAG = "ChatRoomSetupDialogPre";

    @Override
    public void callbackRegisterChatRoom(boolean flag) {
        Log.d(TAG, "callbackRegisterChatRoom: flag " + flag);
        if (!flag) {
            view.showErrorMessage();
        } else {
            preferences.setupChatRoomDialog(true);
            preferences.setEmployeeId(-1);
            view.dismissDialog();
        }
    }

    EmployeeDatabase employeeDatabase;
    private IChatRoomSetupDialog.view view;
    private EmployeeSharedPreferences preferences;
    UserDatabase userDatabase;

    @Inject
    public ChatRoomSetupDialogPresenter(BaseDialog view, EmployeeSharedPreferences preferences , EmployeeDatabase employeeDatabase , UserDatabase userDatabase) {
        this.view = (IChatRoomSetupDialog.view) view;
        this.preferences = preferences;
        this.userDatabase = userDatabase;
        this.employeeDatabase = employeeDatabase;
    }

    @Override
    public void addChatRoom(String name, String password) {
        Log.d(TAG, "addChatRoom: chatRoomName " + name);
        preferences.setManagerChatRoomName(name);
        preferences.setChatRoomName(name);
        if (name.isEmpty() || password.isEmpty()) {
            view.showMissingDataError();
        } else {
            userDatabase.registerChatRoom(name + "@amr.salah.com", password, this);
        }
    }

    @Override
    public void uploadEmployees() {
        List<User> users = new ArrayList<>();
        for (int i = 0; i < employeeDatabase.getEmployees().size(); i++) {
            User user = new User(employeeDatabase.getEmployees().get(i).getId() , employeeDatabase.getEmployees().get(i).getName() , ""  , "" , "");
            users.add(user);
        }

        userDatabase.insertUsers(users, preferences.getChatRoomName());
    }


}
