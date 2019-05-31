package com.salah.amr.workplace.ChatRoom.Activity;

/**
 * Created by user on 12/5/2017.
 */

public interface IChatRoomActivity {

    interface presenter {
        void setupViewForManagerOrEmployee();
        void loadUserData();
        void onEditProfileButtonClicked();
    }

    interface activityView {
        void showSetupDialog();
        void showListDialog();
        void showChatRoomLoginForm();
        void refreshView();
        void showUserData(String userName , String imageURL);
        void hideBottomNavigationBar();
        void navigateToLoginActivity();
    }
}
