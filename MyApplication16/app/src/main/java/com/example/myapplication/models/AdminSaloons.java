package com.example.myapplication.models;

import java.io.Serializable;

public class AdminSaloons implements Serializable {

    String firstname;
    String lastName;
    String mobile;
    String state;
    String city;
    String area;
    String userName;
    String email;
    String password;
    String id_adminSaloons;

    public AdminSaloons(String firstname, String lastName, String mobile, String state, String city, String area, String userName, String email, String password, String id_adminSaloons) {
        this.firstname = firstname;
        this.lastName = lastName;
        this.mobile = mobile;
        this.state = state;
        this.city = city;
        this.area = area;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.id_adminSaloons=id_adminSaloons;
    }

    public String getId_user() {
        return id_adminSaloons;
    }

    public void setId_adminSaloons(String id_adminSaloons) {
        this.id_adminSaloons = id_adminSaloons;
    }

    public AdminSaloons(){}

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId_adminSaloons() {
        return id_adminSaloons;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
