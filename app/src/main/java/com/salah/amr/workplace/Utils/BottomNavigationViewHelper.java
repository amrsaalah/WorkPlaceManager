package com.salah.amr.workplace.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;


import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.salah.amr.workplace.Attendance.AttendanceActivity;
import com.salah.amr.workplace.BarChart.BarChartActivity;
import com.salah.amr.workplace.ChatRoom.Activity.ChatRoomActivity;
import com.salah.amr.workplace.EmployeeList.EmployeeListActivity;
import com.salah.amr.workplace.R;
import com.salah.amr.workplace.Tasks.TasksActivity;

/**
 * Created by user on 9/27/2017.
 */

public class BottomNavigationViewHelper {

    public static void setNavigationView(final Context context, BottomNavigationViewEx bottomNavigationViewEx){
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_employee_list:
                        Intent i = new Intent(context,EmployeeListActivity.class);
                        context.startActivity(i);
                        break;

                    case R.id.menu_employee_attendance:
                        Intent i2 = new Intent(context,AttendanceActivity.class);
                        context.startActivity(i2);
                        break;

                    case R.id.menu_employee_barGraph:
                        Intent i3 = new Intent(context,BarChartActivity.class);
                        context.startActivity(i3);
                        break;

                    case R.id.menu_tasks:
                        Intent i4 = new Intent(context,TasksActivity.class);
                        context.startActivity(i4);
                        break;

                    case R.id.menu_group_chat:
                        Intent i5 = new Intent(context,ChatRoomActivity.class);
                        context.startActivity(i5);
                        break;
                }

                return false;
            }
        });
    }

}
