package com.salah.amr.workplace.Tasks;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.salah.amr.workplace.R;

public class TasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.framelayout_container);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        TasksFragment fragment = new TasksFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container , fragment)
                .commit();
    }
}
