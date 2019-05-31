package com.salah.amr.workplace.Attendance;

import java.util.Calendar;

/**
 * Created by user on 11/28/2017.
 */

public interface IAttendance {
    interface view{
        void showEmployeeList(AttendanceAdapter adapter);
        void showTodayDate(String date);
        void launchAlarm(Calendar calendar);
        void hideBeginningView();
    }

    interface presenter{
        void loadEmployees();
        void setupTodayDate();
        void onCheckBoxClick(int position , int id);
        void setupAlarm();
    }

    interface receiver{

    }

    interface receiverPresenter{
        void resetSalary();
        void resetAttendance();
    }
}
