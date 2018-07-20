package com.app.etow.ui.splash;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.os.Bundle;
import android.os.Handler;

import com.app.etow.R;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.ui.auth.SignInActivity;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.utils.Utils;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashActivity extends BaseMVPDialogActivity implements SplashMVPView {

    @Inject
    SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Utils.getTahomaBoldTypeFace(SplashActivity.this);
                Utils.getTahomaRegularTypeFace(SplashActivity.this);

                GlobalFuntion.startActivity(SplashActivity.this, SignInActivity.class);
                finish();
            }
        }, 1000);
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();
        super.onDestroy();
    }

    @Override
    public void showNoNetworkAlert() {
        showAlert(getString(R.string.error_not_connect_to_internet));
    }

    @Override
    public void onErrorCallApi(int code) {
        GlobalFuntion.showMessageError(this, code);
    }
}
