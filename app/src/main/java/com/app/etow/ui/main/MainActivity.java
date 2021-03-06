package com.app.etow.ui.main;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.app.etow.R;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.data.prefs.DataStoreManager;
import com.app.etow.models.Trip;
import com.app.etow.models.ViewMap;
import com.app.etow.ui.auth.SignInActivity;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.ui.incoming_request.IncomingRequestActivity;
import com.app.etow.ui.main.get_in_touch.GetInTouchFragment;
import com.app.etow.ui.main.home.HomeFragment;
import com.app.etow.ui.main.my_account.MyAccountActivity;
import com.app.etow.ui.main.my_bookings.MyBookingsActivity;
import com.app.etow.ui.main.term_and_condition.TermAndConditionFragment;
import com.app.etow.ui.scheduled_trip.ScheduledTripActivity;
import com.app.etow.ui.trip_summary.card.TripSummaryCardActivity;
import com.app.etow.ui.trip_summary.cash.TripSummaryCashActivity;
import com.app.etow.ui.view_map_location.ViewMapLocationActivity;
import com.app.etow.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseMVPDialogActivity implements MainMVPView {

    private static final int FRAGMENT_HOME = 1;
    private static final int FRAGMENT_GET_IN_TOUCH = 2;
    private static final int FRAGMENT_TERMS_AND_CONDITIONS = 3;

    @Inject
    MainPresenter presenter;

    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawerLayout;

    @BindView(R.id.img_back)
    ImageView imgBack;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @BindView(R.id.tv_count_trip_schedule)
    TextView tvCountTripSchedule;

    @BindView(R.id.tv_title_header)
    TextView tvTitleHeader;

    @BindView(R.id.tv_english)
    TextView tvEnglish;

    @BindView(R.id.tv_urdu)
    TextView tvUrdu;

    @BindView(R.id.layout_notification)
    RelativeLayout layoutNotification;

    boolean isLanguageEnlish = true;
    private int mCurrentFragment = FRAGMENT_HOME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        // set menu
        imgBack.setImageResource(R.drawable.ic_close_black);
        tvTitleToolbar.setText(getString(R.string.menu));

        presenter.getScheduleTrip(this);
        int tripProcessId = DataStoreManager.getPrefIdTripProcess();
        if (tripProcessId != 0) {
            presenter.getTripDetail(this, tripProcessId);
        } else {
            presenter.getTripIncoming(this);
        }

        setListenerDrawer();
        replaceFragment(new HomeFragment(), HomeFragment.class.getName());
        // getLocationChange();
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_main;
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        GlobalFuntion.getCurrentLocation(this, mLocationManager);
        // Update location driver
        // presenter.updateLocationDriver(GlobalFuntion.LATITUDE, GlobalFuntion.LONGITUDE);
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

    private void setListenerDrawer() {
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                GlobalFuntion.hideSoftKeyboard(MainActivity.this);
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            showDialogLogout();
        }
    }

    private void showDialogLogout() {
        MaterialDialog materialDialog = new MaterialDialog.Builder(this)
                .title(getString(R.string.app_name))
                .content(getString(R.string.msg_exit_app))
                .positiveText(getString(R.string.action_ok))
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        finishAffinity();
                    }
                })
                .negativeText(getString(R.string.action_cancel))
                .cancelable(false)
                .show();
    }

    public void replaceFragment(Fragment fragment, String tag) {
        final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, tag);
        ft.commit();
    }

    public void showAndHiddenItemToolbar(String title, boolean isShowNotification) {
        tvTitleHeader.setText(title);
        if (isShowNotification) layoutNotification.setVisibility(View.VISIBLE);
        else layoutNotification.setVisibility(View.GONE);
    }

    @OnClick({R.id.img_back, R.id.img_menu, R.id.tv_menu_home, R.id.tv_menu_my_account,
            R.id.tv_menu_my_bookings, R.id.tv_menu_get_in_touch, R.id.tv_menu_term_and_condition,
            R.id.tv_logout, R.id.tv_language, R.id.tv_english, R.id.tv_urdu, R.id.layout_notification})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                break;

            case R.id.img_menu:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;

            case R.id.tv_menu_home:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                if (mCurrentFragment != FRAGMENT_HOME) {
                    replaceFragment(new HomeFragment(), HomeFragment.class.getName());
                    mCurrentFragment = FRAGMENT_HOME;
                }
                break;

            case R.id.tv_menu_my_account:
                GlobalFuntion.startActivity(this, MyAccountActivity.class);
                break;

            case R.id.tv_menu_my_bookings:
                GlobalFuntion.startActivity(this, MyBookingsActivity.class);
                break;

            case R.id.tv_menu_get_in_touch:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                if (mCurrentFragment != FRAGMENT_GET_IN_TOUCH) {
                    replaceFragment(new GetInTouchFragment(), GetInTouchFragment.class.getName());
                    mCurrentFragment = FRAGMENT_GET_IN_TOUCH;
                }
                break;

            case R.id.tv_menu_term_and_condition:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                if (mCurrentFragment != FRAGMENT_TERMS_AND_CONDITIONS) {
                    replaceFragment(new TermAndConditionFragment(), TermAndConditionFragment.class.getName());
                    mCurrentFragment = FRAGMENT_TERMS_AND_CONDITIONS;
                }
                break;

            case R.id.tv_logout:
                presenter.logout(DataStoreManager.getUserToken());
                break;

            case R.id.tv_language:
                // Not do anything
                break;

            case R.id.tv_english:
                if (!isLanguageEnlish) {
                    isLanguageEnlish = true;
                    tvEnglish.setBackgroundResource(R.drawable.bg_black_corner_left_bottom);
                    tvUrdu.setBackgroundResource(R.drawable.bg_grey_corner_right_bottom);
                    tvEnglish.setTextColor(getResources().getColor(R.color.white));
                    tvUrdu.setTextColor(getResources().getColor(R.color.textColorAccent));
                }
                break;

            case R.id.tv_urdu:
                if (isLanguageEnlish) {
                    isLanguageEnlish = false;
                    tvEnglish.setBackgroundResource(R.drawable.bg_grey_corner_left_bottom);
                    tvUrdu.setBackgroundResource(R.drawable.bg_black_corner_right_bottom);
                    tvEnglish.setTextColor(getResources().getColor(R.color.textColorAccent));
                    tvUrdu.setTextColor(getResources().getColor(R.color.white));
                }
                break;

            case R.id.layout_notification:
                GlobalFuntion.startActivity(this, ScheduledTripActivity.class);
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Utils.callPhoneNumber(this, getString(R.string.phone_number_call_us));
            } else {
                Log.e("Permission", "Permission not Granted");
            }
        }
    }

    @Override
    public void logout() {
        GlobalFuntion.startActivity(this, SignInActivity.class);
        finishAffinity();
    }

    @Override
    public void loadListTripSchedule(int count) {
        if (count > 0) {
            layoutNotification.setVisibility(View.VISIBLE);
            tvCountTripSchedule.setText(count + "");
        } else {
            layoutNotification.setVisibility(View.GONE);
        }
    }

    @Override
    public void getTripIncoming(Trip trip) {
        if (Constant.IS_ONLINE.equalsIgnoreCase(DataStoreManager.getUser().getDrivers().getIs_online())
                && DataStoreManager.getPrefIdTripProcess() == 0 &&
                DataStoreManager.getUser().getDrivers().getVehicle_type().equals(trip.getVehicle_type())) {
            boolean isDriverRejected = false;
            if (trip.getRejects() != null && trip.getRejects().size() > 0) {
                for (int i = 0; i < trip.getRejects().size(); i++) {
                    if (DataStoreManager.getUser().getId() == Integer.parseInt(trip.getRejects().get(i).getDriver_id())) {
                        isDriverRejected = true;
                        break;
                    }
                }
            }
            if (!isDriverRejected) {
                DataStoreManager.setPrefIdTripProcess(trip.getId());
                GlobalFuntion.startActivity(this, IncomingRequestActivity.class);
                finish();
            }
        }
    }

    /*public void getLocationChange() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 0,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        if (latitude > 0 && longitude > 0) {
                            // Update location driver
                            presenter.updateLocationDriver(latitude, longitude);
                        }
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                });
    }*/

    @Override
    public void getTripDetail(Trip trip) {
        if (DataStoreManager.getUser().getId() == Integer.parseInt(trip.getDriver_id())) {
            if (Constant.TRIP_STATUS_NEW.equals(trip.getStatus())) {
                GlobalFuntion.startActivity(this, IncomingRequestActivity.class);
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
        } else {
            presenter.getTripIncoming(this);
        }
    }
}
