package com.app.etow.ui.scheduled_trip.detail;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.models.Trip;
import com.app.etow.models.ViewMap;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.utils.DateTimeUtils;

import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScheduledTripDetailActivity extends BaseMVPDialogActivity implements ScheduledTripDetailMVPView {

    @Inject
    ScheduledTripDetailPresenter presenter;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @BindView(R.id.tv_time)
    TextView tvTime;

    @BindView(R.id.tv_date)
    TextView tvDate;

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

    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.tv_time_countdown)
    TextView tvTimeCountdown;

    private Trip mTripSchedule;
    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        tvTitleToolbar.setText(getString(R.string.scheduled_trips));

        getDataIntent();
        if (mTripSchedule != null) initData();
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTripSchedule = (Trip) bundle.get(Constant.OBJECT_TRIP);
        }
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_scheduled_trip_detail;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.destroyView();
        if (mCountDownTimer != null) mCountDownTimer.cancel();
    }

    @Override
    public void showNoNetworkAlert() {
        showAlert(getString(R.string.error_not_connect_to_internet));
    }

    @Override
    public void onErrorCallApi(int code) {
        GlobalFuntion.showMessageError(this, code);
    }

    private void initData() {
        tvTime.setText(DateTimeUtils.convertTimeStampToFormatDate3(mTripSchedule.getPickup_date()));
        tvDate.setText(DateTimeUtils.convertTimeStampToFormatDate2(mTripSchedule.getPickup_date()));
        tvCustomerName.setText(mTripSchedule.getUser().getFull_name());
        tvPickUp.setText(mTripSchedule.getPick_up());
        tvDropOff.setText(mTripSchedule.getDrop_off());
        if (Constant.TYPE_VEHICLE_NORMAL.equalsIgnoreCase(mTripSchedule.getVehicle_type())) {
            imgVehicleType.setImageResource(R.drawable.ic_car_black);
            tvVehicleType.setText(getString(R.string.type_vehicle_normal));
        } else {
            Drawable myIcon = getResources().getDrawable(R.drawable.ic_vehicle_flatbed_white);
            ColorFilter filter = new LightingColorFilter(Color.BLACK, Color.BLACK);
            myIcon.setColorFilter(filter);
            imgVehicleType.setImageDrawable(myIcon);
            tvVehicleType.setText(getString(R.string.type_vehicle_flatbed));
        }
        if (Constant.TYPE_PAYMENT_CASH.equalsIgnoreCase(mTripSchedule.getPayment_type())) {
            imgPaymentType.setImageResource(R.drawable.ic_cash_black);
            tvPaymentType.setText(getString(R.string.cash));
        } else {
            imgPaymentType.setImageResource(R.drawable.ic_card_black);
            tvPaymentType.setText(getString(R.string.card));
        }
        tvPrice.setText(mTripSchedule.getPrice() + " " + getString(R.string.unit_price));

        long timeExpired = 10 * 60;
        if (GlobalFuntion.mSetting != null) timeExpired = (Integer.parseInt(GlobalFuntion.mSetting.getTimeRequestSchedule())) * 60;
        long timeCountDown = (timeExpired - (Long.parseLong(DateTimeUtils.getCurrentTimeStamp()) -
                Long.parseLong(DateTimeUtils.convertDateToTimeStamp(mTripSchedule.getCreated_at())))) * 1000;
        if (timeCountDown > 0) {
            // Set Event
            mCountDownTimer = new CountDownTimer(timeCountDown, 1000) {

                public void onTick(long millisUntilFinished) {

                    String textTime = String.format(Locale.getDefault(), "%02d:%02d",
                            TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) % 60,
                            TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) % 60);
                    tvTimeCountdown.setText(textTime);
                }

                public void onFinish() {
                    cancel();
                    tvTimeCountdown.setText("00:00");
                }

            }.start();
        }
    }

    @OnClick(R.id.img_back)
    public void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.layout_view_map_pick_up)
    public void onClickLayoutViewMapPickup() {
        ViewMap viewMap = new ViewMap(getString(R.string.scheduled_trips), false,
                Constant.TYPE_PICK_UP, mTripSchedule, false);
        GlobalFuntion.goToViewMapLocationActivity(this, viewMap);
    }

    @OnClick(R.id.layout_view_map_drop_off)
    public void onClickLayoutViewMapDropOff() {
        ViewMap viewMap = new ViewMap(getString(R.string.scheduled_trips), false,
                Constant.TYPE_DROP_OFF, mTripSchedule, false);
        GlobalFuntion.goToViewMapLocationActivity(this, viewMap);
    }

    @OnClick(R.id.tv_confirm)
    public void onClickConfirmTrip() {
        // Todo Check truong hop, khi driver dang o man nay, nhung cron server chay da update trang thai cua Trip la het gio, thi khi click confirm phai thong bao loi.
        presenter.updateTrip(mTripSchedule.getId(), Constant.TRIP_STATUS_ACCEPT + "", "");
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
