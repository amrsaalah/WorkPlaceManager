package com.salah.amr.workplace.Model;

/**
 * Created by user on 12/5/2017.
 */

public class User {
    int id;
    String name;
    String imageURL;
    String email;
    String phone;
    Boolean selected;
    Boolean deleted;


    public User(int id, String name, String imageURL, String email , String phone) {
        this.id = id;
        this.name = name;
        this.imageURL = imageURL;
        this.email = email;
        this.phone  =  phone;
        this.selected = false;
        this.deleted = false;
    }
    public User() {
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", selected=" + selected +
                ", deleted=" + deleted +
                '}';
    }
}
