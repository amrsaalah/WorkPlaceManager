package com.salah.amr.workplace.ChatRoom.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.salah.amr.workplace.Base.BaseActivity;
import com.salah.amr.workplace.ChatRoom.AnnouncementsFragment;
import com.salah.amr.workplace.ChatRoom.GroupChatFragment;
import com.salah.amr.workplace.ChatRoom.ListDialog.ChatRoomListDialogPresenter;
import com.salah.amr.workplace.ChatRoom.ListDialog.ChatRoomListDialog;
import com.salah.amr.workplace.ChatRoom.LoginFormDialog.ChatRoomLoginFormDialog;
import com.salah.amr.workplace.ChatRoom.SetupDialog.ChatRoomSetupDialog;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.Login.LoginActivity;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.MyProfile.MyProfileActivity;
import com.salah.amr.workplace.R;
import com.salah.amr.workplace.Utils.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatRoomActivity extends BaseActivity implements IChatRoomActivity.activityView, ChatRoomLoginFormDialog.CallbackToActivity , ChatRoomSetupDialog.ICallbackToActivity {

    @Override
    public void dialogDismissed(String name) {
        Log.d(TAG, "dialogDismissed: ");
        listDialog = ChatRoomListDialog.newInstance(name);
        listDialog.show(getSupportFragmentManager(), TAG);

    }

    @Override
    public void refreshActivity() {
        Intent intent = new Intent(context , ChatRoomActivity.class);
        startActivity(intent);
    }

    private static final String TAG = "ChatRoomActivity";
    Context context = ChatRoomActivity.this;
    EmployeeSharedPreferences employeeSharedPreferences;
    ChatRoomListDialog listDialog;
    ChatRoomListDialogPresenter chatRoomListDialogPresenter;
    TabLayout tabLayout;
    ViewPager viewPager;
    List<Fragment> fragmentList = new ArrayList<>();
    BottomNavigationViewEx bottomNavigationViewEx;
    TextView textUserName;
    CircleImageView userProfileImage;
    ImageView editProfileButton;

    @Inject
    ChatRoomActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        initWidgets();
        setupBottomNavigation();


        presenter.setupViewForManagerOrEmployee();
        presenter.loadUserData();

        AnnouncementsFragment announcementsFragment = new AnnouncementsFragment();
        GroupChatFragment groupChatFragment = new GroupChatFragment();
        fragmentList.add(groupChatFragment);
        fragmentList.add(announcementsFragment);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Group Chat");
        tabLayout.getTabAt(1).setText("Announcements");

        editProfileButton.setOnClickListener(view -> {
            Intent intent = new Intent(context, MyProfileActivity.class);
            startActivity(intent);
        });
    }

    private void initWidgets() {
        bottomNavigationViewEx = findViewById(R.id.bottom_navigation_ex);
        textUserName = findViewById(R.id.user_name);
        userProfileImage = findViewById(R.id.user_image_profile);
        editProfileButton = findViewById(R.id.edit_profile_btn);
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
    }

    private void setupBottomNavigation() {
        Log.d(TAG, "setupBottomNavigation: ");
        BottomNavigationViewHelper.setNavigationView(context, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(4);
        menuItem.setChecked(true);
    }

    @Override
    public void showSetupDialog() {
        Log.d(TAG, "showSetupDialog: ");
        ChatRoomSetupDialog dialog = new ChatRoomSetupDialog();
        dialog.show(getSupportFragmentManager(), TAG);
    }


    @Override
    public void showChatRoomLoginForm() {
        Log.d(TAG, "showChatRoomLoginForm: ");
        ChatRoomLoginFormDialog dialog = new ChatRoomLoginFormDialog();
        dialog.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void refreshView() {
        Log.d(TAG, "refreshView: ");
        Intent intent = new Intent(context, ChatRoomActivity.class);
        startActivity(intent);
    }

    @Override
    public void showUserData(String userName, String imageURL) {
        Log.d(TAG, "showUserData: ");
        textUserName.setText(userName);
        Glide.with(getApplicationContext()).load(imageURL).asBitmap().fitCenter().placeholder(R.drawable.avatar).into(userProfileImage);

    }


    @Override
    public void hideBottomNavigationBar() {
        bottomNavigationViewEx.setVisibility(View.GONE);
        LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT,
                100f
        );

        viewPager.setLayoutParams(param);
    }

    @Override
    public void navigateToLoginActivity() {
        Intent intent =new Intent(context , LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void showListDialog() {
        listDialog.show(getSupportFragmentManager(), TAG);
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
    }

}
