package com.salah.amr.workplace.MyProfile;

import com.salah.amr.workplace.Utils.SwitchView;

/**
 * Created by user on 12/15/2017.
 */

public interface IMyProfile {
    interface view extends SwitchView {
        void showUserData(String name , String imageURL , String email , String phone);
        void refreshView();
        void navigateToChatRoomActivity();
        void updateSwitchButton(String s);
        void navigateToEmployeeListActivity();
        void navigateToLoginActivity();
    }

    interface presenter{
        void loadUserData();
        void updateUserData(String userName , String email , String phone);
        void onSwitchButtonClicked();
    }
}
