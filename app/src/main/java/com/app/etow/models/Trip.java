package com.app.etow.models;

import java.io.Serializable;
import java.util.List;

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
    private String vehicle_type;
    private String payment_type;
    private String payment_status;
    private Driver driver;
    private User user;
    private String is_schedule;
    private String status_schedule;
    private String current_latitude;
    private String current_longitude;
    private String note;
    private String created_at;
    private String driver_id;
    private List<Reject> rejects;
    private List<Driver> driver_available;

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

    public String getVehicle_type() {
        return vehicle_type;
    }

    public void setVehicle_type(String vehicle_type) {
        this.vehicle_type = vehicle_type;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }

    public String getPayment_status() {
        return payment_status;
    }

    public void setPayment_status(String payment_status) {
        this.payment_status = payment_status;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getIs_schedule() {
        return is_schedule;
    }

    public void setIs_schedule(String is_schedule) {
        this.is_schedule = is_schedule;
    }

    public String getStatus_schedule() {
        return status_schedule;
    }

    public void setStatus_schedule(String status_schedule) {
        this.status_schedule = status_schedule;
    }

    public String getCurrent_latitude() {
        return current_latitude;
    }

    public void setCurrent_latitude(String current_latitude) {
        this.current_latitude = current_latitude;
    }

    public String getCurrent_longitude() {
        return current_longitude;
    }

    public void setCurrent_longitude(String current_longitude) {
        this.current_longitude = current_longitude;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(String driver_id) {
        this.driver_id = driver_id;
    }

    public List<Reject> getRejects() {
        return rejects;
    }

    public void setRejects(List<Reject> rejects) {
        this.rejects = rejects;
    }

    public List<Driver> getDriver_available() {
        return driver_available;
    }

    public void setDriver_available(List<Driver> driver_available) {
        this.driver_available = driver_available;
    }
}
