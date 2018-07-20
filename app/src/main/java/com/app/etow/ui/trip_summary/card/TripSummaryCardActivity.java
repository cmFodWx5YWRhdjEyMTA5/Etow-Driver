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
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.ui.base.BaseMVPDialogActivity;

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

    private boolean mCheckedCash;

    //Fix code
    boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        tvTitleToolbar.setText(getString(R.string.trip_summary));

        initUi();
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

    private void initUi() {
        tvPaymentStatus.setText(getString(R.string.payment_processing));
        tvPaymentStatus.setTextColor(getResources().getColor(R.color.orange));

        //Fix code
        tvPaymentStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!check) {
                    check = true;
                    tvPaymentStatus.setText(getString(R.string.payment_successful));
                    tvPaymentStatus.setTextColor(getResources().getColor(R.color.button_green));
                    tvDone.setVisibility(View.VISIBLE);
                } else {
                    tvPaymentStatus.setText(getString(R.string.payment_declined));
                    tvPaymentStatus.setTextColor(getResources().getColor(R.color.button_red));
                    layoutCash.setVisibility(View.VISIBLE);
                    tvDone.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @OnClick(R.id.img_back)
    public void onClickBack() {
        onBackPressed();
    }

    @OnClick(R.id.layout_check)
    public void onClickLayoutCheck() {
        if (!mCheckedCash) {
            mCheckedCash = true;
            layoutCheck.setBackgroundResource(R.drawable.shape_circle_black);
            imgCheck.setImageResource(R.drawable.ic_check_white);
            tvDone.setBackgroundResource(R.drawable.bg_black_corner);
            tvDone.setTextColor(getResources().getColor(R.color.white));
        } else {
            mCheckedCash = false;
            layoutCheck.setBackgroundResource(R.drawable.shape_circle_grey);
            imgCheck.setImageResource(R.drawable.ic_check_grey_dark);
            tvDone.setBackgroundResource(R.drawable.bg_grey_corner);
            tvDone.setTextColor(getResources().getColor(R.color.textColorSecondary));
        }
    }

    @OnClick(R.id.tv_done)
    public void onClickDone() {
        if (mCheckedCash) showDialogConfirmCashReceived();
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
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
