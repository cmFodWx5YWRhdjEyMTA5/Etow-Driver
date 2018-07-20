package com.app.etow.ui.main.term_and_condition;

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
public class TermAndConditionPresenter extends BasePresenter<TermAndConditionMVPView> {

    @Inject
    public TermAndConditionPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(TermAndConditionMVPView mvpView) {
        super.initialView(mvpView);
    }

    @Override
    public void destroyView() {
        super.destroyView();
    }
}
