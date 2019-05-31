package com.salah.amr.workplace.Attendance;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.salah.amr.workplace.Base.BaseFragment;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;
import com.salah.amr.workplace.Utils.BottomNavigationViewHelper;

import javax.inject.Inject;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by user on 11/23/2017.
 */

public class AttendanceFragment extends BaseFragment implements IAttendance.view ,AttendanceAdapter.OnCheckBoxListener {

    @Override
    public void onCheckBoxClick(int position , int id) {
        presenter.onCheckBoxClick(position , id);
    }

    private static final String TAG = "AttendanceFragment";
    private static final int NAVIGATION_POSITION = 1;

    private RecyclerView recyclerView;
    private TextView textDate;

    BottomNavigationViewEx bottomNavigationViewEx;
    TextView beginningView;

    @Inject
    AttendancePresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View v;
        v = inflater.inflate(R.layout.fragment_attendance , container , false);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        initWidgets(v);
        setupBottomNavigation(v);
        presenter.setupAlarm();
        presenter.setupTodayDate();
        presenter.loadEmployees();

        return v;
    }

    private void initWidgets(View v){
        recyclerView = v.findViewById(R.id.recycler_view);
        bottomNavigationViewEx = v.findViewById(R.id.bottom_navigation_ex);
        textDate = v.findViewById(R.id.attendance_date);
        beginningView = v.findViewById(R.id.beginning_view);
    }

    private void setupBottomNavigation(View v) {
        Log.d(TAG, "setupBottomNavigation: ");
        BottomNavigationViewHelper.setNavigationView(getActivity(),bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(NAVIGATION_POSITION);
        menuItem.setChecked(true);
    }

    @Override
    public void showEmployeeList(AttendanceAdapter adapter) {
        Log.d(TAG, "showEmployeeList: ");
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showTodayDate(String date) {
        textDate.setText("Attendance for "+date);
    }

    @Override
    public void launchAlarm(java.util.Calendar calendar) {
        Intent intent = new Intent(getActivity(), SalaryAlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), 5, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    @Override
    public void hideBeginningView() {
        beginningView.setVisibility(View.GONE);
    }


}
