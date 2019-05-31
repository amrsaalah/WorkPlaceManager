package com.salah.amr.workplace;

import android.app.Application;
import android.content.Context;

import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.Model.AppDatabase;
import com.salah.amr.workplace.Model.EmployeeDatabase;

import javax.inject.Inject;

/**
 * Created by user on 11/2/2017.
 */

public class MyApp extends Application {
    private static MyApp instance;
    private static AppComponent component;

    public static MyApp getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance;
        // or return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        component = DaggerAppComponent.builder().appModule(new AppModule(instance)).build();
        component.inject(this);
        super.onCreate();
    }

    public static AppComponent getComponent(){
        return component;
    }



}