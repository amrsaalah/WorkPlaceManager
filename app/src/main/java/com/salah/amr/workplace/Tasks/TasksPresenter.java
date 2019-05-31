package com.salah.amr.workplace.Tasks;

import android.util.Log;

import com.salah.amr.workplace.Base.BaseView;
import com.salah.amr.workplace.Model.EmployeeDatabase;
import com.salah.amr.workplace.Model.Task;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 12/1/2017.
 */

public class TasksPresenter implements ITasks.presenter {

    private static final String TAG = "TasksPresenter";
    private ITasks.view view;
    private TasksAdapter adapter;
    EmployeeDatabase employeeDatabase;

    @Inject
    public TasksPresenter(BaseView view, TasksAdapter adapter , EmployeeDatabase employeeDatabase) {
        this.view = (ITasks.view) view;
        this.adapter = adapter;
        this.employeeDatabase = employeeDatabase;
    }


    @Override
    public void loadTasks() {
        Log.d(TAG, "loadTasks: ");
        List<Task> tasks = employeeDatabase.getTasks();
        adapter.setTasks(tasks);
        if(employeeDatabase.getTasks().size() > 0){
            view.hideBeginningView();
        }
        view.showTasksList(adapter);
    }


    @Override
    public void onAddTaskClicked() {
        Log.d(TAG, "onAddTaskClicked: ");
        Task task = new Task();
        employeeDatabase.addTask(task);
        view.refreshView();
    }

    @Override
    public void onDeleteTaskCLicked(int position) {
        Log.d(TAG, "onDeleteTaskCLicked: ");
        if (position != -1) {
            Task task = employeeDatabase.getTasks().get(position);
            employeeDatabase.deleteTask(task);
            view.refreshView();
            view.resetDeletePosition();
        }
    }

    @Override
    public void onItemClicked(int position) {

    }

    @Override
    public void updateDate(int position, Date date) {
        Log.d(TAG, "updateDate: ");
        Task task = employeeDatabase.getTasks().get(position);
        Date taskDate = task.getDate();
        if (taskDate != null) {
            Calendar taskCalendar = Calendar.getInstance();
            taskCalendar.setTime(taskDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            taskCalendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH));
            taskCalendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH));
            taskCalendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR));

            Date dateUpdated = taskCalendar.getTime();

            task.setDate(dateUpdated);
        } else {
            task.setDate(date);
        }
        employeeDatabase.updateTask(task);
        view.refreshView();
        setupTaskNotification(position);
        Log.d(TAG, "updateDate: date updated " + employeeDatabase.getTasks().get(position));

    }

    @Override
    public void updateTime(int position, Date date) {
        Log.d(TAG, "updateTime: ");
        Task task = employeeDatabase.getTasks().get(position);
        Date taskDate = task.getDate();
        if (taskDate != null) {
            Calendar taskCalendar = Calendar.getInstance();
            taskCalendar.setTime(taskDate);

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);

            taskCalendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
            taskCalendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE));

            Date dateUpdated = taskCalendar.getTime();

            task.setDate(dateUpdated);
        } else {
            task.setDate(date);
        }
        employeeDatabase.updateTask(task);
        view.refreshView();
        setupTaskNotification(position);
        Log.d(TAG, "updateTime: date updated " + employeeDatabase.getTasks().get(position));


    }

    @Override
    public void updateAssignedEmployee(int position , int employeePosition) {
        Task task = employeeDatabase.getTasks().get(position);
        task.setAssignedEmployee(employeeDatabase.getEmployees().get(employeePosition).getName());
        employeeDatabase.updateTask(task);
        view.refreshView();
    }


    @Override
    public void updateEditDescription(int position, String text) {
        Log.d(TAG, "updateEditDescription: ");
        Task task =  employeeDatabase.getTasks().get(position);
        task.setDescription(text);
        employeeDatabase.updateTask(task);
    }


    private void setupTaskNotification(int position){
        Calendar calendar = Calendar.getInstance();
        Date date = employeeDatabase.getTasks().get(position).getDate();
        int taskId  =  employeeDatabase.getTasks().get(position).getId();
        calendar.setTime(date);
        view.cancelNotification(taskId);
        Log.d(TAG, "setupTaskNotification: "+calendar.getTimeInMillis()  +"  system "+System.currentTimeMillis());
        if(calendar.getTimeInMillis() > System.currentTimeMillis())
            view.showNotification(calendar , taskId);
    }


}
