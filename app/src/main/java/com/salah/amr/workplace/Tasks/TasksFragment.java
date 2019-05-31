package com.salah.amr.workplace.Tasks;

import android.app.Activity;
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
import android.widget.ImageView;
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

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;

import static android.content.Context.ALARM_SERVICE;


/**
 * Created by user on 12/1/2017.
 */

public class TasksFragment extends BaseFragment implements  ITasks.view , TasksAdapter.OnItemClickListener{
    private static final String TAG = "TasksFragment";

    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_TIME = 1;
    private static final int REQUEST_LIST_NAMES = 2;

    private int itemPosition = -1 , datePosition = -1 , timePosition = -1 , assignedEmployeePosition = -1 ;

    @Override
    public void onItemClick(int position) {
        Log.d(TAG, "onItemClick: item position"+position);
        this.itemPosition = position;
        presenter.onItemClicked(position);
    }

    @Override
    public void onDateClick(int position) {
        DatePickerDateDialog datePickerDialog = DatePickerDateDialog.newInstance(position);
        //datePickerDialog.setTargetFragment(this , REQUEST_DATE);
      //  datePickerDialog.show(getFragmentManager() ,TAG);

        CalendarDialog calendarDialog =  new CalendarDialog();
        calendarDialog.show(getFragmentManager() , TAG);

        datePosition = position;

    }

    @Override
    public void onTimeClick(int position) {
        TimePickerDateDialog timePickerDialog = TimePickerDateDialog.newInstance(position);
        timePickerDialog.setTargetFragment(this , REQUEST_TIME);
        timePickerDialog.show(getFragmentManager() , TAG);
        timePosition = position;
    }

    @Override
    public void onSelectEmployeeClick(int position) {
        TasksListDialog tasksListDialog = new TasksListDialog();
        tasksListDialog.setTargetFragment(this ,REQUEST_LIST_NAMES);
        tasksListDialog.show(getFragmentManager() , TAG);
        assignedEmployeePosition = position;
    }

    @Override
    public void onEditDescriptionClick(int position , String text) {
        presenter.updateEditDescription(position , text );
    }

    private static final int NAVIGATION_POSITION = 3;

    BottomNavigationViewEx bottomNavigationViewEx;
    RecyclerView recyclerView;

    @Inject
    TasksPresenter presenter;
    ImageView addTaskButton , deleteTaskButton;
    TextView beginningView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tasks , container , false); ;
        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        initWidgets(v);
        setupBottomNavigation(v);
        presenter.loadTasks();

        addTaskButton.setOnClickListener(view -> {
            presenter.onAddTaskClicked();
        });

        deleteTaskButton.setOnClickListener(view -> {
            Log.d(TAG, "onCreateView: item position "+itemPosition);
            presenter.onDeleteTaskCLicked(itemPosition);
        });

        return v;
    }


    private void setupBottomNavigation(View v) {
        Log.d(TAG, "setupBottomNavigation: ");
        BottomNavigationViewHelper.setNavigationView(getActivity(), bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(NAVIGATION_POSITION);
        menuItem.setChecked(true);
    }

    private void initWidgets(View v){
        bottomNavigationViewEx = v.findViewById(R.id.bottom_navigation_ex);
        recyclerView = v.findViewById(R.id.recycler_view);
        addTaskButton = v.findViewById(R.id.add_tasks_btn);
        deleteTaskButton = v.findViewById(R.id.btn_delete_task);
        beginningView = v.findViewById(R.id.beginning_view);
    }

    @Override
    public void showTasksList(TasksAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void refreshView() {
        Intent intent = new Intent(getActivity() , TasksActivity.class);
        startActivity(intent);
    }

    @Override
    public void showNotification(Calendar calendar , int requestCode) {
        Log.d(TAG, "showNotification: ");
        Intent intent = new Intent(getActivity(), NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        Log.d(TAG, "showNotification: calendar notification time "+calendar.getTime());
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis() , pendingIntent);

    }

    @Override
    public void cancelNotification(int requestCode) {
        Log.d(TAG, "cancelNotification: ");
        boolean alarmUp = (PendingIntent.getBroadcast(getActivity(), requestCode,
                new Intent(getActivity(), NotificationReceiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);

        if (alarmUp)
        {
            Log.d(TAG, "cancelNotification: alarm exists");
            Intent intent = new Intent(getActivity(), NotificationReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(getActivity(), requestCode, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
            alarmManager.cancel(pendingIntent);
            pendingIntent.cancel();
        }

    }

    @Override
    public void resetDeletePosition() {
        Log.d(TAG, "resetDeletePosition: ");
        itemPosition = -1;
    }

    @Override
    public void hideBeginningView() {
        beginningView.setVisibility(View.GONE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_DATE){

            if(resultCode == Activity.RESULT_OK){
                Date date = (Date)data.getSerializableExtra(DatePickerDateDialog.EXTRA_DATE);
                presenter.updateDate(datePosition , date);
            }
        }

        if(requestCode == REQUEST_TIME){
            if(resultCode == Activity.RESULT_OK){
                Date date = (Date)data.getSerializableExtra(TimePickerDateDialog.EXTRA_TIME_DATE);
                presenter.updateTime(timePosition , date);
            }
        }

        if(requestCode == REQUEST_LIST_NAMES){
            if(resultCode == Activity.RESULT_OK){
                int employeePosition = data.getIntExtra(TasksListDialog.EXTRA_EMPLOYEE_NAME , -1);
                presenter.updateAssignedEmployee(assignedEmployeePosition , employeePosition);
            }
        }
    }
}
