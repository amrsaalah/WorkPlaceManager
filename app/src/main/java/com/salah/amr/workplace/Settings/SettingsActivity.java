package com.salah.amr.workplace.Settings;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.salah.amr.workplace.R;

public class SettingsActivity extends AppCompatActivity {

    ImageView backButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        SettingsFragment fragment = new SettingsFragment();
        backButton = findViewById(R.id.back_navigation);

        backButton.setOnClickListener(view -> {
            finish();
        });
        getFragmentManager().beginTransaction()
                .add(R.id.settings_container , fragment)
                .commit();

    }
}
