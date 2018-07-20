package com.app.etow.injection.components;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.injection.modules.ActivityModule;
import com.app.etow.ui.auth.SignInActivity;
import com.app.etow.ui.auth.term_and_condition.TermAndConditionActivity;
import com.app.etow.ui.direction_location.DirectionLocationActivity;
import com.app.etow.ui.feedback.FeedbackActivity;
import com.app.etow.ui.incoming_request.IncomingRequestActivity;
import com.app.etow.ui.main.MainActivity;
import com.app.etow.ui.main.get_in_touch.GetInTouchFragment;
import com.app.etow.ui.main.home.HomeFragment;
import com.app.etow.ui.main.my_account.MyAccountActivity;
import com.app.etow.ui.main.my_bookings.MyBookingsActivity;
import com.app.etow.ui.main.term_and_condition.TermAndConditionFragment;
import com.app.etow.ui.scheduled_trip.ScheduledTripActivity;
import com.app.etow.ui.scheduled_trip.detail.ScheduledTripDetailActivity;
import com.app.etow.ui.splash.SplashActivity;
import com.app.etow.ui.trip_completed_detail.TripCompletedDetailActivity;
import com.app.etow.ui.trip_summary.card.TripSummaryCardActivity;
import com.app.etow.ui.trip_summary.cash.TripSummaryCashActivity;
import com.app.etow.ui.trip_upcoming_detail.TripUpcomingDetailActivity;
import com.app.etow.ui.view_map_location.ViewMapLocationActivity;

import dagger.Subcomponent;

@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    /*inject activity*/
    void inject(SplashActivity loginActivity);

    void inject(MainActivity mainActivity);

    void inject(SignInActivity signInActivity);

    void inject(FeedbackActivity feedbackActivity);

    void inject(TripCompletedDetailActivity tripCompletedDetailActivity);

    void inject(TripUpcomingDetailActivity tripUpcomingDetailActivity);

    void inject(ViewMapLocationActivity viewMapLocationActivity);

    void inject(IncomingRequestActivity incomingRequestActivity);

    void inject(DirectionLocationActivity directionLocationActivity);

    void inject(TripSummaryCashActivity tripSummaryCashActivity);

    void inject(TripSummaryCardActivity tripSummaryCardActivity);

    void inject(ScheduledTripActivity scheduledTripActivity);

    void inject(ScheduledTripDetailActivity scheduledTripDetailActivity);

    void inject(TermAndConditionActivity termAndConditionActivity);

    /*inject fragment*/
    void inject(HomeFragment myTaskFragment);

    void inject(MyAccountActivity myAccountActivity);

    void inject(MyBookingsActivity myBookingsActivity);

    void inject(GetInTouchFragment contactUsFragment);

    void inject(TermAndConditionFragment termAndConditionFragment);
}
