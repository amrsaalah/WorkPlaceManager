package com.salah.amr.workplace.Tasks;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by user on 12/1/2017.
 */

public interface ITasks {
    interface view{
        void showTasksList(TasksAdapter adapter);
        void refreshView();
        void showNotification(Calendar calendar , int requestCode);
        void cancelNotification(int requestCode);
        void resetDeletePosition();
        void hideBeginningView();
    }

    interface presenter{
        void loadTasks();
        void onAddTaskClicked();
        void onDeleteTaskCLicked(int position);
        void onItemClicked(int position);
        void updateDate(int position , Date date);
        void updateTime(int position , Date date);
        void updateAssignedEmployee(int position , int employeePosition);

        void updateEditDescription(int position , String text);
    }

    interface dialogPresenter{
        void loadDate(int position);
        void loadEmployeeNames();
    }
}
