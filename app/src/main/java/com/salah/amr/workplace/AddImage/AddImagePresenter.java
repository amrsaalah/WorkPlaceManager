package com.salah.amr.workplace.AddImage;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import com.salah.amr.workplace.Base.BaseView;
import com.salah.amr.workplace.Model.Employee;
import com.salah.amr.workplace.Model.EmployeeDatabase;
import com.salah.amr.workplace.Model.EmployeeSharedPreferences;
import com.salah.amr.workplace.Model.UserDatabase;
import com.salah.amr.workplace.Utils.ImageManage;

import javax.inject.Inject;

/**
 * Created by user on 12/9/2017.
 */

public class AddImagePresenter implements IAddImage.presenter, UserDatabase.ICallbackUploadImage {

    @Override
    public void callbackUploadImage(String imageURL) {
        Log.d(TAG, "callbackUploadImage: ");
        view.hideProgressBar();
        userDatabase.updateUserValues(preferences.getChatRoomName() , id , null , imageURL , null , null , null);
        view.navigateToChatRoomActivity();


    }

    private String append = "file://";
    private static final String TAG = "AddImagePresenter";
    private IAddImage.view view;
    private EmployeeSharedPreferences preferences;
    private EmployeeDatabase employeeDatabase;
    private UserDatabase userDatabase;
    int id;

    @Inject
    public AddImagePresenter(BaseView view, EmployeeSharedPreferences preferences , EmployeeDatabase employeeDatabase , UserDatabase userDatabase) {
        this.view = (IAddImage.view) view;
        this.preferences = preferences;
        this.employeeDatabase = employeeDatabase;
        this.userDatabase = userDatabase;
    }

    @Override
    public void addImage(String imageURL, int id) {
        Log.d(TAG, "addImage: ");
        if (imageURL != null) {
            if (preferences.isManager() && id != -1) {
                for (int i = 0; i < employeeDatabase.getEmployees().size(); i++) {
                    if (id == employeeDatabase.getEmployees().get(i).getId()) {
                        Employee employee = employeeDatabase.getEmployees().get(i);
                        Log.d(TAG, "addImage: employee " + employee);
                        employee.setImageURL(append + imageURL);
                        employeeDatabase.updateEmployee(employee);
                        Log.d(TAG, "addImage: " + employee);
                        break;
                    }
                }
               view.navigateToEmployeeListActivity();
            } else {
                this.id = id;
                Log.d(TAG, "addImage: " + preferences.getChatRoomName() + preferences.getEmployeeId());
                Bitmap bm = ImageManage.getBitmap(imageURL);
                byte[] bytes = ImageManage.getBytesFromBitmap(bm, 100);

                String idText;
                if(id == -1){
                    idText = null;
                }else{
                    idText = String.valueOf(id);
                }
                userDatabase.uploadImage(bytes, preferences.getChatRoomName(), idText, this);
                view.showProgressBar();
            }

        }

    }



}
