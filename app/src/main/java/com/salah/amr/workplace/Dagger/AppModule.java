package com.salah.amr.workplace.Dagger;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.content.Context;

import com.salah.amr.workplace.EmployeeList.EmployeeListAdapter;
import com.salah.amr.workplace.EmployeeList.EmployeeListFragment;
import com.salah.amr.workplace.EmployeeList.IEmployeeList;
import com.salah.amr.workplace.Model.AppDatabase;
import com.salah.amr.workplace.Model.EmployeeDatabase;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.Model.UserDatabase;
import com.salah.amr.workplace.MyApp;

import javax.inject.Singleton;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 1/25/2018.
 */

@Singleton
@Module
public class AppModule {

    Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    @Singleton
    public AppDatabase provideAppDatabase(Application application){
        return Room.databaseBuilder(application,
                AppDatabase.class, "database-name")
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    public Application provideApplicationContext(){
        return application;
    }

    @Provides
    @Singleton
    public EmployeeSharedPreferences provideEmployeeSharedPreferences(Application application){
        return new EmployeeSharedPreferences(application);
    }

    @Provides
    @Singleton
    public UserDatabase provideUserDatabase(){
        return new UserDatabase();
    }

    @Provides
    @Singleton
    public EmployeeDatabase provideEmployeeDatabase(AppDatabase appDatabase){
        return new EmployeeDatabase(appDatabase);
    }


}
