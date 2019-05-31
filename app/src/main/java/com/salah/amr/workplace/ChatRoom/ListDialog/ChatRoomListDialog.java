package com.salah.amr.workplace.ChatRoom.ListDialog;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.salah.amr.workplace.Base.BaseDialog;
import com.salah.amr.workplace.ChatRoom.Activity.ChatRoomActivity;
import com.salah.amr.workplace.ChatRoom.Activity.IChatRoomActivity;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.EmployeeList.EmployeeListActivity;
import com.salah.amr.workplace.Login.LoginActivity;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 12/1/2017.
 */

public class ChatRoomListDialog extends BaseDialog implements IChatRoomListDialog.dialog{

    private static final String ARG_CHAT_ROOM_NAME = "arg_chat_room_name";

    private static final String TAG = "ChatRoomListDialog";
    List<String> names  = new ArrayList<>();
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    IChatRoomActivity.activityView activityView;
    View v  =  null;
    String chatRoomName;

    @Inject
    ChatRoomListDialogPresenter presenter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog: ");
        setRetainInstance(true);

        chatRoomName = getArguments().getString(ARG_CHAT_ROOM_NAME);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_list , null);

        presenter.loadEmployeesForDialogList(chatRoomName);

        listView = v.findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter<>(getActivity() , android.R.layout.simple_list_item_1 , names);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {;
            presenter.saveEmployeeId(i);
        });
        return new AlertDialog.Builder(getActivity())
               .setView(v)
                .setNegativeButton("Switch To Manager" , (dialogInterface, i) -> {
                    presenter.onSwitchButtonClicked();
                })
                .setCancelable(false)
               .setTitle("Employee Names")
               .create();
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCanceledOnTouchOutside(false);
        activityView = (IChatRoomActivity.activityView) getActivity();
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            // Prevent dialog close on back press button
            return keyCode == KeyEvent.KEYCODE_BACK;
        });
    }


    @Override
    public void setEmployeeNames(List<String> names) {
        Log.d(TAG, "setEmployeeNames: "+names);
        this.names  = names;
        for(int i = 0 ;i<names.size() ; i++){
            arrayAdapter.insert(names.get(i) , i);
        }
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void dismissDialog() {
        Log.d(TAG, "dismissDialog: ");
        getDialog().dismiss();
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(getActivity() , LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToEmployeeListActivity() {
        Intent intent = new Intent(getActivity() , EmployeeListActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToChatRoomActivity() {
        Intent intent = new Intent(getActivity() , ChatRoomActivity.class);
        startActivity(intent);
    }


    @Override
    public void showErrorMessage() {
        Log.d(TAG, "showErrorMessage: ");
        Toast.makeText(getActivity() , "This account already been taken" , Toast.LENGTH_LONG).show();
    }


    public static ChatRoomListDialog newInstance(String chatRoomName){
        Bundle args = new Bundle();
        args.putString(ARG_CHAT_ROOM_NAME , chatRoomName);
        ChatRoomListDialog dialog = new ChatRoomListDialog();
        dialog.setArguments(args);
        return dialog;
    }

}
