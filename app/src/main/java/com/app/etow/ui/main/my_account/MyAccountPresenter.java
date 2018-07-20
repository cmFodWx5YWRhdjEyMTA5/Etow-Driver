package com.app.etow.ui.main.my_account;

/*
 * ******************************************************************************
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/16
 * ******************************************************************************
 */

import com.app.etow.data.DataManager;
import com.app.etow.injection.PerActivity;
import com.app.etow.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;

@PerActivity
public class MyAccountPresenter extends BasePresenter<MyAccountMVPView> {

    @Inject
    public MyAccountPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(MyAccountMVPView mvpView) {
        super.initialView(mvpView);
    }

    @Override
    public void destroyView() {
        super.destroyView();
    }
}
