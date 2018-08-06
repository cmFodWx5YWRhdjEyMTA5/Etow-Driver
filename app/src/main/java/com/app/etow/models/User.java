package com.app.etow.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class User implements Serializable {

    private int id;
    private String full_name;
    private String email;
    private String phone;
    private String token;
    private String avatar;
    private DriverInfor drivers;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public DriverInfor getDrivers() {
        return drivers;
    }

    public void setDrivers(DriverInfor drivers) {
        this.drivers = drivers;
    }

    public String toJSon(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
