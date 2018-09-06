package com.app.etow.ui.trip_upcoming_detail;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.models.Trip;
import com.app.etow.models.ViewMap;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.utils.DateTimeUtils;
import com.app.etow.utils.Utils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TripUpcomingDetailActivity extends BaseMVPDialogActivity implements TripUpcomingDetailMVPView {

    @Inject
    TripUpcomingDetailPresenter presenter;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @BindView(R.id.tv_trip_no)
    TextView tvTripNo;

    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.tv_date)
    TextView tvDate;

    @BindView(R.id.tv_price)
    TextView tvPrice;

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

        tvTitleToolbar.setText(getString(R.string.upcoming_trips));
        getDataIntent();
        initUi();
        presenter.getTripDetail(this, mTrip.getId());
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
        return R.layout.activity_trip_upcoming_detail;
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
        tvTime.setText(DateTimeUtils.convertTimeStampToFormatDate3(mTrip.getPickup_date()));
        tvDate.setText(DateTimeUtils.convertTimeStampToFormatDate2(mTrip.getPickup_date()));
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

    @OnClick(R.id.layout_call_customer)
    public void onClickCallCustomer() {
        showDialogCallCustomer(mTrip.getUser().getPhone());
    }

    @OnClick(R.id.layout_view_map_pick_up)
    public void onClickViewMapPickUp() {
        presenter.updateLocationTrip(Constant.TYPE_PICK_UP, mTrip.getId(), GlobalFuntion.LATITUDE, GlobalFuntion.LONGITUDE);
    }

    @OnClick(R.id.layout_view_map_drop_off)
    public void onClickViewMapDropOff() {
        presenter.updateLocationTrip(Constant.TYPE_DROP_OFF, mTrip.getId(), GlobalFuntion.LATITUDE, GlobalFuntion.LONGITUDE);
    }

    @OnClick(R.id.tv_arrived_at_pickup)
    public void onClickArrivedAtPickup() {
        presenter.updateLocationTrip(Constant.TYPE_DROP_OFF, mTrip.getId(), GlobalFuntion.LATITUDE, GlobalFuntion.LONGITUDE);
    }

    public void showDialogCallCustomer(String phoneNumber) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_call_customer);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setCancelable(false);

        // Get view
        final TextView tvPhoneNumber = dialog.findViewById(R.id.tv_phone_number);
        final TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        final TextView tvCall = dialog.findViewById(R.id.tv_call);

        // Get data
        tvPhoneNumber.setText(phoneNumber);

        // Get listener
        tvCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.callPhoneNumber(TripUpcomingDetailActivity.this, phoneNumber);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void updateStatusTrip(Trip trip) {
        if (Constant.TRIP_STATUS_ARRIVED == trip.getStatus()) {
            ViewMap viewMap = new ViewMap(getString(R.string.upcoming_trips), true,
                    Constant.TYPE_DROP_OFF, mTrip, true);
            GlobalFuntion.goToViewMapLocationActivity(this, viewMap);
            finish();
        }
    }

    @Override
    public void updateLocationSuccess(int type) {
        if (Constant.TYPE_PICK_UP == type) {
            ViewMap viewMap = new ViewMap(getString(R.string.upcoming_trips), true,
                    Constant.TYPE_PICK_UP, mTrip, true);
            GlobalFuntion.goToViewMapLocationActivity(this, viewMap);
            finish();
        } else if (Constant.TYPE_DROP_OFF == type) {
            presenter.updateTrip(mTrip.getId(), Constant.TRIP_STATUS_ARRIVED + "", "");
        }
    }
}
