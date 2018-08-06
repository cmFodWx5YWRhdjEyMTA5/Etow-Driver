package com.app.etow.models;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;

public class ViewMap implements Serializable {

    private String titleToolbar;
    private boolean isShowDistance;
    private int typeLocation;
    private String location;
    private String latitude;
    private String longitude;

    public ViewMap(String titleToolbar, boolean isShowDistance, int typeLocation, String location,
                   String latitude, String longitude) {
        this.titleToolbar = titleToolbar;
        this.isShowDistance = isShowDistance;
        this.typeLocation = typeLocation;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
