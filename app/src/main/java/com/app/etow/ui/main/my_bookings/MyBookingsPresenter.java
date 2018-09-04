package com.app.etow.ui.main.my_bookings;

/*
 * ******************************************************************************
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/16
 * ******************************************************************************
 */

import android.content.Context;

import com.app.etow.ETowApplication;
import com.app.etow.adapter.TripCompletedAdapter;
import com.app.etow.adapter.TripUpcomingAdapter;
import com.app.etow.constant.Constant;
import com.app.etow.data.NetworkManager;
import com.app.etow.data.prefs.DataStoreManager;
import com.app.etow.injection.PerActivity;
import com.app.etow.models.Trip;
import com.app.etow.ui.base.BasePresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;

@PerActivity
public class MyBookingsPresenter extends BasePresenter<MyBookingsMVPView> {

    ArrayList<Trip> listTripCompleted = new ArrayList<>();
    ArrayList<Trip> listTripUpcoming = new ArrayList<>();

    @Inject
    public MyBookingsPresenter(Retrofit mRetrofit, NetworkManager networkManager) {
        super(mRetrofit, networkManager);
    }

    @Override
    public void initialView(MyBookingsMVPView mvpView) {
        super.initialView(mvpView);
    }

    @Override
    public void destroyView() {
        super.destroyView();
    }

    public void getTripCompleted(Context context, TripCompletedAdapter tripCompletedAdapter, String filter) {
        ETowApplication.get(context).getDatabaseReference().orderByChild("driver_id").equalTo(DataStoreManager.getUser().getId())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        if (Constant.TRIP_STATUS_COMPLETE == trip.getStatus()) {
                            if (Constant.FILTER_CASH.equals(filter)) {
                                if (Constant.TYPE_PAYMENT_CASH.equals(trip.getPayment_type()))
                                    listTripCompleted.add(trip);
                            } else if (Constant.FILTER_CARD.equals(filter)) {
                                if (Constant.TYPE_PAYMENT_CARD.equals(trip.getPayment_type()))
                                    listTripCompleted.add(trip);
                            } else {
                                listTripCompleted.add(trip);
                            }
                        }
                        tripCompletedAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        if (Constant.TRIP_STATUS_COMPLETE != trip.getStatus()) {
                            if (listTripCompleted != null && listTripCompleted.size() > 0) {
                                for (int i = 0; i < listTripCompleted.size(); i++) {
                                    if (trip.getId() == listTripCompleted.get(i).getId()) {
                                        listTripCompleted.remove(i);
                                        break;
                                    }
                                }
                            }
                        }
                        tripCompletedAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        if (listTripCompleted != null && listTripCompleted.size() > 0) {
                            for (int i = 0; i < listTripCompleted.size(); i++) {
                                if (trip.getId() == listTripCompleted.get(i).getId()) {
                                    listTripCompleted.remove(i);
                                    break;
                                }
                            }
                        }
                        tripCompletedAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void getTripUpcoming(Context context, TripUpcomingAdapter tripUpcomingAdapter, String filter) {
        ETowApplication.get(context).getDatabaseReference().orderByChild("driver_id").equalTo(DataStoreManager.getUser().getId())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        if (Constant.SCHEDULE_TRIP_STATUS_ACCEPT.equals(trip.getStatus_schedule())) {
                            if (Constant.FILTER_CASH.equals(filter)) {
                                if (Constant.TYPE_PAYMENT_CASH.equals(trip.getPayment_type()))
                                    listTripUpcoming.add(trip);
                            } else if (Constant.FILTER_CARD.equals(filter)) {
                                if (Constant.TYPE_PAYMENT_CARD.equals(trip.getPayment_type()))
                                    listTripUpcoming.add(trip);
                            } else {
                                listTripUpcoming.add(trip);
                            }
                        }
                        tripUpcomingAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        if (!Constant.SCHEDULE_TRIP_STATUS_ACCEPT.equals(trip.getStatus_schedule())) {
                            if (listTripUpcoming != null && listTripUpcoming.size() > 0) {
                                for (int i = 0; i < listTripUpcoming.size(); i++) {
                                    if (trip.getId() == listTripUpcoming.get(i).getId()) {
                                        listTripUpcoming.remove(i);
                                        break;
                                    }
                                }
                            }
                        }
                        tripUpcomingAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        if (listTripUpcoming != null && listTripUpcoming.size() > 0) {
                            for (int i = 0; i < listTripUpcoming.size(); i++) {
                                if (trip.getId() == listTripUpcoming.get(i).getId()) {
                                    listTripUpcoming.remove(i);
                                    break;
                                }
                            }
                        }
                        tripUpcomingAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public ArrayList<Trip> getListTripCompleted() {
        return listTripCompleted;
    }

    public ArrayList<Trip> getListTripUpcoming() {
        return listTripUpcoming;
    }
}
