package com.app.etow.ui.scheduled_trip.detail;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.data.NetworkManager;
import com.app.etow.models.response.ApiSuccess;
import com.app.etow.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ScheduledTripDetailPresenter extends BasePresenter<ScheduledTripDetailMVPView> {

    @Inject
    public ScheduledTripDetailPresenter(Retrofit mRetrofit, NetworkManager networkManager) {
        super(mRetrofit, networkManager);
    }

    @Override
    public void initialView(ScheduledTripDetailMVPView mvpView) {
        super.initialView(mvpView);
    }

    public void updateTrip(int tripId, String status, String note) {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            getMvpView().showProgressDialog(true);
            mNetworkManager.updateTrip(tripId, status, note)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiSuccess>() {
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
                        public void onNext(ApiSuccess apiSuccess) {
                            getMvpView().finishActivity();
                        }
                    });
        }
    }
}
