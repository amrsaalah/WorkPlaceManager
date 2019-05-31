package com.salah.amr.workplace.Dagger;

import com.salah.amr.workplace.EmployeeList.SyncService;

import dagger.Subcomponent;

/**
 * Created by user on 1/28/2018.
 */
@Subcomponent(modules = {ServiceModule.class})
@ServiceScope
public interface ServiceComponent {

    void inject(SyncService target);

}
