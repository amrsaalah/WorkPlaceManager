package com.salah.amr.workplace.EmployeeList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.salah.amr.workplace.Attendance.ResetAttendanceAlarm;
import com.salah.amr.workplace.ChatRoom.Activity.ChatRoomActivity;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 12/6/2017.
 */

public class ManagerOrEmployeeDialog extends DialogFragment {


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_list_manager_or_employee , container , false );
        ListView listView = v.findViewById(R.id.listview);
        List<String> stringList = new ArrayList<>();
        stringList.add("Manager");
        stringList.add("Employee");
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity() , android.R.layout.simple_list_item_1 , stringList);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            if(i == 0){
                // Manager
                dismiss();
                EmployeeSharedPreferences employeeSharedPreferences = new EmployeeSharedPreferences(getActivity());
                employeeSharedPreferences.setCodeOnce(true);
                employeeSharedPreferences.setManagerOrEmployee(0);
                ResetAttendanceAlarm resetAttendanceAlarm = new ResetAttendanceAlarm(getActivity());
            }else{
                // Employee
                Intent intent = new Intent(getActivity() , ChatRoomActivity.class);
                startActivity(intent);
                EmployeeSharedPreferences employeeSharedPreferences = new EmployeeSharedPreferences(getActivity());
                employeeSharedPreferences.setManagerOrEmployee(1);
                employeeSharedPreferences.setCodeOnce(true);
            }
        });
       getDialog().setCanceledOnTouchOutside(false);

        getDialog().setOnKeyListener((dialog, keyCode, event) -> {
            // Prevent dialog close on back press button
            return keyCode == KeyEvent.KEYCODE_BACK;
        });

        return v;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        TextView tv = (TextView) getDialog().findViewById(android.R.id.title);
        tv.setSingleLine(false);
        getDialog().setTitle("Choose how you want to use this App");
        //Window window = getDialog().getWindow();
        //window.setLayout(700, 800);
        //window.setGravity(Gravity.CENTER);

    }
}
