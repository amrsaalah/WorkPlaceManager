package com.salah.amr.workplace.ChatRoom.LoginFormDialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.salah.amr.workplace.Base.BaseDialog;
import com.salah.amr.workplace.ChatRoom.Activity.ChatRoomActivity;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.EmployeeList.EmployeeListActivity;
import com.salah.amr.workplace.Login.LoginActivity;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;

import javax.inject.Inject;

/**
 * Created by user on 12/7/2017.
 */

public class ChatRoomLoginFormDialog extends BaseDialog implements IChatRoomLoginFormDialog.view {
    private static final String TAG = "ChatRoomLoginFormDialog";


    public interface CallbackToActivity {
        void dialogDismissed(String chatRoomName);
    }

    CallbackToActivity callback;
    EditText editChatRoomName, editChatRoomPassword;
    String name, password;

    @Inject
    ChatRoomLoginFormPresenter presenter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_chat_room_login, null);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        editChatRoomName = v.findViewById(R.id.chat_room_name);
        setRetainInstance(true);
        editChatRoomPassword = v.findViewById(R.id.chat_room_password);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Login into Chat Room")
                .setPositiveButton("Ok", (dialogInterface, i) -> {

                })
                .setNegativeButton("Switch To Manager", (dialogInterface, i) -> {
                    presenter.onSwitchButtonClicked();
                })

                .create();

    }


    @Override
    public void onStart() {
        super.onStart();
        callback = (CallbackToActivity) getActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        final AlertDialog d = (AlertDialog) getDialog();
        d.setCanceledOnTouchOutside(false);
        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            // Prevent dialog close on back press button
            return keyCode == KeyEvent.KEYCODE_BACK;
        });
        if (d != null) {
            Button positiveButton = d.getButton(Dialog.BUTTON_POSITIVE);
            positiveButton.setOnClickListener(v -> {

                name = editChatRoomName.getText().toString();
                password = editChatRoomPassword.getText().toString();
                presenter.login(name, password);
            });
        }
    }

    @Override
    public void showError() {
        Toast.makeText(getActivity(), "Wrong username or password", Toast.LENGTH_LONG).show();
    }

    @Override
    public void dismissDialog() {
        Log.d(TAG, "dismissDialog: ");
        if (callback != null) {
            getDialog().dismiss();
            callback.dialogDismissed(name);
        }
    }

    @Override
    public void showMissingDataError() {
        Toast.makeText(getActivity(), "You have to enter name and password", Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToEmployeeListActivity() {
        Intent intent = new Intent(getActivity(), EmployeeListActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToChatRoomActivity() {
        Intent intent = new Intent(getActivity(), ChatRoomActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);
    }

}
