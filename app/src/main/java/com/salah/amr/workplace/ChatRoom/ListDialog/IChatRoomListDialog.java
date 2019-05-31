package com.salah.amr.workplace.ChatRoom.ListDialog;

import com.salah.amr.workplace.Utils.SwitchView;

import java.util.List;

/**
 * Created by user on 12/8/2017.
 */

public interface IChatRoomListDialog {

    interface presenter{
        void loadEmployeesForDialogList(String name);
        void saveEmployeeId(int position);
        void onSwitchButtonClicked();
    }

    interface dialog extends SwitchView {
        void showErrorMessage();
        void setEmployeeNames(List<String> names);
        void dismissDialog();
        void navigateToLoginActivity();
        void navigateToEmployeeListActivity();
    }
}
