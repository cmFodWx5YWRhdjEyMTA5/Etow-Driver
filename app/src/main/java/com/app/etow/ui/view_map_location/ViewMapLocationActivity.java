package com.app.etow.ui.view_map_location;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.content.Context;
import android.graphics.Point;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.app.etow.R;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.data.prefs.DataStoreManager;
import com.app.etow.models.Trip;
import com.app.etow.models.ViewMap;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.ui.trip_summary.card.TripSummaryCardActivity;
import com.app.etow.ui.trip_summary.cash.TripSummaryCashActivity;
import com.app.etow.utils.StringUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewMapLocationActivity extends BaseMVPDialogActivity implements ViewMapLocationMVPView,
        OnMapReadyCallback {

    @Inject
    ViewMapLocationPresenter presenter;

    @BindView(R.id.layout_header)
    RelativeLayout layoutHeader;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @BindView(R.id.layout_get_direction)
    LinearLayout layoutGetDirection;

    @BindView(R.id.tv_type_location)
    TextView tvTypeLocation;

    @BindView(R.id.tv_location)
    TextView tvLocation;

    @BindView(R.id.tv_time_to_location)
    TextView tvTimeToLocation;

    @BindView(R.id.tv_get_direction)
    TextView tvGetDirection;

    @BindView(R.id.layout_direction_location)
    LinearLayout layoutDirectionLocation;

    @BindView(R.id.tv_title_direction)
    TextView tvTitleDirection;

    @BindView(R.id.tv_action_update)
    TextView tvActionUpdate;

    private GoogleMap mMap;
    private ViewMap mViewMap;
    private boolean mIsTripGoing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        getDataIntent();

        // init map
        SupportMapFragment mMapFragment = new SupportMapFragment();
        mMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_view_map));
        mMapFragment.getMapAsync(this);

        DataStoreManager.setPrefIdTripProcess(mViewMap.getTrip().getId());
        presenter.getTripDetail(this, DataStoreManager.getPrefIdTripProcess());
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mViewMap = (ViewMap) bundle.get(Constant.OBJECT_VIEW_MAP);
            mIsTripGoing = bundle.getBoolean(Constant.IS_TRIP_GOING, false);
        }
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_view_map_location;
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        GlobalFuntion.getCurrentLocation(this, mLocationManager);
    }

    private void initData() {
        if (!StringUtil.isEmpty(mViewMap.getTitleToolbar())) {
            layoutHeader.setVisibility(View.VISIBLE);
            tvTitleToolbar.setText(mViewMap.getTitleToolbar());
        } else {
            layoutHeader.setVisibility(View.GONE);
        }

        if (Constant.TYPE_PICK_UP == mViewMap.getTypeLocation()) {
            tvTypeLocation.setText(getString(R.string.pick_up_location_2));
            tvLocation.setText(mViewMap.getTrip().getPick_up());
        } else {
            tvTypeLocation.setText(getString(R.string.drop_off_location_2));
            tvLocation.setText(mViewMap.getTrip().getDrop_off());
            if (mIsTripGoing) {
                layoutGetDirection.setVisibility(View.GONE);
                layoutDirectionLocation.setVisibility(View.VISIBLE);

                tvTitleDirection.setText(getString(R.string.drop_off_location_2));
                tvActionUpdate.setText(getString(R.string.journey_completed));
            }
        }

        if (mViewMap.isShowDistance()) {
            tvTimeToLocation.setVisibility(View.VISIBLE);
            tvGetDirection.setVisibility(View.VISIBLE);

            String timeToLocation = "";
            if (Constant.TYPE_PICK_UP == mViewMap.getTypeLocation()) {
                int distance = GlobalFuntion.getDistanceFromLocation(GlobalFuntion.LATITUDE,
                        GlobalFuntion.LATITUDE,
                        Double.parseDouble(mViewMap.getTrip().getPickup_latitude()),
                        Double.parseDouble(mViewMap.getTrip().getPickup_longitude()));
                int timePickup = 0;
                if (GlobalFuntion.mSetting != null) {
                    timePickup = distance * Integer.parseInt(GlobalFuntion.mSetting.getTimeDistance());
                }
                if (timePickup < 1) timePickup = 1;
                timeToLocation = "<font color=#6D6E70>" + getString(R.string.estimated_time_pick_up_location)
                        + "</font> <b><font color=#121315>"
                        + timePickup + "</font></b> <font color=#6D6E70>"
                        + getString(R.string.label_minute) + "</font>";
            } else {
                int distance = GlobalFuntion.getDistanceFromLocation(GlobalFuntion.LATITUDE, GlobalFuntion.LATITUDE,
                        Double.parseDouble(mViewMap.getTrip().getDropoff_latitude()),
                        Double.parseDouble(mViewMap.getTrip().getDropoff_longitude()));
                int timeDropOff = 0;
                if (GlobalFuntion.mSetting != null) {
                    timeDropOff = distance * Integer.parseInt(GlobalFuntion.mSetting.getTimeDistance());
                }
                if (timeDropOff < 1) timeDropOff = 1;
                timeToLocation = "<font color=#6D6E70>" + getString(R.string.estimated_time_drop_off_location)
                        + "</font> <b><font color=#121315>"
                        + timeDropOff + "</font></b> <font color=#6D6E70>"
                        + getString(R.string.label_minute) + "</font>";
            }
            tvTimeToLocation.setText(Html.fromHtml(timeToLocation));
        } else {
            tvTimeToLocation.setVisibility(View.GONE);
            tvGetDirection.setVisibility(View.GONE);
        }
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

    @OnClick(R.id.img_back)
    public void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.tv_get_direction)
    public void onClickGetDirection() {
        if (Constant.TYPE_PICK_UP == mViewMap.getTypeLocation()) {
            layoutGetDirection.setVisibility(View.GONE);
            layoutDirectionLocation.setVisibility(View.VISIBLE);

            tvTitleDirection.setText(getString(R.string.pick_up_location_2));
            tvActionUpdate.setText(getString(R.string.arrived_for_pick_up));
        } else {
            presenter.updateTrip(DataStoreManager.getPrefIdTripProcess(), Constant.TRIP_STATUS_ON_GOING, "");
        }
    }

    @OnClick(R.id.tv_action_update)
    public void onClickActionUpdate() {
        if (Constant.TYPE_PICK_UP == mViewMap.getTypeLocation()) {
            presenter.updateTrip(DataStoreManager.getPrefIdTripProcess(), Constant.TRIP_STATUS_ARRIVED, "");
        } else {
            presenter.updateTrip(DataStoreManager.getPrefIdTripProcess(), Constant.TRIP_STATUS_JOURNEY_COMPLETED, "");
        }
    }

    @Override
    public void updateStatusTrip(Trip trip) {
        mViewMap.setTrip(trip);
        if (Constant.TRIP_STATUS_ACCEPT.equals(trip.getStatus())) {
            initData();
        } else if (Constant.TRIP_STATUS_ARRIVED.equals(trip.getStatus())) {
            layoutDirectionLocation.setVisibility(View.GONE);
            layoutGetDirection.setVisibility(View.VISIBLE);
            mViewMap.setTypeLocation(Constant.TYPE_DROP_OFF);
            initData();
        } else if (Constant.TRIP_STATUS_ON_GOING.equals(trip.getStatus())) {
            layoutGetDirection.setVisibility(View.GONE);
            layoutDirectionLocation.setVisibility(View.VISIBLE);

            tvTitleDirection.setText(getString(R.string.drop_off_location_2));
            tvActionUpdate.setText(getString(R.string.journey_completed));
        } else if (Constant.TRIP_STATUS_JOURNEY_COMPLETED.equals(trip.getStatus())) {
            if (Constant.TYPE_PAYMENT_CASH.equals(trip.getPayment_type())) {
                GlobalFuntion.startActivity(this, TripSummaryCashActivity.class);
            } else {
                GlobalFuntion.startActivity(this, TripSummaryCardActivity.class);
            }
            finish();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng origin = new LatLng(GlobalFuntion.LATITUDE, GlobalFuntion.LONGITUDE);
        LatLng destination = null;
        if (Constant.TYPE_PICK_UP == mViewMap.getTypeLocation()) {
            destination = new LatLng(Double.parseDouble(mViewMap.getTrip().getPickup_latitude()),
                    Double.parseDouble(mViewMap.getTrip().getPickup_longitude()));
        } else {
            destination = new LatLng(Double.parseDouble(mViewMap.getTrip().getDropoff_latitude()),
                    Double.parseDouble(mViewMap.getTrip().getDropoff_longitude()));
        }
        DrawRouteMaps.getInstance(this)
                .draw(origin, destination, mMap);
        DrawMarker.getInstance(this).draw(mMap, origin, R.drawable.ic_location_black, "");
        DrawMarker.getInstance(this).draw(mMap, destination, R.drawable.ic_location_black, "");

        LatLngBounds bounds = new LatLngBounds.Builder()
                .include(origin)
                .include(destination).build();
        Point displaySize = new Point();
        getWindowManager().getDefaultDisplay().getSize(displaySize);
        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, displaySize.x, 250, 30));
    }
}
