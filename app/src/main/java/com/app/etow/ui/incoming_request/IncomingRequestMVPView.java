package com.app.etow.ui.incoming_request;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.models.Trip;
import com.app.etow.ui.base.BaseScreenMvpView;

interface IncomingRequestMVPView extends BaseScreenMvpView {

    void getTripDetail(Trip trip);

    void getStatusUpdateLocation();
}
