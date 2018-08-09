package com.app.etow.ui.main.home;

/*
 * ******************************************************************************
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/16
 * ******************************************************************************
 */

import com.app.etow.constant.Constant;
import com.app.etow.data.NetworkManager;
import com.app.etow.data.prefs.DataStoreManager;
import com.app.etow.injection.PerActivity;
import com.app.etow.models.Driver;
import com.app.etow.models.response.ApiResponse;
import com.app.etow.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

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

    public void updateStatusDriver(String isOnline) {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            getMvpView().showProgressDialog(true);
            mNetworkManager.updateProfile(isOnline)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiResponse>() {
                        @Override
                        public void onCompleted() {
                            getMvpView().showProgressDialog(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            getMvpView().showProgressDialog(false);
                            getMvpView().onErrorCallApi(getErrorFromHttp(e).getCode());
                        }

                        @Override
                        public void onNext(ApiResponse apiResponse) {
                            if (apiResponse != null) {
                                if (Constant.SUCCESS.equalsIgnoreCase(apiResponse.getStatus())) {
                                    Driver driver = apiResponse.getDataObject(Driver.class);
                                    if (driver != null) {
                                        DataStoreManager.setUser(driver);
                                        getMvpView().loadStatusDriver();
                                    }
                                }
                            }
                        }
                    });
        }
    }
}
