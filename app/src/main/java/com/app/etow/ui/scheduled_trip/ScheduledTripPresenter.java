package com.app.etow.ui.scheduled_trip;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import com.app.etow.adapter.ScheduledTripAdapter;
import com.app.etow.constant.Constant;
import com.app.etow.data.NetworkManager;
import com.app.etow.models.Trip;
import com.app.etow.ui.base.BasePresenter;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import javax.inject.Inject;

import retrofit2.Retrofit;

public class ScheduledTripPresenter extends BasePresenter<ScheduledTripMVPView> {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private String mReference;
    private ArrayList<Trip> listTripSchedule = new ArrayList<>();
    private ArrayList<Trip> listTripScheduleNew = new ArrayList<>();

    @Inject
    public ScheduledTripPresenter(Retrofit mRetrofit, NetworkManager networkManager) {
        super(mRetrofit, networkManager);
    }

    @Override
    public void initialView(ScheduledTripMVPView mvpView) {
        super.initialView(mvpView);
    }

    public void initFirebase() {
        mReference = "/trip";
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference(mReference);
    }

    public void getTripSchedule(ScheduledTripAdapter scheduledTripAdapter) {
        mDatabaseReference.orderByChild("is_schedule").equalTo(Constant.IS_SCHEDULE)
                .addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Trip trip = dataSnapshot.getValue(Trip.class);
                listTripSchedule.add(trip);
                scheduledTripAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Trip trip = dataSnapshot.getValue(Trip.class);
                if (listTripSchedule != null && listTripSchedule.size() > 0) {
                    for (int i = 0; i < listTripSchedule.size(); i++) {
                        if (trip.getId() == listTripSchedule.get(i).getId()) {
                            listTripSchedule.set(i, trip);
                            break;
                        }
                    }
                }
                scheduledTripAdapter.notifyDataSetChanged();
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
                scheduledTripAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getTripScheduleNew(ScheduledTripAdapter scheduledTripNewAdapter) {
        mDatabaseReference.orderByChild("status_schedule").equalTo(Constant.SCHEDULE_TRIP_STATUS_NEW)
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        listTripScheduleNew.add(trip);
                        scheduledTripNewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        if (listTripScheduleNew != null && listTripScheduleNew.size() > 0) {
                            for (int i = 0; i < listTripScheduleNew.size(); i++) {
                                if (trip.getId() == listTripScheduleNew.get(i).getId()) {
                                    listTripScheduleNew.set(i, trip);
                                    break;
                                }
                            }
                        }
                        scheduledTripNewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {
                        Trip trip = dataSnapshot.getValue(Trip.class);
                        if (listTripScheduleNew != null && listTripScheduleNew.size() > 0) {
                            for (int i = 0; i < listTripScheduleNew.size(); i++) {
                                if (trip.getId() == listTripScheduleNew.get(i).getId()) {
                                    listTripScheduleNew.remove(i);
                                    break;
                                }
                            }
                        }
                        scheduledTripNewAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
    }

    public ArrayList<Trip> getListTripSchedule() {
        return listTripSchedule;
    }

    public ArrayList<Trip> getListTripScheduleNew() {
        return listTripScheduleNew;
    }
}
