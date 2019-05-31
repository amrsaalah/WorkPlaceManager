package com.salah.amr.workplace.Model;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.util.Log;

import com.salah.amr.workplace.MyApp;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 11/24/2017.
 */

public class EmployeeDatabase {
    private static final String TAG = "EmployeeDatabase";
    private AppDatabase db;


    public EmployeeDatabase(AppDatabase appDatabase){
        this.db = appDatabase;
    }

    public void addEmployee(Employee employee){
        Log.d(TAG, "addEmployee: "+employee);
        db.employeeDAO().insert(employee);
    }

    public void deleteEmployee(Employee employee){
        db.employeeDAO().delete(employee);
    }
    
    public void deleteEmployee(int position){
        Log.d(TAG, "deleteEmployee: ");
        List<Employee> employees = getEmployees();
        deleteEmployee(employees.get(position));
    }

    public void updateEmployee(Employee employee){
        Log.d(TAG, "updateEmployeeData: ");
        db.employeeDAO().update(employee);
    }

    public List<Employee> getEmployees(){
        return db.employeeDAO().getEmployees();
    }

    public void addTask(Task task){
        Log.d(TAG, "addTask: ");
        db.taskDAO().insert(task);
    }

    public void updateTask(Task task){
        Log.d(TAG, "updateTask: ");
        db.taskDAO().update(task);
    }

    public void deleteTask(Task task){
        Log.d(TAG, "deleteTask: ");
        db.taskDAO().delete(task);
    }

    public List<Task> getTasks(){
        return db.taskDAO().getTasks();
    }

    public Employee getEmployeeById(int id){
        for(int i = 0 ; i<getEmployees().size() ; i++){
            if(id ==  getEmployees().get(i).getId()){
                return getEmployees().get(i);
            }
        }
        return null;
    }



}
