package com.app.etow.ui.incoming_request;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/05/13
 */

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.LightingColorFilter;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.models.Trip;
import com.app.etow.models.ViewMap;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.utils.DateTimeUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IncomingRequestActivity extends BaseMVPDialogActivity implements IncomingRequestMVPView {

    @Inject
    IncomingRequestPresenter presenter;

    @BindView(R.id.img_back)
    ImageView imgBack;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @BindView(R.id.tv_customer_name)
    TextView tvCustomerName;

    @BindView(R.id.tv_date_time)
    TextView tvDateTime;

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

    private Trip mTripIncoming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        imgBack.setVisibility(View.GONE);
        tvTitleToolbar.setText(getString(R.string.incoming_request));

        getDataIntent();
        initData();
    }

    private void getDataIntent() {
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            mTripIncoming = (Trip) bundle.get(Constant.OBJECT_TRIP);
        }
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_incoming_request;
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

    private void initData() {
        if (mTripIncoming != null) {
            tvCustomerName.setText(mTripIncoming.getUser().getFull_name());
            tvDateTime.setText(DateTimeUtils.convertTimeStampToDateFormat5(mTripIncoming.getPickup_date()));
            tvPickUp.setText(mTripIncoming.getPick_up());
            tvDropOff.setText(mTripIncoming.getDrop_off());
            if (Constant.TYPE_VEHICLE_NORMAL.equalsIgnoreCase(mTripIncoming.getVehicle_type())) {
                imgVehicleType.setImageResource(R.drawable.ic_car_black);
                tvVehicleType.setText(getString(R.string.type_vehicle_normal));
            } else {
                Drawable myIcon = getResources().getDrawable(R.drawable.ic_vehicle_flatbed_white);
                ColorFilter filter = new LightingColorFilter(Color.BLACK, Color.BLACK);
                myIcon.setColorFilter(filter);
                imgVehicleType.setImageDrawable(myIcon);
                tvVehicleType.setText(getString(R.string.type_vehicle_flatbed));
            }
            if (Constant.TYPE_PAYMENT_CASH.equalsIgnoreCase(mTripIncoming.getPayment_type())) {
                imgPaymentType.setImageResource(R.drawable.ic_cash_black);
                tvPaymentType.setText(getString(R.string.cash));
            } else {
                imgPaymentType.setImageResource(R.drawable.ic_card_black);
                tvPaymentType.setText(getString(R.string.card));
            }
            tvPrice.setText(mTripIncoming.getPrice() + " " + getString(R.string.unit_price));
        }
    }

    @OnClick(R.id.layout_view_map_pick_up)
    public void onClickViewMapPickUp() {
        ViewMap viewMap = new ViewMap(getString(R.string.incoming_request), false,
                Constant.TYPE_PICK_UP, "", "", "");
        GlobalFuntion.goToViewMapLocationActivity(this, viewMap);
    }

    @OnClick(R.id.layout_view_map_drop_off)
    public void onClickViewMapDropOff() {
        ViewMap viewMap = new ViewMap(getString(R.string.incoming_request), false,
                Constant.TYPE_DROP_OFF, "", "", "");
        GlobalFuntion.goToViewMapLocationActivity(this, viewMap);
    }

    @OnClick(R.id.tv_reject)
    public void onClickReject() {
        showDialogReject();
    }

    @OnClick(R.id.tv_accept)
    public void onClickAccept() {
        ViewMap viewMap = new ViewMap("", true, Constant.TYPE_PICK_UP,
                "", "", "");
        GlobalFuntion.goToViewMapLocationActivity(this, viewMap);
    }

    public void showDialogReject() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_reject);
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
                dialog.dismiss();
                showDialogReasonReject();
            }
        });

        dialog.show();
    }

    public void showDialogReasonReject() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_reason_reject);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setCancelable(false);

        // Get view
        final RadioGroup rdgReasonReject = dialog.findViewById(R.id.rdg_reason_reject);
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
                if (rdgReasonReject.getCheckedRadioButtonId() == -1) {
                    showAlert(getString(R.string.specify_reason_rejecting));
                } else {
                    //Todo
                    dialog.dismiss();
                    finish();
                }
            }
        });

        dialog.show();
    }
}
