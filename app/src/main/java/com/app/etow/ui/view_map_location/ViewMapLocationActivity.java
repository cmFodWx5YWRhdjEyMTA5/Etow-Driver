package com.app.etow.ui.view_map_location;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.adapter.DirectionAdapter;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.data.prefs.DataStoreManager;
import com.app.etow.direction.DirectionFinder;
import com.app.etow.direction.DirectionFinderListener;
import com.app.etow.direction.Route;
import com.app.etow.direction.Step;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ViewMapLocationActivity extends BaseMVPDialogActivity implements ViewMapLocationMVPView,
        OnMapReadyCallback, DirectionFinderListener {

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

    @BindView(R.id.rcv_direction)
    RecyclerView rcvDirection;

    private GoogleMap mMap;
    private ViewMap mViewMap;
    private boolean mIsTripGoing;
    private DirectionAdapter directionAdapter;

    private List<Marker> originMarkers = new ArrayList<>();
    private List<Marker> destinationMarkers = new ArrayList<>();
    private List<Polyline> polylinePaths = new ArrayList<>();

    private ArrayList<LatLng> mListPoints = new ArrayList<LatLng>();
    private Polyline mPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        // Get data intent
        getDataIntent();

        // init map
        SupportMapFragment mMapFragment = new SupportMapFragment();
        mMapFragment = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_view_map));
        mMapFragment.getMapAsync(this);
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

    @Override
    protected void onDestroy() {
        presenter.destroyView();
        if (directionAdapter != null) directionAdapter.release();
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
            getLocationChange();
        } else {
            mListPoints = new ArrayList<>();
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
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.setMyLocationEnabled(true);

        DataStoreManager.setPrefIdTripProcess(mViewMap.getTrip().getId());
        presenter.getTripDetail(this, DataStoreManager.getPrefIdTripProcess());
        initData();
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
            presenter.updateLocationTrip(DataStoreManager.getPrefIdTripProcess(), GlobalFuntion.LATITUDE, GlobalFuntion.LONGITUDE);
        } else {
            tvTimeToLocation.setVisibility(View.GONE);
            tvGetDirection.setVisibility(View.GONE);
        }
    }

    private void initMapLocation() {
        if (Constant.TYPE_PICK_UP == mViewMap.getTypeLocation()) {
            String strCurrentLocation = GlobalFuntion.getCompleteAddressString(this, GlobalFuntion.LATITUDE, GlobalFuntion.LONGITUDE);
            String strPickUp = mViewMap.getTrip().getPick_up();
            if (StringUtil.isEmpty(strCurrentLocation)) {
                showAlert(getString(R.string.unble_trace_location));
            } else {
                sendRequestDirection(strCurrentLocation, strPickUp);
            }
        } else {
            String strCurrentLocation = GlobalFuntion.getCompleteAddressString(this, GlobalFuntion.LATITUDE, GlobalFuntion.LONGITUDE);
            String strDropOff = mViewMap.getTrip().getDrop_off();
            if (StringUtil.isEmpty(strCurrentLocation)) {
                showAlert(getString(R.string.unble_trace_location));
            } else {
                sendRequestDirection(strCurrentLocation, strDropOff);
            }
        }
    }

    @Override
    public void updateStatusTrip(Trip trip) {
        mViewMap.setTrip(trip);
        initMapLocation();
        if (Constant.TRIP_STATUS_ARRIVED.equals(trip.getStatus())) {
            layoutDirectionLocation.setVisibility(View.GONE);
            layoutGetDirection.setVisibility(View.VISIBLE);
            mViewMap.setTypeLocation(Constant.TYPE_DROP_OFF);
            mMap.clear();
            initData();
        } else if (Constant.TRIP_STATUS_ON_GOING.equals(trip.getStatus())) {
            layoutGetDirection.setVisibility(View.GONE);
            layoutDirectionLocation.setVisibility(View.VISIBLE);
            tvTitleDirection.setText(getString(R.string.drop_off_location_2));
            tvActionUpdate.setText(getString(R.string.journey_completed));
            getLocationChange();
        } else if (Constant.TRIP_STATUS_JOURNEY_COMPLETED.equals(trip.getStatus())) {
            if (Constant.TYPE_PAYMENT_CASH.equals(trip.getPayment_type())) {
                GlobalFuntion.startActivity(this, TripSummaryCashActivity.class);
            } else {
                GlobalFuntion.startActivity(this, TripSummaryCardActivity.class);
            }
            finish();
        }
    }

    private void sendRequestDirection(String origin, String destination) {
        try {
            new DirectionFinder(this, origin, destination).execute();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDirectionFinderStart() {
        showProgressDialog(true);
        if (originMarkers != null) {
            for (Marker marker : originMarkers) {
                marker.remove();
            }
        }

        if (destinationMarkers != null) {
            for (Marker marker : destinationMarkers) {
                marker.remove();
            }
        }

        if (polylinePaths != null) {
            for (Polyline polyline : polylinePaths) {
                polyline.remove();
            }
        }
    }

    @Override
    public void onDirectionFinderSuccess(List<Route> routes) {
        showProgressDialog(false);
        polylinePaths = new ArrayList<>();
        originMarkers = new ArrayList<>();
        destinationMarkers = new ArrayList<>();

        for (Route route : routes) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(route.startLocation, 16));
            // ((TextView) findViewById(R.id.tvDuration)).setText(route.duration.text);
            // ((TextView) findViewById(R.id.tvDistance)).setText(route.distance.text);
            String timeToLocation = "";
            if (Constant.TYPE_PICK_UP == mViewMap.getTypeLocation()) {
                timeToLocation = "<font color=#6D6E70>" + getString(R.string.estimated_time_pick_up_location)
                        + "</font> <b><font color=#121315>"
                        + route.duration.text + "</font></b> <font color=#6D6E70></font>";
            } else {
                timeToLocation = "<font color=#6D6E70>" + getString(R.string.estimated_time_drop_off_location)
                        + "</font> <b><font color=#121315>"
                        + route.duration.text + "</font></b> <font color=#6D6E70></font>";
            }
            tvTimeToLocation.setText(Html.fromHtml(timeToLocation));

            originMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_black))
                    .title(route.startAddress)
                    .position(route.startLocation)));
            destinationMarkers.add(mMap.addMarker(new MarkerOptions()
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_black))
                    .title(route.endAddress)
                    .position(route.endLocation)));

            PolylineOptions polylineOptions = new PolylineOptions().
                    geodesic(true).
                    color(Color.BLACK).
                    width(10);

            for (int i = 0; i < route.points.size(); i++)
                polylineOptions.add(route.points.get(i));

            polylinePaths.add(mMap.addPolyline(polylineOptions));

            setDirection(route.steps);
        }
    }

    public void getLocationChange() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 20000, 0,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        double latitude = location.getLatitude();
                        double longitude = location.getLongitude();
                        if (latitude > 0 && longitude > 0) {
                            presenter.updateLocationTrip(DataStoreManager.getPrefIdTripProcess(), latitude, longitude);
                            LatLng latLng = new LatLng(latitude, longitude);
                            mListPoints.add(latLng);
                            redrawLine();
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
    }

    private void redrawLine() {
        // googleMap.clear();  //clears all Markers and Polylines
        PolylineOptions options = new PolylineOptions().width(10)
                .color(getResources().getColor(R.color.colorRouteLine)).geodesic(true);
        for (int i = 0; i < mListPoints.size(); i++) {
            LatLng point = mListPoints.get(i);
            options.add(point);
        }
        // addMarker(); //add Marker in current position
        mPolyline = mMap.addPolyline(options); //add Polyline
    }

    private void setDirection(List<Step> steps) {
        directionAdapter = new DirectionAdapter(this, steps);
        directionAdapter.injectInto(rcvDirection);
        rcvDirection.setAdapter(directionAdapter);
    }
}
