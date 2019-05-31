package com.salah.amr.workplace.ChatRoom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.salah.amr.workplace.Base.BaseFragment;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;

import javax.inject.Inject;

/**
 * Created by user on 11/23/2017.
 */

public class GroupChatFragment extends BaseFragment implements IChatRoom.view , MessagesAdapter.ICallbackMessagesSize{
    private static final String TAG = "GroupChatFragment";

    @Override
    public void callbackMessageSize(int size) {
        Log.d(TAG, "callbackMessageSize: "+size);
        recyclerView.smoothScrollToPosition(size - 1);
        messagesListSize = size;
    }

    RecyclerView recyclerView;
    EditText editMessageText;
    ImageView emoticonsImage , sendButton;
    String text;
    int messagesListSize ;

    @Inject
    ChatRoomPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View v;
        v = inflater.inflate(R.layout.fragment_groupchat , container , false);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        initWidgets(v);

        presenter.loadUser();
        presenter.loadMessages(0);

        sendButton.setOnClickListener(view -> {
            Log.d(TAG, "onCreateView: button send clicked");
            text = editMessageText.getText().toString();
            presenter.onSendButtonClicked(text , 0);
            editMessageText.setText("");
        });

        return v;
    }

    private void initWidgets(View v){
        recyclerView = v.findViewById(R.id.recycler_view);
        editMessageText = v.findViewById(R.id.message_text);
        sendButton =  v.findViewById(R.id.message_send);
    }

    @Override
    public void showMessagesList(MessagesAdapter adapter) {
        Log.d(TAG, "showMessagesList: ");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        if(messagesListSize > 0){
            recyclerView.smoothScrollToPosition(messagesListSize - 1);
        }
    }

    @Override
    public void hideSendLayout() {

    }

}
