package com.app.etow.ui.base;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.support.annotation.StringRes;

public interface BaseScreenMvpView extends MvpView {

    void createProgressDialog();

    void createAlertDialog();

    void showProgressDialog(boolean value);

    void showAlertDialog(@StringRes int resourceId);

    void showAlertDialog(String errorMessage);

    void dismissProgressDialog();

    void showAlert(String s);

    void showAlert(@StringRes int resourceId);

    void showNoNetworkAlert();

    boolean isConnectToInternet();

    void onErrorCallApi(int code);
}
