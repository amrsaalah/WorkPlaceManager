package com.salah.amr.workplace.EmployeeList;

import android.app.IntentService;
import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.salah.amr.workplace.Base.BaseService;
import com.salah.amr.workplace.Dagger.ServiceModule;
import com.salah.amr.workplace.Model.Employee;
import com.salah.amr.workplace.Model.EmployeeDatabase;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.Model.User;
import com.salah.amr.workplace.Model.UserDatabase;
import com.salah.amr.workplace.MyApp;

import javax.inject.Inject;

public class SyncService extends BaseService implements UserDatabase.ICallbackGetUserById {
    private static final String TAG = "SyncService";

    @Override
    public void callbackGetUserById(User user) {
        Log.d(TAG, "callbackGetUserById: count " + count);
        if (count < employeeDatabase.getEmployees().size()) {
            Employee employee = employeeDatabase.getEmployees().get(count);
            Log.d(TAG, "callbackGetUserById: employee " + employee + " user " + user);
            if (!user.getImageURL().isEmpty())
                employee.setImageURL(user.getImageURL());
            if (!user.getEmail().isEmpty())
                employee.setEmail(user.getEmail());
            if (!user.getPhone().isEmpty())
                employee.setPhone(user.getPhone());

            employeeDatabase.updateEmployee(employee);
            count++;
        }

        if (count == employeeDatabase.getEmployees().size()) {
            Log.d(TAG, "callbackGetUserById: finished ");
            sendResult("finish");
        }
    }

    private LocalBroadcastManager broadcaster;
    static final public String COPA_RESULT = "REQUEST_PROCESSED";
    static final public String COPA_MESSAGE = "MSG";
    EmployeeSharedPreferences preferences;
    int count = 0;

    @Inject
    EmployeeDatabase employeeDatabase;

    @Inject
    UserDatabase userDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
        broadcaster = LocalBroadcastManager.getInstance(this);
        MyApp.getComponent().newServiceComponent(new ServiceModule(this)).inject(this);
    }

    public SyncService() {
        super("SyncService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: "+employeeDatabase +" "+userDatabase);
        preferences = new EmployeeSharedPreferences(this);
        for (int i = 0; i < employeeDatabase.getEmployees().size(); i++) {
            Employee employee = employeeDatabase.getEmployees().get(i);
            userDatabase.getUserById(preferences.getChatRoomName(), employee.getId(), this);
        }
    }


    public void sendResult(String message) {
        Log.d(TAG, "sendResult: ");
        Intent intent = new Intent(COPA_RESULT);
        if (message != null)
            intent.putExtra(COPA_MESSAGE, message);
        broadcaster.sendBroadcast(intent);
    }
}
