package com.app.etow.ui.view_map_location;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.data.NetworkManager;
import com.app.etow.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class ViewMapLocationPresenter extends BasePresenter<ViewMapLocationMVPView> {

    @Inject
    public ViewMapLocationPresenter(Retrofit mRetrofit, NetworkManager networkManager) {
        super(mRetrofit, networkManager);
    }

    @Override
    public void initialView(ViewMapLocationMVPView mvpView) {
        super.initialView(mvpView);
    }
}
