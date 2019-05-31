package com.salah.amr.workplace.Dagger;

import com.salah.amr.workplace.Attendance.AttendanceAlarmReceiver;
import com.salah.amr.workplace.Attendance.SalaryAlarmReceiver;

import dagger.Subcomponent;

/**
 * Created by user on 1/28/2018.
 */


@Subcomponent(modules = {BroadRecModule.class})
@BroadRecScope
public interface BroadRecComponent {

    void inject(SalaryAlarmReceiver target);
    void inject(AttendanceAlarmReceiver target);
}
