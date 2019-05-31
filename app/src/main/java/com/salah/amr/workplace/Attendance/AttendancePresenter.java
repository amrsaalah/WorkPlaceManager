package com.salah.amr.workplace.Attendance;

import android.util.Log;

import com.salah.amr.workplace.Base.BaseView;
import com.salah.amr.workplace.Model.Employee;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.Model.EmployeeDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 11/28/2017.
 */

public class AttendancePresenter implements IAttendance.presenter {
    private static final String TAG = "AttendancePresenter";
    private IAttendance.view view;
    private AttendanceAdapter iAdapter;
    private EmployeeDatabase employeeDatabase;
    Employee employee;
    private EmployeeSharedPreferences preferences;

    @Inject
    public AttendancePresenter(BaseView view , AttendanceAdapter iAdapter , EmployeeSharedPreferences preferences , EmployeeDatabase employeeDatabase ) {
        this.preferences = preferences;
        this.view = (IAttendance.view) view;
        this.iAdapter = iAdapter;
        this.employeeDatabase = employeeDatabase;
    }


    @Override
    public void loadEmployees() {
        Log.d(TAG, "loadEmployees: ");
        List<Employee> employeeList  = employeeDatabase.getEmployees();
        if(employeeList.size() > 0){
            view.hideBeginningView();
        }
        iAdapter.setEmployees(employeeList);
        view.showEmployeeList(iAdapter);
    }


    @Override
    public void setupTodayDate() {
        Log.d(TAG, "setupTodayDate: ");
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        Date date = new GregorianCalendar(year,month,day).getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(date);
        view.showTodayDate(formattedDate);
    }

    @Override
    public void onCheckBoxClick(int position , int id) {
         employee = employeeDatabase.getEmployees().get(position);
        switch(id){
            case 0 :
                if(employee.isAbsentCheck()){
                    employee.setTimesAbsent(employee.getTimesAbsent()-1);
                }
                if(employee.isLateCheck()){
                    employee.setTimesLate(employee.getTimesLate()-1);
                }
                employee.updateEmployeeAttendance(true  , false , false);
                break;
            case 1:
                if(employee.isAbsentCheck()){
                    employee.setTimesAbsent(employee.getTimesAbsent()-1);
                }
                employee.setTimesLate(employee.getTimesLate()+1);
                employee.updateEmployeeAttendance(false  , true , false);

                break;
            case 2:
                if(employee.isLateCheck()){
                    employee.setTimesLate(employee.getTimesLate()-1);
                }
                employee.setTimesAbsent(employee.getTimesAbsent()+1);
                employee.updateEmployeeAttendance(false  , false , true);
                break;

        }
        employeeDatabase.updateEmployee(employee);
    }


    @Override
    public void setupAlarm() {
        Calendar calendar;
        Log.d(TAG, "setupAlarm: " + preferences.getSalaryType() + preferences.getSalaryAlarm());
        if (!preferences.getSalaryAlarm()) {
            if (preferences.getSalaryType() == "0") {
                Log.d(TAG, "setupAlarm: month");
                calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                Log.d(TAG, "setupAlarm: date of alarm " + calendar.getTime().toString());
                preferences.setSalaryAlarm(true);
                view.launchAlarm(calendar);
            } else {
                Log.d(TAG, "setupAlarm: year");
                calendar = Calendar.getInstance();
                calendar.set(Calendar.DAY_OF_YEAR, calendar.getActualMaximum(Calendar.DAY_OF_YEAR));
                calendar.set(Calendar.HOUR_OF_DAY, 23);
                calendar.set(Calendar.MINUTE, 59);
                calendar.set(Calendar.SECOND, 59);
                Log.d(TAG, "setupAlarm: date of alarm " + calendar.getTime().toString());
                preferences.setSalaryAlarm(true);
                view.launchAlarm(calendar);
            }
        }
    }



}
