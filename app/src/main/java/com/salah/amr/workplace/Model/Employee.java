package com.salah.amr.workplace.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by user on 11/24/2017.
 */

@Entity
public class Employee {


    @Ignore
    public Employee(String name , String email , String phone , double salary){
        setName(name);
        setEmail(email);
        setPhone(phone);
        setSalary(salary);
        setCurrentSalary(salary);
        setLastSalary(salary);
        setTimesAbsent(0);
        setTimesLate(0);
        setLastTimesAbsent(0);
        setLastTimesLate(0);
        setImageURL(null);
        setAbsentCheck(false);
        setLateCheck(false);
        setOnTimeCheck(true);

    }

    public Employee(){}

    public void updateEmployeeData(String name , String email , String phone, double salary){
        setName(name);
        setEmail(email);
        setPhone(phone);
        setSalary(salary);
    }

    public void updateEmployeeAttendance(boolean onTime , boolean late , boolean absent){
        setAbsentCheck(absent);
        setLateCheck(late);
        setOnTimeCheck(onTime);
    }

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    private int id;

    @ColumnInfo
    private String name;

    @ColumnInfo
    private String email;

    @ColumnInfo
    private String phone;

    @ColumnInfo
    private double salary;

    @ColumnInfo
    private double currentSalary;

    @ColumnInfo
    private double lastSalary;

    @ColumnInfo
    private int timesLate;

    @ColumnInfo
    private int timesAbsent;

    @ColumnInfo
    private int lastTimesLate;

    @ColumnInfo
    private int lastTimesAbsent;


    @ColumnInfo
    private String imageURL;

    @ColumnInfo
    private boolean onTimeCheck;

    @ColumnInfo
    private boolean lateCheck;

    @ColumnInfo
    private boolean absentCheck;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public int getTimesLate() {
        return timesLate;
    }

    public void setTimesLate(int timesLate) {
        this.timesLate = timesLate;
    }

    public int getTimesAbsent() {
        return timesAbsent;
    }

    public void setTimesAbsent(int timesAbsent) {
        this.timesAbsent = timesAbsent;
    }

    public double getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(double currentSalary) {
        this.currentSalary = currentSalary;
    }

    public double getLastSalary() {
        return lastSalary;
    }

    public void setLastSalary(double lastSalary) {
        this.lastSalary = lastSalary;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public boolean isOnTimeCheck() {
        return onTimeCheck;
    }

    public void setOnTimeCheck(boolean onTimeCheck) {
        this.onTimeCheck = onTimeCheck;
    }

    public boolean isLateCheck() {
        return lateCheck;
    }

    public void setLateCheck(boolean lateCheck) {
        this.lateCheck = lateCheck;
    }

    public boolean isAbsentCheck() {
        return absentCheck;
    }

    public void setAbsentCheck(boolean absentCheck) {
        this.absentCheck = absentCheck;
    }

    public int getLastTimesLate() {
        return lastTimesLate;
    }

    public void setLastTimesLate(int lastTimesLate) {
        this.lastTimesLate = lastTimesLate;
    }

    public int getLastTimesAbsent() {
        return lastTimesAbsent;
    }

    public void setLastTimesAbsent(int lastTimesAbsent) {
        this.lastTimesAbsent = lastTimesAbsent;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", salary=" + salary +
                ", currentSalary=" + currentSalary +
                ", lastSalary=" + lastSalary +
                ", timesLate=" + timesLate +
                ", timesAbsent=" + timesAbsent +
                ", lastTimesLate=" + lastTimesLate +
                ", lastTimesAbsent=" + lastTimesAbsent +
                ", imageURL='" + imageURL + '\'' +
                ", onTimeCheck=" + onTimeCheck +
                ", lateCheck=" + lateCheck +
                ", absentCheck=" + absentCheck +
                '}';
    }
}