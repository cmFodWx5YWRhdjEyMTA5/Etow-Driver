package com.app.etow.ui.trip_summary.card;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

public class TripSummaryCardActivity extends BaseMVPDialogActivity implements TripSummaryCardMVPView {

    @Inject
    TripSummaryCardPresenter presenter;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @BindView(R.id.tv_payment_status)
    TextView tvPaymentStatus;

    @BindView(R.id.layout_cash)
    LinearLayout layoutCash;

    @BindView(R.id.layout_check)
    RelativeLayout layoutCheck;

    @BindView(R.id.img_check)
    ImageView imgCheck;

    @BindView(R.id.tv_done)
    TextView tvDone;

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

    private boolean mCheckedActiveDone;
    private Trip mTrip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        tvTitleToolbar.setText(getString(R.string.trip_summary));

        presenter.getTripDetail(this, DataStoreManager.getPrefIdTripProcess());
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_trip_summary_card;
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

    @OnClick(R.id.tv_done)
    public void onClickDone() {
        if (mCheckedActiveDone) {
            if (Constant.TYPE_PAYMENT_CASH.equals(mTrip.getPayment_type())) {
                showDialogConfirmCashReceived();
            } else {
                presenter.updateTrip(DataStoreManager.getPrefIdTripProcess(), Constant.TRIP_STATUS_COMPLETE + "", "");
            }
        }
    }

    public void showDialogConfirmCashReceived() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_confirm_cash_received);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setCancelable(false);

        // Get view
        final TextView tvNo = dialog.findViewById(R.id.tv_no);
        final TextView tvYes = dialog.findViewById(R.id.tv_yes);

        // Get listener
        tvNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        tvYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.updateTrip(DataStoreManager.getPrefIdTripProcess(), Constant.TRIP_STATUS_COMPLETE + "", "");
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public void updateStatusTrip(Trip trip) {
        mTrip = trip;
        if (Constant.PAYMENT_STATUS_PAYMENT_SUCCESS.equals(trip.getPayment_status())) {
            mCheckedActiveDone = true;
            if (Constant.TYPE_PAYMENT_CASH.equals(trip.getPayment_type())) {
                layoutCheck.setBackgroundResource(R.drawable.shape_circle_black);
                imgCheck.setImageResource(R.drawable.ic_check_white);
                tvDone.setBackgroundResource(R.drawable.bg_black_corner);
                tvDone.setTextColor(getResources().getColor(R.color.white));
            } else {
                tvPaymentStatus.setText(getString(R.string.payment_successful));
                tvPaymentStatus.setTextColor(getResources().getColor(R.color.button_green));
                tvDone.setVisibility(View.VISIBLE);
            }
        } else if (Constant.PAYMENT_STATUS_PAYMENT_PENDING.equals(trip.getPayment_status())) {
            tvPaymentStatus.setText(getString(R.string.payment_processing));
            tvPaymentStatus.setTextColor(getResources().getColor(R.color.orange));
        } else if (Constant.PAYMENT_STATUS_PAYMENT_FAIL.equals(trip.getPayment_status())) {
            tvPaymentStatus.setText(getString(R.string.payment_declined));
            tvPaymentStatus.setTextColor(getResources().getColor(R.color.button_red));
            layoutCash.setVisibility(View.VISIBLE);
            tvDone.setVisibility(View.VISIBLE);
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
}
