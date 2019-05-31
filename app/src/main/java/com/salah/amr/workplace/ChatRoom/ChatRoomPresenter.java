package com.salah.amr.workplace.ChatRoom;

import android.util.Log;

import com.salah.amr.workplace.Base.BaseView;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.Model.Message;
import com.salah.amr.workplace.Model.User;
import com.salah.amr.workplace.Model.UserDatabase;

import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 12/12/2017.
 */

public class ChatRoomPresenter implements IChatRoom.presenter  , UserDatabase.ICallbackGetMessages  , UserDatabase.ICallbackSendMessage  {
    private static final String TAG = "ChatRoomPresenter";

    @Override
    public void callbackGetMessages(List<Message> messages , List<User> users) {
        Log.d(TAG, "callbackGetMessages: "+messages.size() + users.size());

        if(view != null && adapter != null){
            if(messages.size() == users.size()){
                adapter.setMessages(messages);
                adapter.setUsers(users);
                view.showMessagesList(adapter);
            }
        }
    }

    @Override
    public void callbackSendMessage(boolean flag) {
        Log.d(TAG, "callbackSendMessage: ");
        if (flag) {
            if(adapter != null)
                adapter.messageAdded();
        }
    }


    private MessagesAdapter adapter;
    private IChatRoom.view view;
    private EmployeeSharedPreferences preferences;
    UserDatabase userDatabase;
    int id;
    User user;
    Message lastSentMessage;

    @Inject
    public ChatRoomPresenter(BaseView view, EmployeeSharedPreferences preferences, MessagesAdapter adapter , UserDatabase userDatabase) {
        this.adapter = adapter;
        this.view = (IChatRoom.view) view;
        this.preferences = preferences;
        this.userDatabase = userDatabase;
    }

    @Override
    public void loadUser() {
        Log.d(TAG, "loadUser: ");
        if(!preferences.isManager()){
            view.hideSendLayout();
        }
    }

    @Override
    public void onSendButtonClicked(String text , int flag) {
        Log.d(TAG, "onSendButtonClicked: ");
        Message message;
        Calendar calendar = Calendar.getInstance();
        message = new Message(text, preferences.getEmployeeId(), calendar.getTimeInMillis());
        userDatabase.sendMessage(message, preferences.getChatRoomName() ,flag , this);
        lastSentMessage = message;
    }

    @Override
    public void loadMessages(int flag) {
        Log.d(TAG, "loadMessages: ");
        userDatabase.getMessages(preferences.getChatRoomName(), flag, this);
    }


}
