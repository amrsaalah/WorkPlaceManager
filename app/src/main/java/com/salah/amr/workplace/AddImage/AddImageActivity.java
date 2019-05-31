package com.salah.amr.workplace.AddImage;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.salah.amr.workplace.Model.EmployeeSharedPreferences;

import com.salah.amr.workplace.R;

import java.util.ArrayList;
import java.util.List;

public class AddImageActivity extends AppCompatActivity {
    private static final String TAG = "AddImageActivity";

    public static final String EXTRA_EMPLOYEE_ID = "extra_employee_id";
    List<Fragment> fragments = new ArrayList<>();
    TabLayout tabLayout;
    ViewPager viewPager;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_image);

        initWidgets();
        checkPermission();
        if (getIntent().hasExtra(EXTRA_EMPLOYEE_ID)) {
            Log.d(TAG, "onCreate:  coming from employees Activity");
            id = getIntent().getIntExtra(EXTRA_EMPLOYEE_ID, -1);
        } else {
            Log.d(TAG, "onCreate: coming from chatRoom activity");
            EmployeeSharedPreferences preferences = new EmployeeSharedPreferences(AddImageActivity.this);
            id = preferences.getEmployeeId();
        }
        GalleryFragment galleryFragment = GalleryFragment.newInstance(id);
        PhotoFragment photoFragment = PhotoFragment.newInstance(id);

        fragments.add(galleryFragment);
        fragments.add(photoFragment);

        viewPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setText("Gallery");
        tabLayout.getTabAt(1).setText("Photo");

    }

    private void initWidgets() {
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.viewpager);
    }

    public static Intent newIntent(Context context, int id) {
        Intent intent = new Intent(context, AddImageActivity.class);
        intent.putExtra(EXTRA_EMPLOYEE_ID, id);
        return intent;
    }

    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    9);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        if (grantResults.length > 0) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    finish();
                }
            }

        }
    }
}


