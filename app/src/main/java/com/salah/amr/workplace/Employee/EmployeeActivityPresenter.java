package com.salah.amr.workplace.Employee;

import com.salah.amr.workplace.Base.BaseActivity;
import com.salah.amr.workplace.Model.EmployeeDatabase;

import javax.inject.Inject;

/**
 * Created by user on 1/28/2018.
 */

public class EmployeeActivityPresenter implements IEmployee.activityPresenter {

    private  EmployeeDatabase employeeDatabase;
    private IEmployee.activityView activityView;

    @Inject
    public EmployeeActivityPresenter(BaseActivity activityView , EmployeeDatabase employeeDatabase) {
        this.activityView = (IEmployee.activityView) activityView;
        this.employeeDatabase = employeeDatabase;
    }

    @Override
    public int getNumberOfEmployees() {
        return employeeDatabase.getEmployees().size();
    }

}
