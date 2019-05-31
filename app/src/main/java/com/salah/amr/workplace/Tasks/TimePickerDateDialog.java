package com.salah.amr.workplace.Tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TimePicker;

import com.salah.amr.workplace.Base.BaseDialog;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;

import java.util.Calendar;
import java.util.Date;

import javax.inject.Inject;


/**
 * Created by user on 12/1/2017.
 */

public class TimePickerDateDialog extends BaseDialog implements IDateDialog {

    public static final String EXTRA_TIME_DATE = "extra_time_date";
    private static final String ARG_POSITION = "arg_position";

    int position;
    TimePicker timePicker;
    Calendar calendar;
    Date date;

    @Inject
    TasksDialogPresenter presenter;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_time_picker , null);

        position = getArguments().getInt(ARG_POSITION);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        presenter.loadDate(position);

        timePicker = v.findViewById(R.id.timepicker);
        if(date != null){
            calendar  = Calendar.getInstance();
            calendar.setTime(date);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                timePicker.setHour(calendar.get(Calendar.HOUR_OF_DAY));
                timePicker.setMinute(calendar.get(Calendar.MINUTE));
            }else{
                timePicker.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
                timePicker.setCurrentMinute(calendar.get(Calendar.MINUTE));
            }

        }
        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Time Picker")
                .setPositiveButton("ok" , (dialogInterface, i) -> {
                    int hour =timePicker.getCurrentHour();
                    int min = timePicker.getCurrentMinute();
                    calendar =  Calendar.getInstance();
                    calendar.set(Calendar.HOUR_OF_DAY , hour);
                    calendar.set(Calendar.MINUTE , min);
                    Date date = calendar.getTime();
                    Intent intent = new Intent();
                    intent.putExtra(EXTRA_TIME_DATE ,date );
                    getTargetFragment().onActivityResult(getTargetRequestCode() , Activity.RESULT_OK , intent);
                })
                .setNegativeButton("Cancel" , null)
                .create();
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }


    public static TimePickerDateDialog newInstance(int position){
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        TimePickerDateDialog dialog = new TimePickerDateDialog();
        dialog.setArguments(args);
        return dialog;
    }
}
