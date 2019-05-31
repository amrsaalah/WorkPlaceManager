package com.salah.amr.workplace.EmployeeList;


import com.salah.amr.workplace.Base.BaseView;

/**
 * Created by user on 11/24/2017.
 */

public interface IEmployeeList {
    interface view extends BaseView{

        void startEmployeeActivity();
        void showEmployeeList(EmployeeListAdapter adapter);
        void startEmployeeActivityWithExtra(int position);
        void navigateToSettingsActivity();
        void navigateToChatRoomActivity();
        void showManagerOrEmployeeDialog();
        void beginSyncService();
        void showSyncErrorMessage();
        void showSyncAlertDialog();
        void hideBeginningView();
        void navigateToLoginActivity();
    }

    interface presenter{
        void loadEmployees();
        void onFabClicked();
        void onItemClicked(int position);
        void onSettingsButtonClicked();
        void setupManagerOrEmployeeDialog();
        void setupManagerOrEmployeeView();
        void onSyncButtonClicked();
    }

}
