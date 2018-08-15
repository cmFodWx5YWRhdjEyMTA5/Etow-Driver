package com.app.etow.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Setting implements Serializable {

    @SerializedName("time_km")
    private String timeDistance;

    @SerializedName("time_buffer")
    private String timeBuffer;

    @SerializedName("time_request_schedule")
    private String timeRequestSchedule;

    @SerializedName("time_estimate_arrive")
    private String estimateTimeArrive;

    public Setting() {
    }

    public String getTimeDistance() {
        return timeDistance;
    }

    public void setTimeDistance(String timeDistance) {
        this.timeDistance = timeDistance;
    }

    public String getTimeBuffer() {
        return timeBuffer;
    }

    public void setTimeBuffer(String timeBuffer) {
        this.timeBuffer = timeBuffer;
    }

    public String getTimeRequestSchedule() {
        return timeRequestSchedule;
    }

    public void setTimeRequestSchedule(String timeRequestSchedule) {
        this.timeRequestSchedule = timeRequestSchedule;
    }

    public String getEstimateTimeArrive() {
        return estimateTimeArrive;
    }

    public void setEstimateTimeArrive(String estimateTimeArrive) {
        this.estimateTimeArrive = estimateTimeArrive;
    }
}
