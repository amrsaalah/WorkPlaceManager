package com.salah.amr.workplace.Tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;
import com.salah.amr.workplace.R;

import java.util.Date;
import java.util.GregorianCalendar;

import static com.salah.amr.workplace.Tasks.DatePickerDateDialog.EXTRA_DATE;

/**
 * Created by Amr Salah on 3/14/2018.
 */

public class CalendarDialog extends DialogFragment {

    private static final String TAG = "CalendarDialog";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_calendar, null);
        MaterialCalendarView calendarView = v.findViewById(R.id.calendarView);

        calendarView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                Toast.makeText(getActivity(), date.toString(), Toast.LENGTH_LONG).show();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Date Picker")
                .setPositiveButton("ok", (dialogInterface, i) -> {
                    CalendarDay date = calendarView.getCurrentDate();
                    Date date1 = date.getDate();
                    Log.d(TAG, "onCreateDialog: date " + date1);
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_DATE, date);
                    getTargetFragment().onActivityResult(getTargetRequestCode(), Activity.RESULT_OK, intent);
                })
                .setNegativeButton("Cancel", null).create();
    }

}

