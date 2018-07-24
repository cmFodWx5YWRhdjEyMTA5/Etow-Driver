package com.app.etow.ui.auth;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/06/16
 */

import com.app.etow.constant.Constant;
import com.app.etow.data.DataManager;
import com.app.etow.models.response.ApiResponse;
import com.app.etow.models.response.UserResponse;
import com.app.etow.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SignInPresenter extends BasePresenter<SignInMVPView> {

    @Inject
    public SignInPresenter(Retrofit mRetrofit, DataManager mDataManager) {
        super(mRetrofit, mDataManager);
    }

    @Override
    public void initialView(SignInMVPView mvpView) {
        super.initialView(mvpView);
    }

    public void login(String email, String password) {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            getMvpView().showProgressDialog(true);
            mDataManager.getUser(email, password)
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
                                    UserResponse userResponse = apiResponse.getDataObject(UserResponse.class);
                                    if (userResponse != null) {
                                        getMvpView().updateStatusLogin();
                                    } else {
                                        getMvpView().showAlert(apiResponse.getMessage());
                                    }
                                } else {
                                    getMvpView().showAlert(apiResponse.getMessage());
                                }
                            }
                        }
                    });
        }
    }
}
