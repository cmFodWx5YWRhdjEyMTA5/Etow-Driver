package com.app.etow;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.app.Application;
import android.content.Context;

import com.app.etow.data.prefs.DataStoreManager;
import com.app.etow.injection.components.ApplicationComponent;
import com.app.etow.injection.components.DaggerApplicationComponent;
import com.app.etow.injection.modules.ApplicationModule;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ETowApplication extends Application {

    private final Object lock = new Object();
    private ApplicationComponent mApplicationComponent;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private String mReference;

    public static ETowApplication get(Context context) {
        return (ETowApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DataStoreManager.init(getApplicationContext());
        FirebaseApp.initializeApp(this);
        initFirebase();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            synchronized (lock) {
                if (mApplicationComponent == null) {
                    mApplicationComponent = DaggerApplicationComponent.builder()
                            .applicationModule(new ApplicationModule(this))
                            .build();
                }
            }
        }
        return mApplicationComponent;
    }

    public void initFirebase() {
        mReference = "/trip";
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference(mReference);
    }

    public DatabaseReference getDatabaseReference() {
        return mDatabaseReference;
    }
}
