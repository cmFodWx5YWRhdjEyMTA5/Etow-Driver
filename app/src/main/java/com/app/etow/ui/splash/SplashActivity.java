package com.app.etow.ui.splash;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

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
import com.app.etow.ui.trip_summary.card.TripSummaryCardActivity;
import com.app.etow.ui.trip_summary.cash.TripSummaryCashActivity;
import com.app.etow.ui.view_map_location.ViewMapLocationActivity;
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
        // Get setting app
        presenter.getSetting();
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
            finish();
        }
    }

    private void settingGPS() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showDialogSettingGps();
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
        } else if (Constant.TRIP_STATUS_ACCEPT.equals(trip.getStatus())) {
            ViewMap viewMap = new ViewMap("", true, Constant.TYPE_PICK_UP, trip);
            GlobalFuntion.goToViewMapLocationActivity(this, viewMap);
        } else if (Constant.TRIP_STATUS_ARRIVED.equals(trip.getStatus())) {
            ViewMap viewMap = new ViewMap("", true, Constant.TYPE_DROP_OFF, trip);
            GlobalFuntion.goToViewMapLocationActivity(this, viewMap);
        } else if (Constant.TRIP_STATUS_ON_GOING.equals(trip.getStatus())) {
            ViewMap viewMap = new ViewMap("", true, Constant.TYPE_DROP_OFF, trip);
            Bundle bundle = new Bundle();
            bundle.putSerializable(Constant.OBJECT_VIEW_MAP, viewMap);
            bundle.putBoolean(Constant.IS_TRIP_GOING, true);
            GlobalFuntion.startActivity(this, ViewMapLocationActivity.class, bundle);
        } else if (Constant.TRIP_STATUS_JOURNEY_COMPLETED.equals(trip.getStatus())) {
            if (Constant.TYPE_PAYMENT_CASH.equals(trip.getPayment_type())) {
                GlobalFuntion.startActivity(this, TripSummaryCashActivity.class);
            } else {
                GlobalFuntion.startActivity(this, TripSummaryCardActivity.class);
            }
        }
        finish();
    }

    public void showDialogSettingGps() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_setting_gps);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);

        // Get view
        final TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        final TextView tvReload = dialog.findViewById(R.id.tv_reload);
        final TextView tvOk = dialog.findViewById(R.id.tv_ok);

        // Get listener
        tvReload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
                startActivity(getIntent());
            }
        });

        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });
        // show dialog
        dialog.show();
    }
}
