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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ahmadrosid.lib.drawroutemap.DrawMarker;
import com.ahmadrosid.lib.drawroutemap.DrawRouteMaps;
import com.app.etow.R;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.models.ViewMap;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.ui.direction_location.DirectionLocationActivity;
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

    @BindView(R.id.tv_type_location)
    TextView tvTypeLocation;

    @BindView(R.id.tv_location)
    TextView tvLocation;

    @BindView(R.id.tv_time_to_location)
    TextView tvTimeToLocation;

    @BindView(R.id.tv_get_direction)
    TextView tvGetDirection;

    private GoogleMap mMap;
    private ViewMap mViewMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        getDataIntent();

        // init map
        SupportMapFragment mMapFragment = new SupportMapFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_view_map, mMapFragment).commit();
        mMapFragment.getMapAsync(this);

        initData();
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mViewMap = (ViewMap) bundle.get(Constant.OBJECT_VIEW_MAP);
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

    private void initData() {
        if (!StringUtil.isEmpty(mViewMap.getTitleToolbar())) {
            layoutHeader.setVisibility(View.VISIBLE);
            tvTitleToolbar.setText(mViewMap.getTitleToolbar());
        } else {
            layoutHeader.setVisibility(View.GONE);
        }

        if (Constant.TYPE_PICK_UP == mViewMap.getTypeLocation()) {
            tvTypeLocation.setText(getString(R.string.pick_up_location_2));
        } else {
            tvTypeLocation.setText(getString(R.string.drop_off_location_2));
        }

        if (mViewMap.isShowDistance()) {
            tvTimeToLocation.setVisibility(View.VISIBLE);
            tvGetDirection.setVisibility(View.VISIBLE);

            String timeToLocation = "";
            if (Constant.TYPE_PICK_UP == mViewMap.getTypeLocation()) {
                timeToLocation = "<font color=#9E9E9D>" + getString(R.string.estimated_time_pick_up_location)
                        + "</font> <b><font color=#121315>"
                        + 13 + "</font></b> <font color=#9E9E9D>"
                        + getString(R.string.min) + "</font>";
            } else {
                timeToLocation = "<font color=#9E9E9D>" + getString(R.string.estimated_time_drop_off_location)
                        + "</font> <b><font color=#121315>"
                        + 13 + "</font></b> <font color=#9E9E9D>"
                        + getString(R.string.min) + "</font>";
            }
            tvTimeToLocation.setText(Html.fromHtml(timeToLocation));
        } else {
            tvTimeToLocation.setVisibility(View.GONE);
            tvGetDirection.setVisibility(View.GONE);
        }
        tvLocation.setText(mViewMap.getLocation());
        LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        GlobalFuntion.getCurrentLocation(this, mLocationManager);
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
        Bundle bundle = new Bundle();
        bundle.putInt(Constant.TYPE_LOCATION, mViewMap.getTypeLocation());
        GlobalFuntion.startActivity(this, DirectionLocationActivity.class, bundle);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng origin = new LatLng(GlobalFuntion.LATITUDE, GlobalFuntion.LONGITUDE);
        LatLng destination = new LatLng(Double.parseDouble(mViewMap.getLatitude()),
                Double.parseDouble(mViewMap.getLongitude()));
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
