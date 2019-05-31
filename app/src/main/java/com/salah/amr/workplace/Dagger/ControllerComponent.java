package com.salah.amr.workplace.Dagger;

import com.salah.amr.workplace.AddImage.AddImageActivity;
import com.salah.amr.workplace.AddImage.GalleryFragment;
import com.salah.amr.workplace.AddImage.PhotoFragment;
import com.salah.amr.workplace.Attendance.AttendanceFragment;
import com.salah.amr.workplace.BarChart.TimesAbsentBarChartFragment;
import com.salah.amr.workplace.BarChart.TimesLateBarChartFragment;
import com.salah.amr.workplace.ChatRoom.Activity.ChatRoomActivity;
import com.salah.amr.workplace.ChatRoom.AnnouncementsFragment;
import com.salah.amr.workplace.ChatRoom.GroupChatFragment;
import com.salah.amr.workplace.ChatRoom.ListDialog.ChatRoomListDialog;
import com.salah.amr.workplace.ChatRoom.LoginFormDialog.ChatRoomLoginFormDialog;
import com.salah.amr.workplace.ChatRoom.SetupDialog.ChatRoomSetupDialog;
import com.salah.amr.workplace.Employee.EmployeeActivity;
import com.salah.amr.workplace.Employee.EmployeeFragment;
import com.salah.amr.workplace.EmployeeList.EmployeeListFragment;
import com.salah.amr.workplace.Login.LoginActivity;
import com.salah.amr.workplace.MyProfile.MyProfileActivity;
import com.salah.amr.workplace.Settings.SettingsFragment;
import com.salah.amr.workplace.Tasks.DatePickerDateDialog;
import com.salah.amr.workplace.Tasks.TasksFragment;
import com.salah.amr.workplace.Tasks.TasksListDialog;
import com.salah.amr.workplace.Tasks.TimePickerDateDialog;


import dagger.Subcomponent;

/**
 * Created by user on 1/26/2018.
 */

@Subcomponent(modules = {ControllerModule.class})
@ControllerScope
public interface ControllerComponent {

    void inject(GalleryFragment target);
    void inject(PhotoFragment target);
    void inject(EmployeeListFragment target);
    void inject(EmployeeFragment target);
    void inject(AttendanceFragment target);
    void inject(TimesAbsentBarChartFragment target);
    void inject(TimesLateBarChartFragment target);
    void inject(TasksFragment target);
    void inject(DatePickerDateDialog target);
    void inject(TimePickerDateDialog target);
    void inject(TasksListDialog target);
    void inject(ChatRoomActivity target);
    void inject(ChatRoomListDialog target);
    void inject(ChatRoomLoginFormDialog target);
    void inject(ChatRoomSetupDialog target);
    void inject(AnnouncementsFragment target);
    void inject(GroupChatFragment target);
    void inject(LoginActivity target);
    void inject(MyProfileActivity target);
    void inject(SettingsFragment target);
    void inject(EmployeeActivity target);

}
