package com.salah.amr.workplace.Dagger;

import com.salah.amr.workplace.Base.BaseService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 1/28/2018.
 */
@ServiceScope
@Module
public class ServiceModule {

    private BaseService service;

    @ServiceScope
    public ServiceModule(BaseService service){
        this.service = service;
    }

    @Provides
    @ServiceScope
    public BaseService provideBaseService(){
        return this.service;
    }

}
