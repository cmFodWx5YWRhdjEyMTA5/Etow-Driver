package com.app.etow.ui.scheduled_trip;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.data.NetworkManager;
import com.app.etow.models.Trip;
import com.app.etow.ui.base.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class ScheduledTripPresenter extends BasePresenter<ScheduledTripMVPView> {

    @Inject
    public ScheduledTripPresenter(Retrofit mRetrofit, NetworkManager networkManager) {
        super(mRetrofit, networkManager);
    }

    @Override
    public void initialView(ScheduledTripMVPView mvpView) {
        super.initialView(mvpView);
    }

    public void getListScheduledTrip() {
        List<Trip> list = new ArrayList<>();
        getMvpView().loadScheduledTrips(list);
    }
}
