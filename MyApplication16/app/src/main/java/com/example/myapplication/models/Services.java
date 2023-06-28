package com.example.myapplication.models;

import java.io.Serializable;

public class Services implements Serializable {
    String idservice; //sua
    String type;
    String name;
    String price;
    String id_salon;

    public Services(String idservice, String type, String name, String price, String id_salon) {
        this.idservice = idservice;
        this.type = type;
        this.name = name;
        this.price = price;
        this.id_salon = id_salon;
    }

    public Services() {

    }

    public String getId_salon() {
        return id_salon;
    }

    public void setId_salon(String id_salon) {
        this.id_salon = id_salon;
    }

    boolean isSelected = false;

    public String getIdservice() { //sua
        return idservice;
    }

    public void setIdservice(String idservice) {
        this.idservice = idservice;
    }//sua

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }
}
