package com.app.etow.ui.main.home;

/*
 * ******************************************************************************
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/16
 * ******************************************************************************
 */

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.data.prefs.DataStoreManager;
import com.app.etow.ui.base.BaseMVPFragmentWithDialog;
import com.app.etow.ui.main.MainActivity;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeFragment extends BaseMVPFragmentWithDialog implements HomeMVPView, OnMapReadyCallback {

    @Inject
    HomePresenter presenter;

    @BindView(R.id.tv_online)
    TextView tvOnline;

    @BindView(R.id.tv_offline)
    TextView tvOffline;

    private GoogleMap mMap;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this, view);
        presenter.initialView(this);

        SupportMapFragment mMapFragment = new SupportMapFragment();
        mMapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.fragment_view_map));
        mMapFragment.getMapAsync(this);

        loadStatusDriver();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.destroyView();
    }

    @Override
    protected void initToolbar() {

    }

    @Override
    public void onErrorCallApi(int code) {
        GlobalFuntion.showMessageError(getActivity(), code);
    }

    @OnClick(R.id.tv_online)
    public void onClickOnline() {
        if (!Constant.IS_ONLINE.equalsIgnoreCase(DataStoreManager.getUser().getDrivers().getIs_online())) {
            presenter.updateStatusDriver(Constant.IS_ONLINE);
        }
    }

    @OnClick(R.id.tv_offline)
    public void onClickOffline() {
        if (Constant.IS_ONLINE.equalsIgnoreCase(DataStoreManager.getUser().getDrivers().getIs_online())) {
            presenter.updateStatusDriver(Constant.IS_OFFLINE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (GlobalFuntion.LATITUDE > 0 && GlobalFuntion.LONGITUDE > 0) {
            // Add a marker in Sydney, Australia, and move the camera.
            LatLng currentLocation = new LatLng(GlobalFuntion.LATITUDE, GlobalFuntion.LONGITUDE);
            // create marker
            MarkerOptions marker = new MarkerOptions().position(currentLocation)
                    .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location_black));
            // adding marker
            mMap.addMarker(marker);

            CameraUpdate myLoc = CameraUpdateFactory.newCameraPosition(new CameraPosition.Builder()
                    .target(currentLocation).zoom(13).build());
            mMap.moveCamera(myLoc);

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void loadStatusDriver() {
        if (Constant.IS_ONLINE.equalsIgnoreCase(DataStoreManager.getUser().getDrivers().getIs_online())) {
            if (!DataStoreManager.getPrefLanguage()) {
                tvOnline.setBackgroundResource(R.drawable.bg_green_corner_left_bottom);
                tvOffline.setBackgroundResource(R.drawable.bg_grey_corner_right_bottom);
            } else {
                tvOnline.setBackgroundResource(R.drawable.bg_green_corner_right_bottom);
                tvOffline.setBackgroundResource(R.drawable.bg_grey_corner_left_bottom);
            }

            tvOnline.setTextColor(getResources().getColor(R.color.white));
            tvOffline.setTextColor(getResources().getColor(R.color.textColorAccent));
        } else {
            if (!DataStoreManager.getPrefLanguage()) {
                tvOnline.setBackgroundResource(R.drawable.bg_grey_corner_left_bottom);
                tvOffline.setBackgroundResource(R.drawable.bg_red_corner_right_bottom);
            } else {
                tvOnline.setBackgroundResource(R.drawable.bg_grey_corner_right_bottom);
                tvOffline.setBackgroundResource(R.drawable.bg_red_corner_left_bottom);
            }

            tvOnline.setTextColor(getResources().getColor(R.color.textColorAccent));
            tvOffline.setTextColor(getResources().getColor(R.color.white));
        }
    }
}
