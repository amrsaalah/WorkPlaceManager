package com.salah.amr.workplace.Model;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.arch.persistence.room.migration.Migration;
import android.support.annotation.NonNull;

import com.salah.amr.workplace.Utils.Converters;

/**
 * Created by user on 11/24/2017.
 */

@Database(entities = {Employee.class , Task.class} , version = 9 , exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract EmployeeDAO employeeDAO();
    public abstract TaskDAO taskDAO();

    static final Migration MIGRATION_5_6 = new Migration(5,6) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {

        }
    };



    static final Migration MIGRATION_8_9 = new Migration(8 , 9){
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL(" ALTER TABLE employee" +
                    "  ADD lastTimesLate INTEGER NOT NULL DEFAULT 0 ; ");

            database.execSQL(" ALTER TABLE employee" +
                    "  ADD lastTimesAbsent INTEGER NOT NULL DEFAULT 0 ; ");
        }
    };
    static final Migration MIGRATION_7_8 =  new Migration(7,8) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE employee " +
                    "Add lastSalary REAL NOT NULL DEFAULT 50 ");
        }
    };

    static final Migration MIGRATION_6_7 = new Migration(6, 7) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("create table task (" +
                    "id INTEGER  NOT NULL," +
                    "title TEXT," +
                    "description TEXT," +
                    "assignedEmployee TEXT," +
                    "date INTEGER,"+
                    "primary key(id)"+
                    ");");
        }
    };
}
