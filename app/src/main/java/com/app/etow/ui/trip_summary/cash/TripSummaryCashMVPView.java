package com.app.etow.ui.trip_summary.cash;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.models.Trip;
import com.app.etow.ui.base.BaseScreenMvpView;

interface TripSummaryCashMVPView extends BaseScreenMvpView {

    void updateStatusTrip(Trip trip);
}
