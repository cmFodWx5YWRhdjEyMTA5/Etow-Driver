package com.app.etow.ui.splash;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;

import com.app.etow.R;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.data.prefs.DataStoreManager;
import com.app.etow.models.Trip;
import com.app.etow.models.ViewMap;
import com.app.etow.ui.auth.SignInActivity;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.ui.incoming_request.IncomingRequestActivity;
import com.app.etow.ui.main.MainActivity;
import com.app.etow.utils.Utils;

import javax.inject.Inject;

import butterknife.ButterKnife;

public class SplashActivity extends BaseMVPDialogActivity implements SplashMVPView {

    @Inject
    SplashPresenter presenter;

    private LocationManager mLocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);
        // init font text
        Utils.getTahomaRegularTypeFace(SplashActivity.this);
        // request open gps
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_splash;
    }

    @Override
    protected void onDestroy() {
        presenter.destroyView();
        super.onDestroy();
    }

    @Override
    public void showNoNetworkAlert() {
        showAlert(getString(R.string.error_not_connect_to_internet));
    }

    @Override
    public void onErrorCallApi(int code) {
        GlobalFuntion.showMessageError(this, code);
    }

    private void goToActivity() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int tripProcessId = DataStoreManager.getPrefIdTripProcess();
                if (tripProcessId != 0) {
                    presenter.getTripDetail(SplashActivity.this, tripProcessId);
                } else {
                    if (!DataStoreManager.getFirstInstallApp()) {
                        DataStoreManager.setFirstInstallApp(true);
                        DataStoreManager.removeUser();

                        GlobalFuntion.startActivity(SplashActivity.this, SignInActivity.class);
                    } else {
                        if (DataStoreManager.getIsLogin()) {
                            GlobalFuntion.startActivity(SplashActivity.this, MainActivity.class);
                        } else {
                            GlobalFuntion.startActivity(SplashActivity.this, SignInActivity.class);
                        }
                    }
                }
                finish();
            }
        }, 1000);
    }

    private void settingGPS() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            GlobalFuntion.showDialogNoGPS(this);
        } else if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            GlobalFuntion.getCurrentLocation(this, mLocationManager);
            goToActivity();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                settingGPS();
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            }
        }
    }

    @Override
    public void getTripDetail(Trip trip) {
        if (Constant.TRIP_STATUS_NEW.equals(trip.getStatus())) {
            GlobalFuntion.startActivity(SplashActivity.this, IncomingRequestActivity.class);
        } else  if (Constant.TRIP_STATUS_ACCEPT.equals(trip.getStatus())) {
            ViewMap viewMap = new ViewMap("", true, Constant.TYPE_PICK_UP, trip);
            GlobalFuntion.goToViewMapLocationActivity(this, viewMap);
        } else  if (Constant.TRIP_STATUS_ARRIVED.equals(trip.getStatus())) {
            ViewMap viewMap = new ViewMap("", true, Constant.TYPE_DROP_OFF, trip);
            GlobalFuntion.goToViewMapLocationActivity(this, viewMap);
        }
    }
}
