package com.salah.amr.workplace.Login;

import com.salah.amr.workplace.Utils.SwitchView;

/**
 * Created by user on 1/11/2018.
 */

public interface ILogin {
    interface view extends SwitchView {
        void showErrorMessage();
        void showWrongChatRoomMessage(String message);
        void navigateToEmployeeListActivity();
        void navigateToChatRoomActivity();
        void updateEditChatRoomName(String name);
        void updateLoggingInText(String message);
        void updateSwitchButton(String message);
        void updateResetButton(String message);
    }

    interface presenter{
        void onLoginButtonClicked(String name , String password);
        void fillEditChatRoomName();
        void onSwitchButtonClicked();
        void onForgotButtonClicked();
        void setupViewForManagerOrEmployee();
    }
}
