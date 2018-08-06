package com.app.etow.ui.incoming_request;

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
import android.widget.RadioGroup;
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

public class IncomingRequestActivity extends BaseMVPDialogActivity implements IncomingRequestMVPView {

    @Inject
    IncomingRequestPresenter presenter;

    @BindView(R.id.img_back)
    ImageView imgBack;

    @BindView(R.id.tv_title_toolbar)
    TextView tvTitleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        imgBack.setVisibility(View.GONE);
        tvTitleToolbar.setText(getString(R.string.incoming_request));
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
