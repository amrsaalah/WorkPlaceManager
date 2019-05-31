package com.salah.amr.workplace.AddImage;

import com.salah.amr.workplace.Base.BaseView;

/**
 * Created by user on 12/9/2017.
 */

public interface IAddImage {

    interface view extends BaseView{
        void navigateToEmployeeListActivity();
        void navigateToChatRoomActivity();
        void showProgressBar();
        void hideProgressBar();
    }
    interface presenter{
        void addImage(String imageURL , int id);
    }

}
