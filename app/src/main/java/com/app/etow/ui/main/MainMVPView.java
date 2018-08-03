package com.app.etow.ui.main;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.ui.base.BaseScreenMvpView;

interface MainMVPView extends BaseScreenMvpView {

    void logout();

    void loadListTripSchedule(int count);
}
