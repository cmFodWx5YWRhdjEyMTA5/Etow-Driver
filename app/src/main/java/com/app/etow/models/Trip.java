package com.app.etow.models;

public class Trip {

    private boolean isAssigned;

    public Trip() {
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
}
