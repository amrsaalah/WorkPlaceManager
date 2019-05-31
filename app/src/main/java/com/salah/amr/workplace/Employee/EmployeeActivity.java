package com.salah.amr.workplace.Employee;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.salah.amr.workplace.Base.BaseActivity;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.EmployeeList.IEmployeeList;
import com.salah.amr.workplace.Model.Employee;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;

import javax.inject.Inject;


public class EmployeeActivity extends BaseActivity implements IEmployee.activityView {

    private static final String EXTRA_POSITION = "extra_position";
    private static final String TAG = "EmployeeActivity";
    Context context = EmployeeActivity.this;
    EmployeeFragment fragment;
    ViewPager viewPager;

    @Inject
    EmployeeActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: activity starting");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewpager_container);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        viewPager = findViewById(R.id.viewpager_container_id);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (getIntent().hasExtra(EXTRA_POSITION)) {
                    return EmployeeFragment.newInstance(position);
                } else {
                    return EmployeeFragment.newInstance(-1);
                }

            }

            @Override
            public int getCount() {
                if (getIntent().hasExtra(EXTRA_POSITION)) {
                    return presenter.getNumberOfEmployees();
                } else {
                    return 1;
                }
            }
        });
        if(getIntent().hasExtra(EXTRA_POSITION))
        viewPager.setCurrentItem(getIntent().getIntExtra(EXTRA_POSITION , 0));
    }



    public static Intent newIntent(Context context, int position) {
        Intent intent = new Intent(context, EmployeeActivity.class);
        intent.putExtra(EXTRA_POSITION, position);
        return intent;
    }
}
