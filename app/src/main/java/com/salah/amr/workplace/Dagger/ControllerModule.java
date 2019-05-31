package com.salah.amr.workplace.Dagger;

import android.preference.PreferenceFragment;

import com.salah.amr.workplace.Base.BaseActivity;
import com.salah.amr.workplace.Base.BaseDialog;
import com.salah.amr.workplace.Base.BaseFragment;
import com.salah.amr.workplace.Base.BaseListener;
import com.salah.amr.workplace.Base.BaseView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by user on 1/26/2018.
 */
@ControllerScope
@Module
public class ControllerModule {

    PreferenceFragment preferenceFragment;
    BaseFragment fragment;
    BaseDialog dialog;
    private BaseActivity activity;

    @ControllerScope
    public ControllerModule(BaseFragment fragment){
        this.fragment = fragment;
    }

    @ControllerScope
    public ControllerModule(BaseActivity activity){
        this.activity = activity;
    }

    @ControllerScope
    public ControllerModule(BaseDialog dialog){
        this.dialog = dialog;
    }

    @ControllerScope
    public ControllerModule(PreferenceFragment fragment){
        this.preferenceFragment = fragment;
    }

    @Provides
    @ControllerScope
    BaseActivity provideActivity(){
        return this.activity;
    }

    @Provides
    @ControllerScope
    BaseView provideView(){
        return this.fragment;
    }

    @Provides
    @ControllerScope
    BaseDialog provideDialog(){
        return this.dialog;
    }

    @Provides
    @ControllerScope
    BaseFragment provideBaseFragment(){
        return this.fragment;
    }

    @Provides
    @ControllerScope
    BaseListener provideOnItemClickListener(){
        return (BaseListener) fragment;
    }

    @Provides
    @ControllerScope
    PreferenceFragment providePreferenceFragment(){
        return this.preferenceFragment;
    }


}
