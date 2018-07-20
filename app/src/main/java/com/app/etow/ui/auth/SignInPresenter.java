package com.app.etow.ui.auth;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/06/16
 */

import com.app.etow.data.DataManager;
import com.app.etow.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class SignInPresenter extends BasePresenter<SignInMVPView> {

    @Inject
    public SignInPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(SignInMVPView mvpView) {
        super.initialView(mvpView);
    }
}
