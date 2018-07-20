package com.app.etow.ui.main.my_bookings;

/*
 * ******************************************************************************
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/16
 * ******************************************************************************
 */

import com.app.etow.models.Trip;
import com.app.etow.ui.base.BaseScreenMvpView;

import java.util.List;

interface MyBookingsMVPView extends BaseScreenMvpView {

    void loadListTripCompleted(List<Trip> listTripCompleted);

    void loadListTripUpcoming(List<Trip> listTripUpcoming);
}
