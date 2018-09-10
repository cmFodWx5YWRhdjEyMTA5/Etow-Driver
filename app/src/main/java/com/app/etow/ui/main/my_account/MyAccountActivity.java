package com.app.etow.ui.main.my_account;

/*
 * ******************************************************************************
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/16
 * ******************************************************************************
 */

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.data.prefs.DataStoreManager;
import com.app.etow.models.Driver;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.utils.GlideUtils;
import com.app.etow.utils.StringUtil;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAccountActivity extends BaseMVPDialogActivity implements MyAccountMVPView {

    @Inject
    MyAccountPresenter presenter;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @BindView(R.id.img_avatar)
    ImageView imgAvatar;

    @BindView(R.id.tv_driver_name)
    TextView tvDriverName;

    @BindView(R.id.tv_type_vehicle)
    TextView tvTypeVehicle;

    @BindView(R.id.img_type_vehicle)
    ImageView imgTypeVehicle;

    @BindView(R.id.tv_email)
    TextView tvEmail;

    @BindView(R.id.img_back)
    ImageView imgBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        tvTitleToolbar.setText(getString(R.string.my_account));
        if (!DataStoreManager.getPrefLanguage()) {
            imgBack.setImageResource(R.drawable.ic_back_black);
        } else {
            imgBack.setImageResource(R.drawable.ic_back_black_right);
        }
        setUserInfor();
    }

    @Override
    protected boolean bindView() {
        return false;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_my_account;
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

    private void setUserInfor() {
        Driver driver = DataStoreManager.getUser();
        if (!StringUtil.isEmpty(driver.getAvatar())) {
            GlideUtils.loadUrl(driver.getAvatar(), imgAvatar);
        } else {
            imgAvatar.setImageResource(R.drawable.ic_avatar_default);
        }
        tvDriverName.setText(driver.getFull_name());
        tvEmail.setText(driver.getEmail());
        if (Constant.TYPE_VEHICLE_NORMAL.equalsIgnoreCase(driver.getDrivers().getVehicle_type())) {
            imgTypeVehicle.setImageResource(R.drawable.ic_car_black);
            tvTypeVehicle.setText(getString(R.string.type_vehicle_normal));
        } else {
            Drawable myIcon = getResources().getDrawable(R.drawable.ic_vehicle_flatbed_white);
            ColorFilter filter = new LightingColorFilter(Color.BLACK, Color.BLACK);
            myIcon.setColorFilter(filter);
            imgTypeVehicle.setImageDrawable(myIcon);
            tvTypeVehicle.setText(getString(R.string.type_vehicle_flatbed));
        }
    }

    @OnClick(R.id.img_back)
    public void onClickBack() {
        onBackPressed();
    }
}
