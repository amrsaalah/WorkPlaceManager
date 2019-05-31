package com.salah.amr.workplace.ChatRoom.SetupDialog;

/**
 * Created by user on 12/5/2017.
 */

public interface IChatRoomSetupDialog {

    interface view{
        void showErrorMessage();
        void dismissDialog();
        void showMissingDataError();
    }

    interface presenter{
        void addChatRoom(String name , String password);
        void uploadEmployees();
    }
}
