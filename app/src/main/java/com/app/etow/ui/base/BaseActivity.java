package com.app.etow.ui.base;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.app.etow.ETowApplication;
import com.app.etow.R;
import com.app.etow.injection.components.ActivityComponent;
import com.app.etow.injection.components.ActivityScopeComponent;
import com.app.etow.injection.components.DaggerActivityScopeComponent;
import com.app.etow.injection.modules.ActivityModule;
import com.app.etow.utils.Utils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private ActivityComponent mActivityComponent;
    private ActivityScopeComponent mActivityScopeComponent;

    protected Unbinder viewUnbind;
    @Inject
    Toast mToast;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(addContextView());
        if (bindView()) {
            viewUnbind = ButterKnife.bind(this);
        }
        ETowApplication.get(this).getComponent().inject(this);
    }

    protected abstract boolean bindView();

    protected abstract int addContextView();

    public void hideAllSystemUI() {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            decorView.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            decorView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                    | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

    private ActivityScopeComponent getActivityScopeComponent() {
        if (mActivityScopeComponent == null) {
            mActivityScopeComponent = DaggerActivityScopeComponent.builder()
                    .applicationComponent(ETowApplication.get(this).getComponent())
                    .build();
        }
        return mActivityScopeComponent;
    }

    public final ActivityComponent getActivityComponent() {
        if (mActivityComponent == null) {
            mActivityComponent = getActivityScopeComponent()
                    .activityComponent(new ActivityModule(this));
        }
        return mActivityComponent;
    }


    @Override
    public void showNoNetworkAlert() {
        showMessage(R.string.error_not_connect_to_internet);
    }

    @Override
    public boolean isConnectToInternet() {
        return Utils.isConnectivityAvailable(this);
    }


    @MainThread
    @UiThread
    protected void showMessage(@StringRes int strRes) {
        showMessage(getString(strRes));
    }

    @MainThread
    @UiThread
    protected void showMessage(String message) {
        Toast toast = mToast;
        if (toast == null) {
            toast = Toast.makeText(this, message, Toast.LENGTH_LONG);
        } else {
            toast.setText(message);
        }
        toast.show();
    }

    @Override
    protected void onDestroy() {
        if (viewUnbind != null) {
            viewUnbind.unbind();
        }
        super.onDestroy();
    }
}
