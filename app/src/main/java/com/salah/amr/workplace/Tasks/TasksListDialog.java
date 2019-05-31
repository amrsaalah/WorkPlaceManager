package com.salah.amr.workplace.Tasks;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.salah.amr.workplace.Base.BaseDialog;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 12/1/2017.
 */

public class TasksListDialog extends BaseDialog implements IListDialog{
    private static final String TAG = "TasksListDialog";
    public static final String EXTRA_EMPLOYEE_NAME = "extra_employee_name";
    List<String> names  = new ArrayList<>();

    @Inject
    TasksDialogPresenter presenter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.d(TAG, "onCreateDialog: ");
        View v = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_list , null);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        presenter.loadEmployeeNames();

        ListView listView = v.findViewById(R.id.listview);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getActivity() , android.R.layout.simple_list_item_1 , names);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Log.d(TAG, "onCreateDialog: click");
            Intent intent = new Intent();
            intent.putExtra(EXTRA_EMPLOYEE_NAME , i);
            getTargetFragment().onActivityResult(getTargetRequestCode() , Activity.RESULT_OK , intent);
        });
        return new AlertDialog.Builder(getActivity())
               .setView(v)
               .setTitle("Employee Names")
               .setNegativeButton("cancel" , null)
               .create();
    }

    @Override
    public void setEmployeeNames(List<String> names) {
        Log.d(TAG, "setEmployeeNames: ");
        this.names  = names;
    }
}
