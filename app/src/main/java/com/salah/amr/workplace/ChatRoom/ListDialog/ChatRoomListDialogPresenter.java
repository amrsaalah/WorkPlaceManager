package com.salah.amr.workplace.ChatRoom.ListDialog;

import android.util.Log;

import com.salah.amr.workplace.Base.BaseDialog;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.Model.User;
import com.salah.amr.workplace.Model.UserDatabase;
import com.salah.amr.workplace.Utils.SwitchManagerEmployeeHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 12/8/2017.
 */

public class ChatRoomListDialogPresenter implements IChatRoomListDialog.presenter, UserDatabase.ICallbackGetEmployees, UserDatabase.ICallbackGetUserById {

    @Override
    public void callbackGetUserById(User user) {
        Log.d(TAG, "callbackGetUserById: ");
        if (user.isSelected()) {
            Log.d(TAG, "callbackGetUserById:  selected ");
            Log.d(TAG, "callbackGetUserById: in");
            dialog.showErrorMessage();
        } else {
            Log.d(TAG, "callbackGetUserById: not selected  ");
            Log.d(TAG, "callbackGetUserById: in");
            userDatabase.updateUserValues(preferences.getChatRoomName(), user.getId(), null, null, null, null, true);
            preferences.setEmployeeId(user.getId());
            preferences.loginChatRoomDialog(true);
            dialog.dismissDialog();
            dialog.navigateToChatRoomActivity();
        }


    }

    @Override
    public void callbackGetEmployees(List<User> users) {
        Log.d(TAG, "callbackGetEmployees: ");
        this.users = users;
        List<String> strings = new ArrayList<>();
        for (int i = 0; i < users.size(); i++) {
            if (!users.get(i).getDeleted())
                strings.add(users.get(i).getName());
        }
        dialog.setEmployeeNames(strings);
    }


    private static final String TAG = "ChatRoomListDialogPr";
    private final UserDatabase userDatabase;
    private IChatRoomListDialog.dialog dialog;
    private EmployeeSharedPreferences preferences;
    private List<User> users = new ArrayList<>();
    int id;

    @Inject
    public ChatRoomListDialogPresenter(BaseDialog dialog, EmployeeSharedPreferences preferences , UserDatabase userDatabase) {
        this.dialog = (IChatRoomListDialog.dialog) dialog;
        this.preferences = preferences;
        this.userDatabase = userDatabase;
    }


    @Override
    public void loadEmployeesForDialogList(String name) {
        userDatabase.getEmployees(name, this);
    }

    @Override
    public void saveEmployeeId(int position) {
        Log.d(TAG, "saveEmployeeId: ");
        id = users.get(position).getId();
        userDatabase.getUserById(preferences.getChatRoomName(), id, this);

    }

    @Override
    public void onSwitchButtonClicked() {
        SwitchManagerEmployeeHelper.switchManagerEmployee(userDatabase , preferences , dialog);
    }


}
