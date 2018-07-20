package com.app.etow.ui.base;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.MainThread;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.annotation.StringRes;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.app.etow.ETowApplication;
import com.app.etow.injection.ActivityScopeComponentCache;
import com.app.etow.injection.components.ActivityComponent;
import com.app.etow.injection.components.ActivityScopeComponent;
import com.app.etow.injection.components.DaggerActivityScopeComponent;
import com.app.etow.injection.modules.ActivityModule;

import javax.inject.Inject;

import butterknife.Unbinder;

public abstract class BaseFragment extends Fragment {

    private ActivityComponent activityComponent;
    private ActivityScopeComponent activityScopeComponent;

    protected Unbinder viewUnbind;

    private long componentStateKey = -1;
    private boolean isDestroyBySystem = false;

    @Inject
    ActivityScopeComponentCache activityScopeComponentCache;

    @Inject
    Toast mToast;

    private ActivityScopeComponent getActivityScopeComponent() {
        if (activityScopeComponent == null) {
            activityScopeComponent = DaggerActivityScopeComponent.builder()
                    .applicationComponent(ETowApplication.get(getActivity()).getComponent())
                    .build();
        }
        return activityScopeComponent;
    }

    public final ActivityComponent getActivityComponent() {
        if (activityComponent == null) {
            activityComponent = getActivityScopeComponent()
                    .activityComponent(new ActivityModule(getActivity()));
        }
        return activityComponent;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ETowApplication.get(getActivity()).getComponent().inject(this);
        activityScopeComponent = activityScopeComponentCache.restoreComponent(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (activityScopeComponentCache != null) {
            componentStateKey = activityScopeComponentCache
                    .saveComponentInstance(activityScopeComponent, outState);
        }
        isDestroyBySystem = true;
    }

    @Override
    public void onDestroyView() {
        if (viewUnbind != null) {
            viewUnbind.unbind();
        }
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        if (!isDestroyBySystem) {
            if (activityScopeComponentCache != null) {
                activityScopeComponentCache.clearComponent(componentStateKey);
            }
        }
        isDestroyBySystem = false;
        super.onDestroy();
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
        if (toast != null) {
            toast.setText(message);
        }
        Context context = getContext();
        if (context != null) {
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        }
        if (toast != null) {
            toast.show();
        }
    }
}
