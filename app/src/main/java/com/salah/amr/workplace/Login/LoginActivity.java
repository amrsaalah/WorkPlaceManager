package com.salah.amr.workplace.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.salah.amr.workplace.Base.BaseActivity;
import com.salah.amr.workplace.ChatRoom.Activity.ChatRoomActivity;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.EmployeeList.EmployeeListActivity;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;

import javax.inject.Inject;

public class LoginActivity extends BaseActivity implements ILogin.view {
    private static final String TAG = "LoginActivity";

    EditText editUsername, editPassword;
    Button loginButton , switchButton;
    String username , password;
    TextView loggingInAsManagerEmployee , textResetButton;
    Context context = LoginActivity.this;

    @Inject
    LoginPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        loginButton = findViewById(R.id.login_button);
        editUsername = findViewById(R.id.input_email);
        editPassword = findViewById(R.id.input_password);
        loggingInAsManagerEmployee = findViewById(R.id.text_logging_in_as_employeeManager);
        textResetButton = findViewById(R.id.text_forgot_password);
        switchButton = findViewById(R.id.switch_btn);


        editUsername.setEnabled(false);
        presenter.fillEditChatRoomName();
        presenter.setupViewForManagerOrEmployee();
        loginButton.setOnClickListener(view -> {
            username = editUsername.getText().toString();
            password = editPassword.getText().toString();
            presenter.onLoginButtonClicked(username , password);
        });

        textResetButton.setOnClickListener(view -> {
            presenter.onForgotButtonClicked();
        });

        switchButton.setOnClickListener(view -> {
            presenter.onSwitchButtonClicked();
        });

    }

    @Override
    public void showErrorMessage() {
        Toast.makeText(context , "Wrong Username or password" , Toast.LENGTH_LONG).show();
    }

    @Override
    public void showWrongChatRoomMessage(String message) {
        Toast.makeText(context , message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void navigateToEmployeeListActivity() {
        Intent intent = new Intent(context , EmployeeListActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToChatRoomActivity() {
        Intent intent = new Intent(context , ChatRoomActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToLoginActivity() {
        Log.d(TAG, "navigateToLoginActivity: ");
        Intent intent = new Intent(context , LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateEditChatRoomName(String name) {
        editUsername.setText(name);
    }

    @Override
    public void updateLoggingInText(String message) {
        Log.d(TAG, "updateLoggingInText: "+message);
        loggingInAsManagerEmployee.setText(message);
    }

    @Override
    public void updateSwitchButton(String message) {
        switchButton.setText(message);
    }

    @Override
    public void updateResetButton(String message) {
        textResetButton.setText(message);
    }
}
