package com.salah.amr.workplace.Attendance;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.salah.amr.workplace.Base.BaseReceiver;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.BroadRecModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.MyApp;

import javax.inject.Inject;

public class SalaryAlarmReceiver extends BaseReceiver implements IAttendance.receiver {
    private static final String TAG = "SalaryAlarmReceiver";

    @Inject
    AttendanceReceiverPresenter presenter;
    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: salary alarm reset");

        MyApp.getComponent().newReceiverComponent(new BroadRecModule(this)).inject(this);
        presenter.resetSalary();
    }
}
