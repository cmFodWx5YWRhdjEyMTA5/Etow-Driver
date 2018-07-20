package com.app.etow.ui.splash;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.data.DataManager;
import com.app.etow.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class SplashPresenter extends BasePresenter<SplashMVPView> {

    @Inject
    public SplashPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(SplashMVPView mvpView) {
        super.initialView(mvpView);
    }
}
