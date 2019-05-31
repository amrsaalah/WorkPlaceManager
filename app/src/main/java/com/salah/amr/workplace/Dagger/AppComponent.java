package com.salah.amr.workplace.Dagger;

import com.salah.amr.workplace.EmployeeList.EmployeeListFragment;
import com.salah.amr.workplace.Model.AppDatabase;
import com.salah.amr.workplace.Model.EmployeeDatabase;
import com.salah.amr.workplace.MyApp;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by user on 1/25/2018.
 */

@Singleton
@Component(modules = {AppModule.class})
public interface AppComponent {

    ControllerComponent newControllerComponent(ControllerModule module);

    BroadRecComponent newReceiverComponent(BroadRecModule module);

    ServiceComponent newServiceComponent(ServiceModule module);

    void inject(MyApp target);
}
