package com.app.etow.ui.direction_location;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.data.NetworkManager;
import com.app.etow.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class DirectionLocationPresenter extends BasePresenter<DirectionLocationMVPView> {

    @Inject
    public DirectionLocationPresenter(Retrofit mRetrofit, NetworkManager networkManager) {
        super(mRetrofit, networkManager);
    }

    @Override
    public void initialView(DirectionLocationMVPView mvpView) {
        super.initialView(mvpView);
    }
}
