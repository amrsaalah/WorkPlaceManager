package com.salah.amr.workplace.MyProfile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.salah.amr.workplace.AddImage.AddImageActivity;
import com.salah.amr.workplace.Base.BaseActivity;
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

import de.hdodenhof.circleimageview.CircleImageView;

public class MyProfileActivity extends BaseActivity implements IMyProfile.view {
    private static final String TAG = "MyProfileActivity";


    Context context = MyProfileActivity.this;
    ImageView backButton, saveButton;
    EditText editName, editEmail, editPhone;
    CircleImageView profileImage;
    TextView changePhotoButton;
    Button switchButton;
    String userName , email , phone;

    @Inject
    MyProfilePresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        initView();
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        presenter.loadUserData();

        backButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, ChatRoomActivity.class);
            startActivity(intent);
        });
        saveButton.setOnClickListener(view -> {
            userName = editName.getText().toString();
            email =  editEmail.getText().toString();
            phone = editPhone.getText().toString();
            presenter.updateUserData(userName , email , phone);
        });

        changePhotoButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, AddImageActivity.class);
            startActivity(intent);
        });

        switchButton.setOnClickListener(view -> {
            presenter.onSwitchButtonClicked();
        });
    }

    private void initView() {
        Log.d(TAG, "initView: ");
        backButton = findViewById(R.id.back_navigation);
        saveButton = findViewById(R.id.btn_save_changes);
        editName = findViewById(R.id.edit_employee_name);
        editEmail = findViewById(R.id.edit_employee_email);
        editPhone = findViewById(R.id.edit_employee_phone);
        profileImage = findViewById(R.id.image_employee);
        changePhotoButton = findViewById(R.id.text_change_photo);
        switchButton = findViewById(R.id.switch_btn);
    }

    @Override
    public void showUserData(String name, String imageURL , String email , String phone) {
        Log.d(TAG, "showUserData: ");
        editName.setText(name);
        editEmail.setText(email);
        editPhone.setText(phone);

        Glide.with(context).load(imageURL).asBitmap().fitCenter().placeholder(R.drawable.avatar).into(profileImage);
    }

    @Override
    public void refreshView() {
        Log.d(TAG, "refreshView: ");
        Intent intent = new Intent(context, MyProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToChatRoomActivity() {
        Log.d(TAG, "navigateToChatRoomActivity: ");
        Intent intent = new Intent(context , ChatRoomActivity.class);
        startActivity(intent);
    }

    @Override
    public void updateSwitchButton(String s) {
        Log.d(TAG, "updateSwitchButton: ");
        switchButton.setText(s);
    }

    @Override
    public void navigateToEmployeeListActivity() {
        Log.d(TAG, "navigateToEmployeeListActivity: ");
        Intent intent = new Intent(context , EmployeeListActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent  = new Intent(context , LoginActivity.class);
        startActivity(intent);
    }
}
