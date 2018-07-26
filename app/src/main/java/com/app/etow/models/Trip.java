package com.app.etow.models;

import com.google.gson.annotations.SerializedName;

public class Trip {

    private int id;
    private String pickup_date;
    private String pick_up;
    private String drop_off;
    private String price;
    private String status;
    private String payment_type;
    private String vehicle_type;
    private boolean isAssigned;

    public Trip() {
    }

    public Trip(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPickup_date() {
        return pickup_date;
    }

    public void setPickup_date(String pickup_date) {
        this.pickup_date = pickup_date;
    }

    public String getPick_up() {
        return pick_up;
    }

    public void setPick_up(String pick_up) {
        this.pick_up = pick_up;
    }

    public String getDrop_off() {
        return drop_off;
    }

    public void setDrop_off(String drop_off) {
        this.drop_off = drop_off;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }
}
