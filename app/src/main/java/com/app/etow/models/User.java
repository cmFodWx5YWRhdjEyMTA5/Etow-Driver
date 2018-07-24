package com.app.etow.models;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class User {

    private int id;
    @SerializedName("full_name")
    private String fullName;
    private String email;
    private String phone;
    private String token;
    private String avatar;
    private int type;
    @SerializedName("type_vehicle")
    private int typeVehicle;
    private int status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getTypeVehicle() {
        return typeVehicle;
    }

    public void setTypeVehicle(int typeVehicle) {
        this.typeVehicle = typeVehicle;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String toJSon(){
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
