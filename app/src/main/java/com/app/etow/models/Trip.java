package com.app.etow.models;

import java.io.Serializable;

public class Trip implements Serializable{

    private int id;
    private String pickup_date;
    private String pick_up;
    private String pickup_latitude;
    private String pickup_longitude;
    private String drop_off;
    private String dropoff_latitude;
    private String dropoff_longitude;
    private String price;
    private String status;
    private String payment_type;
    private String vehicle_type;
    private User customer;
    private String created_at;

    public Trip() {
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

    public String getPickup_latitude() {
        return pickup_latitude;
    }

    public void setPickup_latitude(String pickup_latitude) {
        this.pickup_latitude = pickup_latitude;
    }

    public String getPickup_longitude() {
        return pickup_longitude;
    }

    public void setPickup_longitude(String pickup_longitude) {
        this.pickup_longitude = pickup_longitude;
    }

    public String getDrop_off() {
        return drop_off;
    }

    public void setDrop_off(String drop_off) {
        this.drop_off = drop_off;
    }

    public String getDropoff_latitude() {
        return dropoff_latitude;
    }

    public void setDropoff_latitude(String dropoff_latitude) {
        this.dropoff_latitude = dropoff_latitude;
    }

    public String getDropoff_longitude() {
        return dropoff_longitude;
    }

    public void setDropoff_longitude(String dropoff_longitude) {
        this.dropoff_longitude = dropoff_longitude;
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

    public User getCustomer() {
        return customer;
    }

    public void setCustomer(User customer) {
        this.customer = customer;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
