package com.salah.amr.workplace.BarChart;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.salah.amr.workplace.R;
import com.salah.amr.workplace.Utils.BottomNavigationViewHelper;

import java.util.ArrayList;
import java.util.List;

public class BarChartActivity extends AppCompatActivity {

    private static final String TAG = "BarChartActivity";
    Context context = BarChartActivity.this;
    TabLayout tabLayout;
    ViewPager viewPager;
    List<Fragment> fragmentList = new ArrayList<>();
    BottomNavigationViewEx bottomNavigationViewEx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barchart);
        bottomNavigationViewEx = findViewById(R.id.bottom_navigation_ex);
        setupBottomNavigation();

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);

        TimesAbsentBarChartFragment timesAbsentBarChartFragment = new TimesAbsentBarChartFragment();
        TimesLateBarChartFragment timesLateBarChartFragment = new TimesLateBarChartFragment();
        fragmentList.add(timesLateBarChartFragment);
        fragmentList.add(timesAbsentBarChartFragment);
        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Late");
        tabLayout.getTabAt(1).setText("Absent");
    }

    private void setupBottomNavigation() {
        Log.d(TAG, "setupBottomNavigation: ");
        BottomNavigationViewHelper.setNavigationView(context,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
    }
}
