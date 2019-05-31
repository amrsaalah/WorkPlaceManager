package com.salah.amr.workplace.Attendance;

import android.util.Log;

import com.salah.amr.workplace.Base.BaseReceiver;
import com.salah.amr.workplace.Model.Employee;
import com.salah.amr.workplace.Model.EmployeeDatabase;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;

import javax.inject.Inject;

/**
 * Created by user on 1/28/2018.
 */

public class AttendanceReceiverPresenter implements IAttendance.receiverPresenter {

    private static final String TAG = "AttendanceReceiverPre";

    private IAttendance.receiver receiver;
    private EmployeeSharedPreferences preferences;
    private EmployeeDatabase employeeDatabase;

    @Inject
    public AttendanceReceiverPresenter(BaseReceiver receiver , EmployeeSharedPreferences preferences , EmployeeDatabase employeeDatabase){
        this.receiver = (IAttendance.receiver) receiver;
        this.preferences = preferences;
        this.employeeDatabase = employeeDatabase;
    }

    @Override
    public void resetAttendance() {
        Log.d(TAG, "resetAttendance: ");
        for(int i =0 ; i<employeeDatabase.getEmployees().size() ; i++){
            Employee employee = employeeDatabase.getEmployees().get(i);
            employee.updateEmployeeAttendance(true , false , false);
            employeeDatabase.updateEmployee(employee);
        }
    }

    @Override
    public void resetSalary() {
        Log.d(TAG, "resetSalary: ");
        for(int i = 0 ; i<employeeDatabase.getEmployees().size() ; i++){
            Employee employee = employeeDatabase.getEmployees().get(i);
            Log.d(TAG, "resetSalary: "+employee);
            employee.setLastSalary(calculateCurrentSalary(i));
            employee.setLastTimesLate(employee.getTimesLate());
            employee.setLastTimesAbsent(employee.getTimesAbsent());
            employee.setCurrentSalary(employee.getSalary());
            employeeDatabase.updateEmployee(employee);
            Log.d(TAG, "resetSalary: "+employeeDatabase.getEmployees().get(i));
        }
        preferences.setSalaryAlarm(false);
    }


    private double calculateCurrentSalary(int position) {
        Log.d(TAG, "calculateCurrentSalary: ");
        double salary = employeeDatabase.getEmployees().get(position).getSalary();
        int timesLate = employeeDatabase.getEmployees().get(position).getTimesLate();
        int timesAbsent = employeeDatabase.getEmployees().get(position).getTimesAbsent();
        int lastTimesLate  = employeeDatabase.getEmployees().get(position).getLastTimesLate();
        int lastTimesAbsent = employeeDatabase.getEmployees().get(position).getLastTimesAbsent();
        Log.d(TAG, "calculateCurrentSalary: " + preferences + " " + preferences.getLatePenalty());
        double latePenalty = Double.parseDouble(preferences.getLatePenalty());
        double absentPenalty = Double.parseDouble(preferences.getAbsentPenalty());
        Log.d(TAG, "calculateCurrentSalary: salary " + salary + " timesLate " + timesLate + " timesAbsent " + timesAbsent + " latePenalty " + latePenalty + " absentPenalty" + absentPenalty);
        return salary - (latePenalty * (timesLate  - lastTimesLate )) - (absentPenalty * (timesAbsent -  lastTimesAbsent ));
    }
}
