package com.app.etow.ui.view_map_location;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.models.Trip;
import com.app.etow.ui.base.BaseScreenMvpView;

interface ViewMapLocationMVPView extends BaseScreenMvpView {

    void updateStatusTrip(Trip trip);
}
