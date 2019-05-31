package com.salah.amr.workplace.MyProfile;

import android.util.Log;

import com.salah.amr.workplace.Base.BaseActivity;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.Model.User;
import com.salah.amr.workplace.Model.UserDatabase;
import com.salah.amr.workplace.Utils.SwitchManagerEmployeeHelper;

import javax.inject.Inject;

/**
 * Created by user on 12/15/2017.
 */

public class MyProfilePresenter implements IMyProfile.presenter , UserDatabase.ICallbackGetUserById {
    private static final String TAG = "MyProfilePresenter";


    @Override
    public void callbackGetUserById(User user) {
        Log.d(TAG, "callbackGetUserById: user"+user);
        if(user != null){
            if(user.getDeleted()){
                view.navigateToChatRoomActivity();
            }else{
                view.showUserData(user.getName() , user.getImageURL() , user.getEmail()  , user.getPhone());
            }
        }
    }

    private IMyProfile.view view;
    private EmployeeSharedPreferences preferences;
    private UserDatabase userDatabase;

    @Inject
    public MyProfilePresenter(BaseActivity view, EmployeeSharedPreferences preferences , UserDatabase userDatabase) {
        this.view = (IMyProfile.view) view;
        this.preferences = preferences;
        this.userDatabase = userDatabase;
    }

    @Override
    public void loadUserData() {
        Log.d(TAG, "loadUserData: ");
        userDatabase.getUserById(preferences.getChatRoomName() , preferences.getEmployeeId() , this);
        if(preferences.getEmployeeId() == -1){
            String s = "Switch To Employee";
            view.updateSwitchButton(s);
        }else{
            String s = "Switch To Manager";
            view.updateSwitchButton(s);
        }

    }

    @Override
    public void updateUserData(String userName , String email , String phone) {
        userDatabase.updateUserValues(preferences.getChatRoomName() , preferences.getEmployeeId() , userName , null , email  , phone , null);
        view.refreshView();
    }

    @Override
    public void onSwitchButtonClicked() {
        SwitchManagerEmployeeHelper.switchManagerEmployee(userDatabase , preferences , view);
    }
}
