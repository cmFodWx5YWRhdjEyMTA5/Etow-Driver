package com.app.etow.ui.main;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.content.Context;

import com.app.etow.ETowApplication;
import com.app.etow.constant.Constant;
import com.app.etow.data.NetworkManager;
import com.app.etow.data.prefs.DataStoreManager;
import com.app.etow.models.Trip;
import com.app.etow.models.response.ApiSuccess;
import com.app.etow.ui.base.BasePresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainPresenter extends BasePresenter<MainMVPView> {

    private ArrayList<Trip> listTripSchedule = new ArrayList<>();

    @Inject
    public MainPresenter(Retrofit mRetrofit, NetworkManager networkManager) {
        super(mRetrofit, networkManager);
    }

    @Override
    public void initialView(MainMVPView mvpView) {
        super.initialView(mvpView);
    }

    public void logout(String token) {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            getMvpView().showProgressDialog(true);
            mNetworkManager.logout(token)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiSuccess>() {
                        @Override
                        public void onCompleted() {
                            getMvpView().showProgressDialog(false);
                        }

                        @Override
                        public void onError(Throwable e) {
                            getMvpView().showProgressDialog(false);
                            getMvpView().onErrorCallApi(getErrorFromHttp(e).getCode());
                        }

                        @Override
                        public void onNext(ApiSuccess apiSuccess) {
                            if (apiSuccess != null) {
                                DataStoreManager.setIsLogin(false);
                                DataStoreManager.setUserToken("");
                                DataStoreManager.removeUser();
                                getMvpView().logout();
                            }
                        }
                    });
        }
    }

    public void getScheduleTrip(Context context) {
        ETowApplication.get(context).getDatabaseReference().orderByChild("status_schedule")
                .equalTo(Constant.SCHEDULE_TRIP_STATUS_NEW)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        listTripSchedule.add(trip);
                        if (getMvpView() != null)
                            getMvpView().loadListTripSchedule(listTripSchedule.size());
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        if (listTripSchedule != null && listTripSchedule.size() > 0) {
                            for (int i = 0; i < listTripSchedule.size(); i++) {
                                if (trip.getId() == listTripSchedule.get(i).getId()) {
                                    listTripSchedule.remove(i);
                                    break;
                                }
                            }
                        }
                        if (getMvpView() != null)
                            getMvpView().loadListTripSchedule(listTripSchedule.size());
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void getTripIncoming(Context context) {
        ETowApplication.get(context).getDatabaseReference().orderByChild("status_schedule").equalTo(Constant.NORMAL_TRIP_STATUS_NEW)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        if (getMvpView() != null && trip != null)
                            getMvpView().getTripIncoming(trip);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public void updateLocationDriver(double latitude, double longitude) {
        if (!isConnectToInternet()) {
            notifyNoNetwork();
        } else {
            mNetworkManager.updateLocationUser(latitude, longitude)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<ApiSuccess>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            getMvpView().onErrorCallApi(getErrorFromHttp(e).getCode());
                        }

                        @Override
                        public void onNext(ApiSuccess apiSuccess) {

                        }
                    });
        }
    }

    public void getTripDetail(Context context, int tripId) {
        ETowApplication.get(context).getDatabaseReference().orderByChild("id").equalTo(tripId)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        if (getMvpView() != null && trip != null) getMvpView().getTripDetail(trip);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        if (getMvpView() != null && trip != null) getMvpView().getTripDetail(trip);
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }
}
