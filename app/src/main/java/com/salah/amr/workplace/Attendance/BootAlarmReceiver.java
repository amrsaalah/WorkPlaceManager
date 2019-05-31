package com.salah.amr.workplace.Attendance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.salah.amr.workplace.Model.EmployeeSharedPreferences;

import java.util.Calendar;

public class BootAlarmReceiver extends BroadcastReceiver {
    private static final String TAG = "BootAlarmReceiver";
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: BOOT RECEIVER WORKING");
        ResetAttendanceAlarm resetAttendanceAlarm = new ResetAttendanceAlarm(context);
        EmployeeSharedPreferences preferences = new EmployeeSharedPreferences(context);
        preferences.setSalaryAlarm(false);
    }
}
