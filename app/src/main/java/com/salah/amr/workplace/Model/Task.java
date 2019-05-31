package com.salah.amr.workplace.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.salah.amr.workplace.Utils.Converters;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by user on 12/1/2017.
 */
@Entity
public class Task {
    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo
    int id;

    @ColumnInfo
    String description;

    @ColumnInfo
    String title;

    @ColumnInfo
    String assignedEmployee;


    @TypeConverters({Converters.class})
    @ColumnInfo
    Date date;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAssignedEmployee() {
        return assignedEmployee;
    }

    public void setAssignedEmployee(String assignedEmployee) {
        this.assignedEmployee = assignedEmployee;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", title='" + title + '\'' +
                ", assignedEmployee='" + assignedEmployee + '\'' +
                ", date=" + date +
                '}';
    }
}
