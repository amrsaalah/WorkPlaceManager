package com.salah.amr.workplace.EmployeeList;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.salah.amr.workplace.Employee.EmployeeActivity;
import com.salah.amr.workplace.Employee.EmployeeFragment;
import com.salah.amr.workplace.R;
import com.salah.amr.workplace.Utils.BottomNavigationViewHelper;

public class EmployeeListActivity extends AppCompatActivity implements EmployeeListFragment.Callback {

    private static final String TAG = "EmployeeListActivity";
    private static final int NAVIGATION_POSITION = 0;
    Context context = EmployeeListActivity.this;
    BottomNavigationViewEx bottomNavigationViewEx;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Activity Starting ");
        setContentView(R.layout.activity_masterdetail);
        Log.d(TAG, "onCreate: "+bottomNavigationViewEx);
        bottomNavigationViewEx = findViewById(R.id.bottom_navigation_ex);
        Log.d(TAG, "onCreate: "+bottomNavigationViewEx);
        setupBottomNavigation();
        EmployeeListFragment fragment = new EmployeeListFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commit();
    }


    @Override
    public void navigateToEmployeeActivity(int position) {
        Log.d(TAG, "navigateToEmployeeActivity: ");
        if (findViewById(R.id.detail_fragment_container) == null) {
            if (position == -2) {
                Intent intent = new Intent(context, EmployeeActivity.class);
                startActivity(intent);
            }else{
                 Intent intent = EmployeeActivity.newIntent(context, position);
                 startActivity(intent);
            }
        } else {
            if(position == -2){
                Fragment newDetail = EmployeeFragment.newInstance(-1);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment_container, newDetail)
                        .commit();
            }else{
                Fragment newDetail = EmployeeFragment.newInstance(position);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.detail_fragment_container, newDetail)
                        .commit();
            }
           
        }
    }

    private void setupBottomNavigation() {
        Log.d(TAG, "setupBottomNavigation: ");
        BottomNavigationViewHelper.setNavigationView(context, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(NAVIGATION_POSITION);
        menuItem.setChecked(true);
    }
}
