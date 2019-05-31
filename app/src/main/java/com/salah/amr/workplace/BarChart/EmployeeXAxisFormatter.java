package com.salah.amr.workplace.BarChart;

import android.util.Log;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.BarLineChartBase;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.salah.amr.workplace.Model.Employee;
import com.salah.amr.workplace.Utils.IAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 11/29/2017.
 */

class EmployeeXAxisFormatter implements IAxisValueFormatter  , FormatterAdapter {

    private static final String TAG = "EmployeeXAxisFormatter";

    private List<Employee> employees;

    @Inject
    public EmployeeXAxisFormatter() {
        employees  = new ArrayList<>();
    }

    @Override
    public String getFormattedValue(float value, AxisBase axis) {
        Log.d(TAG, "getFormattedValue: "+ employees.get((int)value).getName());
        return employees.get((int)value).getName();
    }

    @Override
    public void setEmployees(List<Employee> employeeList) {
        employees = employeeList;
    }

    @Override
    public void addEmployees(Employee employee) {
        employees.add(employee);
    }
}
