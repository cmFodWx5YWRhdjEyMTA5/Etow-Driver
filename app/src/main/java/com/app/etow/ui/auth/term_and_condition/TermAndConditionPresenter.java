package com.app.etow.ui.auth.term_and_condition;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.data.NetworkManager;
import com.app.etow.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class TermAndConditionPresenter extends BasePresenter<TermAndConditionMVPView> {

    @Inject
    public TermAndConditionPresenter(Retrofit mRetrofit, NetworkManager networkManager) {
        super(mRetrofit, networkManager);
    }

    @Override
    public void initialView(TermAndConditionMVPView mvpView) {
        super.initialView(mvpView);
    }
}
