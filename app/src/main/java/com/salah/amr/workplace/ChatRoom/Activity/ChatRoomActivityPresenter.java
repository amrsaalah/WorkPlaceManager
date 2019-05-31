package com.salah.amr.workplace.ChatRoom.Activity;

import android.util.Log;

import com.salah.amr.workplace.Base.BaseActivity;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.Model.User;
import com.salah.amr.workplace.Model.UserDatabase;

import javax.inject.Inject;

/**
 * Created by user on 12/5/2017.
 */

public class ChatRoomActivityPresenter implements IChatRoomActivity.presenter, UserDatabase.ICallbackGetUserById, UserDatabase.ICallbackCheckDeleted {

    @Override
    public void callbackCheckDeleted(Boolean flag) {
        Log.d(TAG, "callbackCheckDeleted: flag  " + flag);
        if (flag != null) {
            if (flag) {
                Log.d(TAG, "callbackCheckDeleted:");
                try {
                    Log.d(TAG, "callbackCheckDeleted: delete success");
                    activityView.showChatRoomLoginForm();
                } catch (IllegalStateException ignored) {
                    Log.d(TAG, "callbackCheckDeleted: catch ");
                    // There's no way to avoid getting this if saveInstanceState has already been called.
                }
            }
        }

    }

    @Override
    public void callbackGetUserById(User user) {
        Log.d(TAG, "callbackGetUserById: " + user);
        if (user == null) {
            Log.d(TAG, "callbackGetUserById:  user null");
            activityView.navigateToLoginActivity();
        } else {
            Log.d(TAG, "callbackGetUserById: user" + user);
            if (user.getDeleted()) {
                activityView.showChatRoomLoginForm();
            } else {
                activityView.showUserData(user.getName(), user.getImageURL());
            }
        }

    }

    private static final String TAG = "ChatRoomActivityPre";

    private EmployeeSharedPreferences preferences;
    private UserDatabase userDatabase;
    private IChatRoomActivity.activityView activityView;

    @Inject
    public ChatRoomActivityPresenter(BaseActivity activityView, EmployeeSharedPreferences preferences, UserDatabase userDatabase) {
        this.activityView = (IChatRoomActivity.activityView) activityView;
        this.preferences = preferences;
        this.userDatabase = userDatabase;
        Log.d(TAG, "ChatRoomActivityPresenter: userdatabase"+userDatabase);
    }

    @Override
    public void setupViewForManagerOrEmployee() {
        if (preferences.isManager()) {
            if (!preferences.isSetupChatRoomDialog()) {
                activityView.showSetupDialog();
            }
        } else {
            if (!preferences.isLoginChatRoomDialog()) {
                activityView.showChatRoomLoginForm();
            }
            activityView.hideBottomNavigationBar();
        }
    }

    @Override
    public void loadUserData() {
        Log.d(TAG, "loadUserData: " + preferences.getChatRoomName() + preferences.getEmployeeId());
        if (preferences.getEmployeeId() != -2) {
            userDatabase.getUserById(preferences.getChatRoomName(), preferences.getEmployeeId(), this);
            userDatabase.checkIfDeleted(preferences.getChatRoomName(), preferences.getEmployeeId(), this);
        }
    }

    @Override
    public void onEditProfileButtonClicked() {

    }


}
