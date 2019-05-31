package com.salah.amr.workplace.AddImage;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.salah.amr.workplace.Base.BaseFragment;
import com.salah.amr.workplace.ChatRoom.Activity.ChatRoomActivity;
import com.salah.amr.workplace.Dagger.AppComponent;
import com.salah.amr.workplace.Dagger.AppModule;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.Dagger.DaggerAppComponent;
import com.salah.amr.workplace.EmployeeList.EmployeeListActivity;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;

import java.io.ByteArrayOutputStream;

import javax.inject.Inject;

/**
 * Created by user on 12/8/2017.
 */

public class PhotoFragment extends BaseFragment implements IAddImage.view {
    private static final String TAG = "PhotoFragment";


    private static final String ARG_EMPLOYEE_ID = "arg_employee_id";
    private static final int REQUEST_CAMERA = 7 ;
    Button openCameraButton;
    ImageView closeButton;
    ProgressBar progressBar;
    int id;

    @Inject
    AddImagePresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt(ARG_EMPLOYEE_ID);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View  v =  inflater.inflate(R.layout.fragment_photo , container , false);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        openCameraButton = v.findViewById(R.id.open_camera_btn);
        closeButton = v.findViewById(R.id.close_button);
        progressBar = v.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        closeButton.setOnClickListener(view -> {
            getActivity().finish();
        });

        openCameraButton.setOnClickListener(view -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent , REQUEST_CAMERA);
        });

        return v;
    }


    public static PhotoFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putInt(ARG_EMPLOYEE_ID , id);
        PhotoFragment photoFragment = new PhotoFragment();
        photoFragment.setArguments(args);
        return photoFragment;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == REQUEST_CAMERA){
            if(resultCode == Activity.RESULT_OK){
                Log.d(TAG, "onActivityResult: camera ");
                Bitmap bitmap;
                bitmap = (Bitmap) data.getExtras().get("data");

                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "Title", null);
                Uri uri = Uri.parse(path);
                Log.d(TAG, "onActivityResult: "+path);
                Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                String realPath =  cursor.getString(idx);
                Log.d(TAG, "onActivityResult: "+realPath);
                presenter.addImage(realPath , id);
            }
        }
    }

    @Override
    public void navigateToEmployeeListActivity() {
        Intent intent = new Intent(getActivity() , EmployeeListActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToChatRoomActivity() {
        Intent intent = new Intent(getActivity() , ChatRoomActivity.class);
        startActivity(intent);
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }
}
