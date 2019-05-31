package com.salah.amr.workplace.ChatRoom;

/**
 * Created by user on 12/12/2017.
 */

public interface IChatRoom {

    interface view{
        void showMessagesList(MessagesAdapter adapter);
        void hideSendLayout();
    }

    interface presenter{
        void loadUser();
        void onSendButtonClicked(String text , int flag);
        void loadMessages(int flag);
    }
}
