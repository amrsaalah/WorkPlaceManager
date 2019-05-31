package com.salah.amr.workplace.Attendance;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.salah.amr.workplace.Attendance.AttendanceFragment;
import com.salah.amr.workplace.R;

public class AttendanceActivity extends AppCompatActivity {

    private static final String TAG = "AttendanceActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.framelayout_container);
        AttendanceFragment fragment = new AttendanceFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container , fragment)
                .commit();
    }
}
