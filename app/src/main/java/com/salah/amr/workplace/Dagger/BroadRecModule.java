package com.salah.amr.workplace.Dagger;

import com.salah.amr.workplace.Base.BaseReceiver;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 1/28/2018.
 */
@BroadRecScope
@Module
public class BroadRecModule {

    BaseReceiver receiver;

    @BroadRecScope
    public BroadRecModule(BaseReceiver receiver){
        this.receiver = receiver;
    }


    @Provides
    @BroadRecScope
    BaseReceiver provideReceiver(){
        return this.receiver;
    }


}
