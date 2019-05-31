package com.salah.amr.workplace.Employee;

import android.util.Log;

import com.salah.amr.workplace.Base.BaseView;
import com.salah.amr.workplace.Model.Employee;
import com.salah.amr.workplace.Model.EmployeeDatabase;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.Model.User;
import com.salah.amr.workplace.Model.UserDatabase;

import java.text.NumberFormat;

import javax.inject.Inject;

/**
 * Created by user on 11/24/2017.
 */

public class EmployeePresenter implements IEmployee.presenter {

    private static final String TAG = "EmployeePresenter";
    private EmployeeDatabase employeeDatabase;
    private IEmployee.view view;
    private EmployeeSharedPreferences preferences;
    private IEmployee.activityView activityView;
    private UserDatabase userDatabase;

    @Inject
    public EmployeePresenter(BaseView view, EmployeeSharedPreferences preferences , EmployeeDatabase employeeDatabase , UserDatabase userDatabase) {
        this.view = (IEmployee.view) view;
        this.preferences = preferences;
        this.employeeDatabase = employeeDatabase;
        this.userDatabase = userDatabase;

        Log.d(TAG, "EmployeePresenter: employeedatabase "+employeeDatabase);
        Log.d(TAG, "EmployeePresenter: userdatabase "+userDatabase);
    }



    @Override
    public void onPhoneButtonClicked(int position) {
        Log.d(TAG, "onPhoneButtonClicked: ");
        String phone = employeeDatabase.getEmployees().get(position).getPhone();
        view.startPhoneIntent(phone);
    }

    @Override
    public void onEmailButtonClicked(int position) {
        Log.d(TAG, "onEmailButtonClicked: ");
        String email = employeeDatabase.getEmployees().get(position).getEmail();
        view.startEmailIntent(email);
    }

    @Override
    public void onSaveChangesClicked(String name, String email, String phone, String salary, int position) {
        Log.d(TAG, "onSaveChangesClicked: salary is  " + salary);
        if (position == -1) {
            if (name.isEmpty() || salary.isEmpty()) {
                view.showMissingDataError();
            } else {
                Employee employee = new Employee(name, email, phone, Double.parseDouble(salary));
                Log.d(TAG, "onSaveChangesClicked: "+employee);
                employeeDatabase.addEmployee(employee);

                if (preferences.isSetupChatRoomDialog()) {
                    Employee lastEmployee = employeeDatabase.getEmployees().get(employeeDatabase.getEmployees().size() - 1);
                    User user = new User(lastEmployee.getId() , lastEmployee.getName() , "" ,lastEmployee.getEmail() , lastEmployee.getPhone() );
                    Log.d(TAG, "onSaveChangesClicked: "+user);
                    userDatabase.insertEmployee(preferences.getChatRoomName() , lastEmployee.getId() , user);
                }

                view.navigateToEmployeeListActivity();
            }
        } else {
            if (name.isEmpty() || salary.isEmpty()) {
                view.showMissingDataError();
            } else {
                Employee employee = employeeDatabase.getEmployees().get(position);
                employee.updateEmployeeData(name, email, phone, Double.parseDouble(salary));
                employeeDatabase.updateEmployee(employee);
                view.navigateToEmployeeListActivity();
            }
        }
    }

    @Override
    public void onDeleteButtonClicked(int position) {
        Log.d(TAG, "onDeleteButtonClicked: ");
        if (position != -1) {
            Employee employee = employeeDatabase.getEmployees().get(position);
            employeeDatabase.deleteEmployee(position);

            if(preferences.isSetupChatRoomDialog()){
                Log.d(TAG, "onDeleteButtonClicked: mark deleted");
                userDatabase.removeEmployee(preferences.getChatRoomName() , employee.getId());
            }

        }
        view.navigateToEmployeeListActivity();


    }

    @Override
    public void onBackButtonClicked() {
        Log.d(TAG, "onBackButtonClicked: ");
        view.returnToParentActivity();
    }

    @Override
    public void onChangePhotoClicked(int position) {
        Log.d(TAG, "onChangePhotoClicked: ");
        if (position == -1) {
            view.showChangePhotoError();
        } else {
            int id = employeeDatabase.getEmployees().get(position).getId();
            view.navigateToAddImageActivity(id);
        }
    }

    @Override
    public void loadEmployee(int position) {
        if (position != -1) {
            NumberFormat format = NumberFormat.getCurrencyInstance();
            format.setMaximumFractionDigits(0);
            Employee employee = employeeDatabase.getEmployees().get(position);
            employee.setCurrentSalary(calculateCurrentSalary(position));
            employeeDatabase.updateEmployee(employee);
            view.fillEmployeeData(employee.getName(), employee.getEmail(), employee.getPhone(),
                    employee.getImageURL(), String.valueOf((int) employee.getSalary()),
                    String.valueOf(employee.getTimesLate()),
                    String.valueOf(employee.getTimesAbsent()), format.format(employee.getCurrentSalary()),
                    format.format(employee.getLastSalary()));
        }
    }


    @Override
    public void checkMonthlyOrYearly() {
        Log.d(TAG, "checkMonthlyOrYearly: " + preferences.getSalaryType());
        if (preferences.getSalaryType().equals("0")) {
            view.setMonthlyOrYearly(0);
        } else {
            view.setMonthlyOrYearly(1);
        }
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
