package com.salah.amr.workplace.ChatRoom.LoginFormDialog;

import com.salah.amr.workplace.Utils.SwitchView;

/**
 * Created by user on 12/7/2017.
 */

public interface IChatRoomLoginFormDialog {

    interface view extends SwitchView {
        void showError();
        void dismissDialog();
        void showMissingDataError();
    }

    interface presenter{
        void login(String name , String password);
        void onSwitchButtonClicked();
    }

}
