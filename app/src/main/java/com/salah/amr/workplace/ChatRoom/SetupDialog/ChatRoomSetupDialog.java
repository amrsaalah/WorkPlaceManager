package com.salah.amr.workplace.ChatRoom.SetupDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.salah.amr.workplace.Base.BaseDialog;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.EmployeeList.EmployeeListActivity;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;

import javax.inject.Inject;

/**
 * Created by user on 12/5/2017.
 */

public class ChatRoomSetupDialog extends BaseDialog implements IChatRoomSetupDialog.view {
    private static final String TAG = "ChatRoomSetupDialog";
    public interface ICallbackToActivity{
        void refreshActivity();
    }


    public static final String EXTRA_CHAT_ROOM_NAME = "extra_chat_room_name";
    public static final String EXTRA_CHAT_ROOM_PASSWORD = "extra_chat_room_password";
    public static final int REQUEST_CHAT_ROOM_SETUP = 150;

    ICallbackToActivity callbackToActivity;
    EditText editChatRoomName , editChatRoomPassword;
    String name , password;

    @Inject
    ChatRoomSetupDialogPresenter chatRoomSetupDialogPresenter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_chat_room_setup  , null);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        Log.d(TAG, "onCreateDialog: ");
        setRetainInstance(true);
        editChatRoomName = v.findViewById(R.id.chat_room_name);
        editChatRoomPassword = v.findViewById(R.id.chat_room_password);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Setting Chat Room")
                .setNegativeButton("Cancel" ,(dialogInterface, i) -> {
                    Intent intent = new Intent(getActivity() , EmployeeListActivity.class);
                    startActivity(intent);
                })
                .setPositiveButton("Ok" , (dialogInterface, i) -> {

                })
                .create();
    }

    @Override
    public void onStart() {
        super.onStart();
        callbackToActivity = (ICallbackToActivity) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog d = (AlertDialog)getDialog();

        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            // Prevent dialog close on back press button
            return keyCode == KeyEvent.KEYCODE_BACK;
        });
        if(d != null)
        {
            Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {

                name=  editChatRoomName.getText().toString();
                password= editChatRoomPassword.getText().toString();
                Log.d(TAG, "onCreateDialog: setting password and name for chat room");
                chatRoomSetupDialogPresenter.addChatRoom(name , password);
            });
        }
    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(getActivity() , "error" , Toast.LENGTH_LONG).show();
    }

    @Override
    public void dismissDialog() {
        chatRoomSetupDialogPresenter.uploadEmployees();
        dismiss();
        callbackToActivity.refreshActivity();
    }

    @Override
    public void showMissingDataError() {
        Toast.makeText(getActivity()  , "You have to enter name and password" , Toast.LENGTH_LONG).show();
    }
}
