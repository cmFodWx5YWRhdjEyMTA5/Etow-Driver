package com.app.etow.ui.trip_upcoming_detail;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.data.NetworkManager;
import com.app.etow.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class TripUpcomingDetailPresenter extends BasePresenter<TripUpcomingDetailMVPView> {

    @Inject
    public TripUpcomingDetailPresenter(Retrofit mRetrofit, NetworkManager networkManager) {
        super(mRetrofit, networkManager);
    }

    @Override
    public void initialView(TripUpcomingDetailMVPView mvpView) {
        super.initialView(mvpView);
    }
}
