package com.app.etow.ui.main.my_bookings;

/*
 * ******************************************************************************
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/16
 * ******************************************************************************
 */

import com.app.etow.constant.Constant;
import com.app.etow.data.NetworkManager;
import com.app.etow.injection.PerActivity;
import com.app.etow.models.Trip;
import com.app.etow.ui.base.BasePresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Retrofit;

@PerActivity
public class MyBookingsPresenter extends BasePresenter<MyBookingsMVPView> {

    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mDatabaseReference;
    String mReference;
    ArrayList<Trip> listTripCompleted = new ArrayList<>();

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

    public void getListTripUpcoming() {
        List<Trip> list = new ArrayList<>();
        getMvpView().loadListTripUpcoming(list);
    }

    public void initFirebase() {
        mReference = "/trip";
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference(mReference);
    }

    public void getTripCompleted() {
        getMvpView().showProgressDialog(true);
        mDatabaseReference.orderByChild("status").equalTo(Constant.TRIP_STATUS_COMPLETE)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        getMvpView().showProgressDialog(false);
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        listTripCompleted.add(trip);
                        getMvpView().loadListTripCompleted();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        if (listTripCompleted != null && listTripCompleted.size() > 0) {
                            for (int i = 0; i < listTripCompleted.size(); i++) {
                                if (trip.getId() == listTripCompleted.get(i).getId()) {
                                    listTripCompleted.set(i, trip);
                                    break;
                                }
                            }
                        }
                        getMvpView().loadListTripCompleted();
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
                        getMvpView().loadListTripCompleted();
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
}
