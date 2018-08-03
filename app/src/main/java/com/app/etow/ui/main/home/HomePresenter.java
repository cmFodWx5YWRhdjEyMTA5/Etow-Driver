package com.app.etow.ui.main.home;

/*
 * ******************************************************************************
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/16
 * ******************************************************************************
 */

import com.app.etow.constant.Constant;
import com.app.etow.data.NetworkManager;
import com.app.etow.injection.PerActivity;
import com.app.etow.models.Trip;
import com.app.etow.ui.base.BasePresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;

@PerActivity
public class HomePresenter extends BasePresenter<HomeMVPView> {

    @Inject
    public HomePresenter(Retrofit mRetrofit, NetworkManager networkManager) {
        super(mRetrofit, networkManager);
    }

    @Override
    public void initialView(HomeMVPView mvpView) {
        super.initialView(mvpView);
    }

    @Override
    public void destroyView() {
        super.destroyView();
    }
}
