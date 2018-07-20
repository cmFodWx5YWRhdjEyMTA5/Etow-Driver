package com.app.etow.listener;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/19
 */

import android.os.SystemClock;
import android.view.View;

public abstract class IOnSingleClickListener implements View.OnClickListener {

    private static final long MIN_CLICK_INTERVAL = 600;
    private long mLastClickTime;

    public abstract void onSingleClick(View v);

    @Override
    public void onClick(View v) {
        long currentClickTime = SystemClock.uptimeMillis();
        long elapsedTime = currentClickTime - mLastClickTime;
        mLastClickTime = currentClickTime;
        if (elapsedTime <= MIN_CLICK_INTERVAL)
            return;
        onSingleClick(v);
    }
}
