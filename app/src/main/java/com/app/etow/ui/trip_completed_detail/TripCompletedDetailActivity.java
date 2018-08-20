package com.app.etow.ui.trip_completed_detail;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
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
import com.app.etow.models.Trip;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.utils.DateTimeUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TripCompletedDetailActivity extends BaseMVPDialogActivity implements TripCompletedDetailMVPView {

    @Inject
    TripCompletedDetailPresenter presenter;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @BindView(R.id.tv_trip_no)
    TextView tvTripNo;

    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.tv_date_time)
    TextView tvDateTime;

    @BindView(R.id.tv_customer_name)
    TextView tvCustomerName;

    @BindView(R.id.tv_pick_up)
    TextView tvPickUp;

    @BindView(R.id.tv_drop_off)
    TextView tvDropOff;

    @BindView(R.id.img_vehicle_type)
    ImageView imgVehicleType;

    @BindView(R.id.tv_vehicle_type)
    TextView tvVehicleType;

    @BindView(R.id.tv_driver_name)
    TextView tvDriverName;

    @BindView(R.id.tv_vehicle_number)
    TextView tvVehicleNumber;

    @BindView(R.id.img_payment_type)
    ImageView imgPaymentType;

    @BindView(R.id.tv_payment_type)
    TextView tvPaymentType;

    private Trip mTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        tvTitleToolbar.setText(getString(R.string.completed_trips));
        getDataIntent();

        initUi();
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTrip = (Trip) bundle.get(Constant.OBJECT_TRIP);
        }
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_trip_completed_detail;
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

    private void initUi() {
        tvTripNo.setText(mTrip.getId() + "");
        tvDateTime.setText(DateTimeUtils.convertTimeStampToDateFormat6(mTrip.getPickup_date()));
        tvPrice.setText(mTrip.getPrice() + " " + getString(R.string.unit_price));
        tvCustomerName.setText(mTrip.getUser().getFull_name());
        tvPickUp.setText(mTrip.getPick_up());
        tvDropOff.setText(mTrip.getDrop_off());
        if (Constant.TYPE_VEHICLE_NORMAL.equalsIgnoreCase(mTrip.getVehicle_type())) {
            imgVehicleType.setImageResource(R.drawable.ic_car_black);
            tvVehicleType.setText(getString(R.string.type_vehicle_normal));
        } else {
            Drawable myIcon = getResources().getDrawable(R.drawable.ic_vehicle_flatbed_white);
            ColorFilter filter = new LightingColorFilter(Color.BLACK, Color.BLACK);
            myIcon.setColorFilter(filter);
            imgVehicleType.setImageDrawable(myIcon);
            tvVehicleType.setText(getString(R.string.type_vehicle_flatbed));
        }
        tvDriverName.setText(mTrip.getDriver().getName());
        tvVehicleNumber.setText(mTrip.getDriver().getVehicle_number());
        if (Constant.TYPE_PAYMENT_CASH.equalsIgnoreCase(mTrip.getPayment_type())) {
            imgPaymentType.setImageResource(R.drawable.ic_cash_black);
            tvPaymentType.setText(getString(R.string.cash));
        } else {
            imgPaymentType.setImageResource(R.drawable.ic_card_black);
            tvPaymentType.setText(getString(R.string.card));
        }
    }

    @OnClick(R.id.img_back)
    public void onClickBack() {
        onBackPressed();
    }
}
