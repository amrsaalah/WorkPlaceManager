package com.salah.amr.workplace.Attendance;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;


public class ResetAttendanceAlarm {
    private static final String TAG = "ResetAttendanceAlarm";
    private static final int durationInMinute = 60*24;

    public ResetAttendanceAlarm(Context context) {
        Log.d(TAG, "ResetAttendanceAlarm: ");

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        while(calendar.getTimeInMillis() <= System.currentTimeMillis()){
            calendar.add(Calendar.MINUTE , durationInMinute);
        }
        Log.d(TAG, "ResetAttendanceAlarm: calendar "+calendar.getTime().toString());
        Log.d(TAG, "ResetAttendanceAlarm: system "+System.currentTimeMillis());
        Intent intent = new Intent(context, AttendanceAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Log.d(TAG, "ResetAttendanceAlarm: alarm timing "+calendar.getTimeInMillis());
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 1000 * 60 * durationInMinute, pendingIntent);
    }
}
