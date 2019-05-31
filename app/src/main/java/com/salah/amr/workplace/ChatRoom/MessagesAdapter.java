package com.salah.amr.workplace.ChatRoom;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.salah.amr.workplace.Base.BaseListener;
import com.salah.amr.workplace.Model.Message;
import com.salah.amr.workplace.Model.User;
import com.salah.amr.workplace.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by user on 12/12/2017.
 */

public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.MessageHolder> implements IMessagesAdapter {
    private static final String TAG = "MessagesAdapter";

    public interface ICallbackMessagesSize extends BaseListener {
        void callbackMessageSize(int size);
    }

    @Inject
    public MessagesAdapter(BaseListener callbackMessagesSize) {
        this.callbackMessagesSize = (ICallbackMessagesSize) callbackMessagesSize;
    }

    private ICallbackMessagesSize callbackMessagesSize;
    private List<Message> messages = new ArrayList<>();
    private List<User> users = new ArrayList<>();
    private Context context;

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
        return new MessageHolder(v);
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: " + users.size() + messages.size());
        holder.bindMessage(users.get(position), messages.get(position));
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }


    @Override
    public void messageAdded() {
        notifyDataSetChanged();
        callbackMessagesSize.callbackMessageSize(messages.size());
    }

    @Override
    public void setMessages(List<Message> messages) {
        this.messages = messages;
        callbackMessagesSize.callbackMessageSize(messages.size());
    }

    @Override
    public void setUsers(List<User> users) {
        this.users = users;
    }


    public class MessageHolder extends RecyclerView.ViewHolder {
        TextView textMessage, textTimeStamp, textUserName;
        CircleImageView imageView;

        public MessageHolder(View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.item_message_text);
            textTimeStamp = itemView.findViewById(R.id.item_message_timestamp);
            imageView = itemView.findViewById(R.id.item_message_image);
            textUserName = itemView.findViewById(R.id.item_message_userName);
        }

        public void bindMessage(User user, Message message) {
            Log.d(TAG, "bindMessage: ");
            Log.d(TAG, "bindMessage: message" + message + "user " + user);

            textUserName.setText(user.getName());
            textMessage.setText(message.getText());

            Glide.with(itemView.getContext()).load(user.getImageURL()).asBitmap().fitCenter().placeholder(R.drawable.avatar).into(imageView);


            Calendar calendar = Calendar.getInstance();
            Calendar today = Calendar.getInstance();
            calendar.setTimeInMillis(message.getTimeStamp());
            Date date = calendar.getTime();
            if (calendar.get(Calendar.DAY_OF_MONTH) == today.get(Calendar.DAY_OF_MONTH)) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
                String dateText = dateFormat.format(date);
                textTimeStamp.setText(dateText);
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM HH:mm");
                String dateText = dateFormat.format(date);
                textTimeStamp.setText(dateText);
            }
        }

    }


}
