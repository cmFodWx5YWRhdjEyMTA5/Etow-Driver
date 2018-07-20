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
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.ui.base.BaseMVPDialogActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TripSummaryCashActivity extends BaseMVPDialogActivity implements TripSummaryCashMVPView {

    @Inject
    TripSummaryCashPresenter presenter;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @BindView(R.id.layout_check)
    RelativeLayout layoutCheck;

    @BindView(R.id.img_check)
    ImageView imgCheck;

    @BindView(R.id.tv_done)
    TextView tvDone;

    private boolean mCheckedCash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        tvTitleToolbar.setText(getString(R.string.trip_summary));
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
            layoutCheck.setBackgroundResource(R.drawable.shape_circle_white);
            imgCheck.setImageResource(R.drawable.ic_check_grey);
            tvDone.setBackgroundResource(R.drawable.bg_grey_corner);
            tvDone.setTextColor(getResources().getColor(R.color.textColorSecondary));
        }
    }

    @OnClick(R.id.tv_done)
    public void onClickDone() {
        if (mCheckedCash) {
            // Todo
        }
    }

}
