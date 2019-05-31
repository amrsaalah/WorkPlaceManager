package com.salah.amr.workplace.ChatRoom;

import com.salah.amr.workplace.Model.Message;
import com.salah.amr.workplace.Model.User;

import java.util.List;

/**
 * Created by user on 12/13/2017.
 */

public interface IMessagesAdapter {
    void messageAdded();

    void setMessages(List<Message> messages);
    void setUsers(List<User> users);
}
