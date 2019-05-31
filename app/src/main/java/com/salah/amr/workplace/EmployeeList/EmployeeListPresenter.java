package com.salah.amr.workplace.EmployeeList;


import com.salah.amr.workplace.Base.BasePresenter;
import com.salah.amr.workplace.Base.BaseView;
import com.salah.amr.workplace.Model.Employee;
import com.salah.amr.workplace.Model.EmployeeDatabase;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 11/24/2017.
 */

public class EmployeeListPresenter extends BasePresenter implements IEmployeeList.presenter {

    private static final String TAG = "EmployeeListPresenter";
    private IEmployeeList.view view;
    private EmployeeDatabase employeeDatabase;
    private EmployeeListAdapter iAdapter;
    private EmployeeSharedPreferences preferences;

    @Inject
    public EmployeeListPresenter(BaseView view, EmployeeListAdapter iAdapter, EmployeeSharedPreferences preferences , EmployeeDatabase employeeDatabase ){

        this.view = (IEmployeeList.view) view;
        this.iAdapter = iAdapter;
        this.preferences = preferences;
       this.employeeDatabase = employeeDatabase;

    }

    @Override
    public void loadEmployees() {

        List<Employee> employeeList = employeeDatabase.getEmployees();
        if (employeeList.size() != 0) {
            iAdapter.setEmployees(employeeList);
            view.showEmployeeList(iAdapter);
        }

        if(employeeList.size() > 0){
            view.hideBeginningView();
        }
    }

    @Override
    public void onFabClicked() {
        view.startEmployeeActivity();
    }

    @Override
    public void onItemClicked(int position) {
        view.startEmployeeActivityWithExtra(position);
    }

    @Override
    public void onSettingsButtonClicked() {
        view.navigateToSettingsActivity();
    }

    @Override
    public void setupManagerOrEmployeeDialog() {
        if(!preferences.getCodeOnce()){
            view.showManagerOrEmployeeDialog();
        }
    }

    @Override
    public void setupManagerOrEmployeeView() {
        if(preferences.getSignedOut()){
            view.navigateToLoginActivity();
        }else{
            if(!preferences.isManager()){
                view.navigateToChatRoomActivity();
            }
        }

    }

    @Override
    public void onSyncButtonClicked() {
        if(preferences.isSetupChatRoomDialog()){
           view.showSyncAlertDialog();
        }else{
            view.showSyncErrorMessage();
        }
    }


}
