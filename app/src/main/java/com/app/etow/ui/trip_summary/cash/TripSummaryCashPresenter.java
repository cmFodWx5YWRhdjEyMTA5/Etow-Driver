package com.app.etow.ui.trip_summary.cash;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.data.NetworkManager;
import com.app.etow.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class TripSummaryCashPresenter extends BasePresenter<TripSummaryCashMVPView> {

    @Inject
    public TripSummaryCashPresenter(Retrofit mRetrofit, NetworkManager networkManager) {
        super(mRetrofit, networkManager);
    }

    @Override
    public void initialView(TripSummaryCashMVPView mvpView) {
        super.initialView(mvpView);
    }
}
