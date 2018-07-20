package com.app.etow.ui.main;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.data.DataManager;
import com.app.etow.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class MainPresenter extends BasePresenter<MainMVPView> {

    @Inject
    public MainPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(MainMVPView mvpView) {
        super.initialView(mvpView);
    }
}
