package com.app.etow.ui.main.get_in_touch;

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
public class GetInTouchPresenter extends BasePresenter<GetInTouchMVPView> {

    @Inject
    public GetInTouchPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(GetInTouchMVPView mvpView) {
        super.initialView(mvpView);
    }

    @Override
    public void destroyView() {
        super.destroyView();
    }
}
