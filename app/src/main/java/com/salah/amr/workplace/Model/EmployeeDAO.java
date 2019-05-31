package com.salah.amr.workplace.Model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by user on 11/24/2017.
 */
@Dao
public interface EmployeeDAO {

    @Query("select * from employee")
    List<Employee> getEmployees();

    @Insert
    void insert(Employee employee);

    @Delete
    void delete(Employee employee);

    @Update
    void update(Employee employee);
}
