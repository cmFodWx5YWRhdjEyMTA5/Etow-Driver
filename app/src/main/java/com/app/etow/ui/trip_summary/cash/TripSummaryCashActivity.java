package com.app.etow.ui.trip_summary.cash;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.data.prefs.DataStoreManager;
import com.app.etow.models.Trip;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.ui.main.MainActivity;
import com.app.etow.utils.DateTimeUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TripSummaryCashActivity extends BaseMVPDialogActivity implements TripSummaryCashMVPView {

    @Inject
    TripSummaryCashPresenter presenter;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @BindView(R.id.tv_trip_no)
    TextView tvTripNo;

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

    @BindView(R.id.tv_price)
    TextView tvPrice;

    @BindView(R.id.layout_check)
    RelativeLayout layoutCheck;

    @BindView(R.id.img_check)
    ImageView imgCheck;

    @BindView(R.id.tv_done)
    TextView tvDone;

    @BindView(R.id.img_back)
    ImageView imgBack;

    private boolean mCheckedCash;
    private Trip mTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        tvTitleToolbar.setText(getString(R.string.trip_summary));
        if (!DataStoreManager.getPrefLanguage()) {
            imgBack.setImageResource(R.drawable.ic_back_black);
        } else {
            imgBack.setImageResource(R.drawable.ic_back_black_right);
        }

        presenter.getTripDetail(this, DataStoreManager.getPrefIdTripProcess());
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_trip_summary_cash;
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

    @Override
    public void updateStatusTrip(Trip trip) {
        mTrip = trip;
        if (Constant.PAYMENT_STATUS_PAYMENT_SUCCESS.equals(trip.getPayment_status())) {
            mCheckedCash = true;
            layoutCheck.setBackgroundResource(R.drawable.shape_circle_black);
            imgCheck.setImageResource(R.drawable.ic_check_white);
            tvDone.setBackgroundResource(R.drawable.bg_black_corner);
            tvDone.setTextColor(getResources().getColor(R.color.white));
        }
        if (Constant.TRIP_STATUS_JOURNEY_COMPLETED == trip.getStatus()) {
            initData(trip);
        } else if (Constant.TRIP_STATUS_COMPLETE == trip.getStatus()) {
            DataStoreManager.setPrefIdTripProcess(0);
            GlobalFuntion.startActivity(this, MainActivity.class);
            finishAffinity();
        }
    }

    private void initData(Trip trip) {
        if (trip != null) {
            tvTripNo.setText(trip.getId() + "");
            tvTime.setText(DateTimeUtils.convertTimeStampToFormatDate3(trip.getPickup_date()));
            tvDate.setText(DateTimeUtils.convertTimeStampToFormatDate2(trip.getPickup_date()));
            tvCustomerName.setText(trip.getUser().getFull_name());
            tvPickUp.setText(trip.getPick_up());
            tvDropOff.setText(trip.getDrop_off());
            tvPrice.setText(trip.getPrice() + " " + getString(R.string.unit_price));
        }
    }

    @OnClick(R.id.img_back)
    public void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.layout_check)
    public void onClickLayoutCheck() {
        presenter.updatePaymentStatus(DataStoreManager.getPrefIdTripProcess(), Constant.TYPE_PAYMENT_CASH,
                Constant.PAYMENT_STATUS_PAYMENT_SUCCESS);
        /*if (!mCheckedCash) {
            mCheckedCash = true;
            layoutCheck.setBackgroundResource(R.drawable.shape_circle_black);
            imgCheck.setImageResource(R.drawable.ic_check_white);
            tvDone.setBackgroundResource(R.drawable.bg_black_corner);
            tvDone.setTextColor(getResources().getColor(R.color.white));
        } else {
            mCheckedCash = false;
            layoutCheck.setBackgroundResource(R.drawable.shape_circle_white);
            imgCheck.setImageResource(R.drawable.ic_check_grey);
            tvDone.setBackgroundResource(R.drawable.bg_grey_corner);
            tvDone.setTextColor(getResources().getColor(R.color.textColorSecondary));
        }*/
    }

    @OnClick(R.id.tv_done)
    public void onClickDone() {
        if (mCheckedCash) {
            if (Constant.TRIP_STATUS_COMPLETE == mTrip.getStatus()) {
                DataStoreManager.setPrefIdTripProcess(0);
                GlobalFuntion.startActivity(this, MainActivity.class);
                finishAffinity();
            } else {
                presenter.updateTrip(DataStoreManager.getPrefIdTripProcess(), Constant.TRIP_STATUS_COMPLETE + "", "");
            }
        }
    }
}
