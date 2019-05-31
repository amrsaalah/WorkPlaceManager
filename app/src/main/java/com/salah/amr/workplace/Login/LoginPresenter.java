package com.salah.amr.workplace.Login;

import com.salah.amr.workplace.Base.BaseActivity;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.Model.UserDatabase;
import com.salah.amr.workplace.Utils.SwitchManagerEmployeeHelper;

import javax.inject.Inject;

/**
 * Created by user on 1/11/2018.
 */

public class LoginPresenter implements ILogin.presenter  , UserDatabase.IChatRoomLoginFormCallback {
    private static final String TAG = "LoginPresenter";
    @Override
    public void loginCallback(boolean flag) {
        if(flag){
            preferences.setSignedOut(false);
            if(!preferences.isManager()){
                view.navigateToChatRoomActivity();
            }
            else{
                view.navigateToEmployeeListActivity();
            }
        }
        else{
            view.showErrorMessage();
        }
    }

    private final ILogin.view view;
    private EmployeeSharedPreferences preferences;
    UserDatabase userDatabase;

    @Inject
    public LoginPresenter(BaseActivity view , EmployeeSharedPreferences preferences , UserDatabase userDatabase){
        this.view = (ILogin.view) view;
        this.preferences = preferences;
        this.userDatabase = userDatabase;
    }

    @Override
    public void onLoginButtonClicked(String name, String password) {
        if(!preferences.isManager()){
            if(!name.equals(preferences.getEmployeeChatRoomName())){
                view.showWrongChatRoomMessage("You have to enter employee chat room name and password");
            }else{
                userDatabase.chatRoomLogin(name + "@amr.salah.com" , password , this);
            }
        }else{
            if(!name.equals(preferences.getManagerChatRoomName())){
                view.showWrongChatRoomMessage("You have to enter manager chat room name and password");
            }else{
                userDatabase.chatRoomLogin(name + "@amr.salah.com" , password , this);
            }
        }
    }

    @Override
    public void fillEditChatRoomName() {
        if(!preferences.isManager()){
            view.updateEditChatRoomName(preferences.getEmployeeChatRoomName());
        }
        else{
            view.updateEditChatRoomName(preferences.getManagerChatRoomName());
        }
    }

    @Override
    public void onSwitchButtonClicked() {
        SwitchManagerEmployeeHelper.switchManagerEmployee(userDatabase ,preferences , view);
    }

    @Override
    public void onForgotButtonClicked() {
        if(!preferences.isManager()){

            // logging in as employee
            preferences.setManagerOrEmployee(1);
            preferences.setEmployeeChatRoomName("");
            preferences.loginChatRoomDialog(false);
            preferences.setChatRoomName("");
            preferences.setEmployeeId(-2);
            preferences.setTempEmployeeId(-2);
            preferences.setSignedOut(false);
            view.navigateToChatRoomActivity();
        }
        else{
            // logging in as manager
            preferences.setManagerOrEmployee(0);
            preferences.setupChatRoomDialog(false);
            preferences.setChatRoomName("");
            preferences.setManagerChatRoomName("");
            preferences.setEmployeeId(-2);
            preferences.setSignedOut(false);
            view.navigateToEmployeeListActivity();
        }
    }

    @Override
    public void setupViewForManagerOrEmployee() {
        preferences.setSignedOut(true);
        if(!preferences.isManager()){
            view.updateLoggingInText("You are logging in as employee");
            view.updateSwitchButton("Switch To Manager");
            view.updateResetButton("Reset Employee chat room and create new one");
        }
        else{
            view.updateLoggingInText("You are logging in as manager");
            view.updateSwitchButton("Switch To Employee");
            view.updateResetButton("Reset Manager chat room and create new one");
        }
    }


}
