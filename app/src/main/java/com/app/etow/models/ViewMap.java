package com.app.etow.models;

import java.io.Serializable;

public class ViewMap implements Serializable {

    private String titleToolbar;
    private boolean isShowDistance;
    private int typeLocation;
    private Trip trip;
    private boolean isSaveTrip;

    public ViewMap() {
    }

    public ViewMap(String titleToolbar, boolean isShowDistance, int typeLocation, Trip trip, boolean isSaveTrip) {
        this.titleToolbar = titleToolbar;
        this.isShowDistance = isShowDistance;
        this.typeLocation = typeLocation;
        this.trip = trip;
        this.isSaveTrip = isSaveTrip;
    }

    public String getTitleToolbar() {
        return titleToolbar;
    }

    public void setTitleToolbar(String titleToolbar) {
        this.titleToolbar = titleToolbar;
    }

    public boolean isShowDistance() {
        return isShowDistance;
    }

    public void setShowDistance(boolean showDistance) {
        isShowDistance = showDistance;
    }

    public int getTypeLocation() {
        return typeLocation;
    }

    public void setTypeLocation(int typeLocation) {
        this.typeLocation = typeLocation;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public boolean isSaveTrip() {
        return isSaveTrip;
    }

    public void setSaveTrip(boolean saveTrip) {
        isSaveTrip = saveTrip;
    }
}
