package com.app.etow.ui.feedback;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.constant.Constant;
import com.app.etow.data.NetworkManager;
import com.app.etow.models.response.ApiSuccess;
import com.app.etow.ui.base.BasePresenter;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FeedbackPresenter extends BasePresenter<FeedbackMVPView> {

    @Inject
    public FeedbackPresenter(Retrofit mRetrofit, NetworkManager networkManager) {
        super(mRetrofit, networkManager);
    }

    @Override
    public void initialView(FeedbackMVPView mvpView) {
        super.initialView(mvpView);
    }

    public void sendFeedback(String comment) {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            getMvpView().showProgressDialog(true);
            mNetworkManager.sendFeedback(comment)
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
                            if (apiSuccess != null) {
                                if (Constant.SUCCESS.equalsIgnoreCase(apiSuccess.getStatus())) {
                                    getMvpView().getStatusFeedback();
                                }
                            }
                        }
                    });
        }
    }
}
