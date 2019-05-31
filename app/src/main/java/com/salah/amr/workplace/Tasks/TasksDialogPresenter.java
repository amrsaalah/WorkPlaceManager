package com.salah.amr.workplace.Tasks;

import android.util.Log;

import com.salah.amr.workplace.Base.BaseDialog;
import com.salah.amr.workplace.Model.EmployeeDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 1/28/2018.
 */

public class TasksDialogPresenter implements ITasks.dialogPresenter {

    private static final String TAG = "TasksDialogPresenter";

    private IDateDialog dateDialog;
    private IListDialog iListDialog;
    private EmployeeDatabase employeeDatabase;

    @Inject
    public TasksDialogPresenter(BaseDialog dialog, EmployeeDatabase employeeDatabase) {
        if (dialog instanceof IDateDialog) {
            this.dateDialog = (IDateDialog) dialog;
        } else {
            this.iListDialog = (IListDialog) dialog;
        }
        this.employeeDatabase = employeeDatabase;
    }

    @Override
    public void loadDate(int position) {
        dateDialog.setDate(employeeDatabase.getTasks().get(position).getDate());
    }

    @Override
    public void loadEmployeeNames() {
        Log.d(TAG, "loadEmployeeNames: ");
        List<String> names = new ArrayList<>();
        for (int i = 0; i < employeeDatabase.getEmployees().size(); i++) {
            names.add(employeeDatabase.getEmployees().get(i).getName());
        }
        iListDialog.setEmployeeNames(names);
    }
}
