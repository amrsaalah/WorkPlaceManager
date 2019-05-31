package com.salah.amr.workplace.Employee;

import java.util.Calendar;

/**
 * Created by user on 11/24/2017.
 */

public interface IEmployee {
    interface view{
        void showMissingDataError();
        void returnToParentActivity();
        void fillEmployeeData(String name , String email , String phone,String imageURL , String salary , String timesLate , String timesAbsent , String currentSalary , String lastSalary);
        void navigateToEmployeeListActivity();
        void startPhoneIntent(String phone);
        void startEmailIntent(String email);
        void navigateToAddImageActivity(int id);
        void setMonthlyOrYearly(int flag);
        void showChangePhotoError();
    }

    interface presenter{
        void onPhoneButtonClicked(int position);
        void onEmailButtonClicked(int position);
        void onSaveChangesClicked(String name , String email , String phone , String salary , int position);
        void onDeleteButtonClicked(int position);
        void onBackButtonClicked();
        void onChangePhotoClicked(int position);
        void loadEmployee(int position);
        void checkMonthlyOrYearly();

    }

    interface activityView{

    }

    interface activityPresenter{
        int getNumberOfEmployees();
    }

}
