package com.salah.amr.workplace.Attendance;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.salah.amr.workplace.Base.BaseReceiver;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.BroadRecModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.MyApp;

import javax.inject.Inject;

public class AttendanceAlarmReceiver extends BaseReceiver implements IAttendance.receiver {

    private static final String TAG = "AttendanceAlarmReceiver";

    @Inject
     AttendanceReceiverPresenter presenter;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: ");
        Toast.makeText(context , "Attendance Reset " , Toast.LENGTH_LONG).show();
        MyApp.getComponent().newReceiverComponent(new BroadRecModule(this)).inject(this);
        presenter.resetAttendance();
    }
}
