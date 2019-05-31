package com.salah.amr.workplace.Tasks;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;

import com.salah.amr.workplace.Base.BaseDialog;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.inject.Inject;

/**
 * Created by user on 12/1/2017.
 */

public class DatePickerDateDialog extends BaseDialog implements IDateDialog {

    public static final String EXTRA_DATE="extra_date";
    private static final String ARG_POSITION = "arg_position";
    Date date;
    int position;
    @Inject
    TasksDialogPresenter presenter;


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_date_picker , null);
        position = getArguments().getInt(ARG_POSITION);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        presenter.loadDate(position);
        DatePicker datePicker =  v.findViewById(R.id.datepicker);

        Calendar calendar = Calendar.getInstance();
        if(date != null){
            calendar.setTime(date);
        }
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(year , month , day , null);
        datePicker.setMinDate(System.currentTimeMillis() -1000);

        return new AlertDialog.Builder(getActivity())
                .setView(v)
                .setTitle("Date Picker")
                .setPositiveButton("ok" , (dialogInterface, i) -> {
                    int year2 = datePicker.getYear();
                   int month2 = datePicker.getMonth();
                   int day2  = datePicker.getDayOfMonth();
                   Date date = new GregorianCalendar(year2  , month2 ,day2).getTime();
                   Intent intent =new Intent();
                    intent.putExtra(EXTRA_DATE , date);
                    getTargetFragment().onActivityResult(getTargetRequestCode() , Activity.RESULT_OK , intent);
                })
                .setNegativeButton("Cancel" , null).create();
    }


    @Override
    public void setDate(Date date) {
        this.date = date;
    }


    public static DatePickerDateDialog newInstance(int position){
        Bundle args = new Bundle();
        args.putInt(ARG_POSITION, position);
        DatePickerDateDialog dialog = new DatePickerDateDialog();
        dialog.setArguments(args);
        return dialog;
    }
}
