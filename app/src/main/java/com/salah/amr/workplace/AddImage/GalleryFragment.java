package com.salah.amr.workplace.AddImage;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.bumptech.glide.Glide;
import com.salah.amr.workplace.Base.BaseFragment;
import com.salah.amr.workplace.ChatRoom.Activity.ChatRoomActivity;
import com.salah.amr.workplace.Dagger.ControllerModule;
import com.salah.amr.workplace.EmployeeList.EmployeeListActivity;
import com.salah.amr.workplace.MyApp;
import com.salah.amr.workplace.R;
import com.salah.amr.workplace.Utils.FilePaths;
import com.salah.amr.workplace.Utils.FileSearch;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by user on 12/8/2017.
 */

public class GalleryFragment extends BaseFragment implements IAddImage.view {

    private static final String TAG = "GalleryFragment";
    private static final String ARG_EMPLOYEE_ID = "arg_employee_id";
    ImageView closeButton, selectedImage;
    Spinner spinner;
    ImageView saveButton;
    private List<String> directories;
    private List<String> imageFiles;
    private RecyclerView recyclerView;
    private String append = "file://";
    private String selectedImgURL;
    ProgressBar progressBar;
    int id;
    static boolean active = false;

    @Inject
    AddImagePresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt(ARG_EMPLOYEE_ID);
        setRetainInstance(true);
    }

    @Override
    public void onStart() {
        super.onStart();
        active = true;
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        active = false;
        Log.d(TAG, "onStop: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_gallery , container , false);
        initWidgets(v);

        MyApp.getComponent().newControllerComponent(new ControllerModule(this)).inject(this);

        progressBar.setVisibility(View.GONE);
        directories = new ArrayList<>();
        if (FileSearch.getDirectoryPaths(FilePaths.PICTURES) != null) {
            directories = FileSearch.getDirectoryPaths(FilePaths.PICTURES);
        }


        directories.add(FilePaths.CAMERA);
        List<String> stringList = new ArrayList<>();
        String [] splits;
        for(int i = 0 ; i<directories.size() ; i++){
            splits =  directories.get(i).split("/");
            stringList.add(splits[splits.length - 1]);
        }
        ArrayAdapter adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_spinner_item, stringList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected:  " + directories.get(position));
                imageFiles = FileSearch.getFilePaths(directories.get(position));
                if (imageFiles != null) {
                    recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                    GalleryAdapter adapter = new GalleryAdapter(imageFiles);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        saveButton.setOnClickListener(view -> {
            presenter.addImage(selectedImgURL , id);
        });

        closeButton.setOnClickListener(view -> {
            getActivity().finish();
        });


        return v;
    }

    private void initWidgets(View v){
        recyclerView = v.findViewById(R.id.recyclerview);
        selectedImage = v.findViewById(R.id.share_image);
        saveButton = v.findViewById(R.id.btn_save_changes);
        spinner =  v.findViewById(R.id.spinner);
        closeButton =  v.findViewById(R.id.close_button);
        progressBar = v.findViewById(R.id.progress_bar);
    }

    @Override
    public void navigateToEmployeeListActivity() {
        Intent intent = new Intent(getActivity() , EmployeeListActivity.class);
        startActivity(intent);
    }

    @Override
    public void navigateToChatRoomActivity() {
        if(active){
            Intent intent = new Intent(getActivity() , ChatRoomActivity.class);
            startActivity(intent);
        }

    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }


    private class GalleryHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        public GalleryHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_profile_image_view);
            itemView.setOnClickListener(v -> {
                int pos = getLayoutPosition();
                Log.d(TAG, "onClick: " + pos);
                Log.d(TAG, "onClick: " + append + imageFiles.get(pos));
                selectedImgURL = imageFiles.get(pos);
               /* Picasso.with(getActivity())
                        .load(append + imageFiles.get(pos))
                        .placeholder(null)
                        .into(selectedImage);*/
                Glide.with(getActivity()).load(append + imageFiles.get(pos)).asBitmap().placeholder(null).into(selectedImage);

            });
        }

        public void bindGallery(String url) {
            Log.d(TAG, "bindGallery: ");
          /*  Picasso.with(getActivity())
                    .load(append + url)
                    .fit()
                    .placeholder(null)
                    .into(imageView);*/

            Glide.with(getActivity()).load(append + url).asBitmap().placeholder(null).into(imageView);

        }
    }

    private class GalleryAdapter extends RecyclerView.Adapter<GalleryHolder> {

        private List<String> imageURLs;

        public GalleryAdapter(List<String> imageURLs) {
            this.imageURLs = imageURLs;
        }

        @Override
        public GalleryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(getActivity()).inflate(R.layout.item_profile_gridview, parent, false);
            return new GalleryHolder(v);
        }

        @Override
        public void onBindViewHolder(GalleryHolder holder, int position) {
            holder.bindGallery(imageURLs.get(position));
        }

        @Override
        public int getItemCount() {
            return imageURLs.size();
        }
    }


    public static GalleryFragment newInstance(int id){
        Bundle args = new Bundle();
        args.putInt(ARG_EMPLOYEE_ID , id);
        GalleryFragment galleryFragment = new GalleryFragment();
        galleryFragment.setArguments(args);
        return galleryFragment;
    }

}
