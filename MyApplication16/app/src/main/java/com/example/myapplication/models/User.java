package com.example.myapplication.models;

import java.io.Serializable;

public class User implements Serializable {
    String id_user;
    String fullname;
    String mobile;
    String email;
    String id_salon;
    String adderess;
    public  User(){

    }
    public User(String fullname, String mobile, String email, String adderess,String iduser,String id_salon) {
        this.fullname = fullname;
        this.mobile = mobile;
        this.email = email;
        this.id_salon=id_salon;
        this.adderess = adderess;
        this.id_user=iduser;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId_salon() {
        return id_salon;
    }

    public void setId_salon(String id_salon) {
        this.id_salon = id_salon;
    }

    public void setAdderess(String adderess) {
        this.adderess = adderess;
    }

    public String getFullname() {
        return fullname;
    }

    public String getMobile() {
        return mobile;
    }

    public String getEmail() {
        return email;
    }



    public String getAdderess() {
        return adderess;
    }
}
