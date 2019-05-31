package com.salah.amr.workplace.BarChart;

import android.util.Log;

import com.salah.amr.workplace.Base.BaseView;
import com.salah.amr.workplace.Model.EmployeeDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 11/29/2017.
 */

public class BarChartPresenter implements IBarChart.presenter {

    private static final String TAG = "BarChartPresenter";

    private IBarChart.view view;
    private EmployeeXAxisFormatter iAdapter;
    private EmployeeDatabase employeeDatabase;

    @Inject
    public BarChartPresenter(BaseView view, EmployeeXAxisFormatter iAdapter , EmployeeDatabase employeeDatabase) {
        this.view = (IBarChart.view) view;
        this.iAdapter = iAdapter;
        this.employeeDatabase = employeeDatabase;
    }


    @Override
    public void loadBarChartLateData() {

        List<Float> floats = new ArrayList<>();

        for (int i = 0; i < employeeDatabase.getEmployees().size(); i++) {
            if (employeeDatabase.getEmployees().get(i).getTimesLate() != 0) {
                iAdapter.addEmployees(employeeDatabase.getEmployees().get(i));
                float f = employeeDatabase.getEmployees().get(i).getTimesLate();
                Log.d(TAG, "loadBarChartData: float f " + f);
                floats.add(f);
            }
        }

        if(floats.size() > 0){
            view.hideBeginningView();
        }
        view.addBarChartEntries(floats , iAdapter);


    }

    @Override
    public void loadBarChartAbsentData() {

        List<Float> floats = new ArrayList<>();

        for (int i = 0; i < employeeDatabase.getEmployees().size(); i++) {
            if (employeeDatabase.getEmployees().get(i).getTimesAbsent() != 0) {
                iAdapter.addEmployees(employeeDatabase.getEmployees().get(i));
                float f = employeeDatabase.getEmployees().get(i).getTimesAbsent();
                Log.d(TAG, "loadBarChartData: float f " + f);
                floats.add(f);
            }
        }

        if(floats.size() > 0){
            view.hideBeginningView();
        }
        view.addBarChartEntries(floats , iAdapter);
    }
}
