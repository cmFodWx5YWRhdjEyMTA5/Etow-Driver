package com.app.etow.ui.trip_upcoming_detail;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.models.Trip;
import com.app.etow.ui.base.BaseScreenMvpView;

interface TripUpcomingDetailMVPView extends BaseScreenMvpView {

    void updateStatusTrip(Trip trip);

    void updateLocationSuccess(int type);
}
