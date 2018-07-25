package com.app.etow.models;

import com.google.gson.annotations.SerializedName;

public class Trip {

    private int id;
    private boolean isAssigned;
    private String pick_up;

    public Trip() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Trip(boolean isAssigned) {
        this.isAssigned = isAssigned;
    }

    public boolean isAssigned() {
        return isAssigned;
    }

    public void setAssigned(boolean assigned) {
        isAssigned = assigned;
    }

    public String getPick_up() {
        return pick_up;
    }

    public void setPick_up(String pick_up) {
        this.pick_up = pick_up;
    }
}
