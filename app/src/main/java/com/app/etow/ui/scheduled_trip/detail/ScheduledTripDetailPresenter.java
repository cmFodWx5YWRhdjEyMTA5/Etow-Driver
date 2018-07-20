package com.app.etow.ui.scheduled_trip.detail;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.data.DataManager;
import com.app.etow.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class ScheduledTripDetailPresenter extends BasePresenter<ScheduledTripDetailMVPView> {

    @Inject
    public ScheduledTripDetailPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(ScheduledTripDetailMVPView mvpView) {
        super.initialView(mvpView);
    }
}
