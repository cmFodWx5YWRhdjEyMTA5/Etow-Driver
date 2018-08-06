package com.app.etow.ui.trip_upcoming_detail;

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
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.constant.Constant;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.models.ViewMap;
import com.app.etow.ui.base.BaseMVPDialogActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TripUpcomingDetailActivity extends BaseMVPDialogActivity implements TripUpcomingDetailMVPView {

    @Inject
    TripUpcomingDetailPresenter presenter;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        tvTitleToolbar.setText(getString(R.string.upcoming_trips));
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

    @OnClick(R.id.layout_call_customer)
    public void onClickCallCustomer() {
        showDialogCallCustomer();
    }

    @OnClick(R.id.layout_view_map_pick_up)
    public void onClickViewMapPickUp() {
        ViewMap viewMap = new ViewMap(getString(R.string.upcoming_trips), true,
                Constant.TYPE_PICK_UP, "", "", "");
        GlobalFuntion.goToViewMapLocationActivity(this, viewMap);
    }

    @OnClick(R.id.layout_view_map_drop_off)
    public void onClickViewMapDropOff() {
        ViewMap viewMap = new ViewMap(getString(R.string.upcoming_trips), true,
                Constant.TYPE_DROP_OFF, "", "", "");
        GlobalFuntion.goToViewMapLocationActivity(this, viewMap);
    }

    public void showDialogCallCustomer() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_call_customer);
        Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        dialog.setCancelable(false);

        // Get view
        final TextView tvCancel = dialog.findViewById(R.id.tv_cancel);
        final TextView tvCall = dialog.findViewById(R.id.tv_call);

        // Get listener
        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}
