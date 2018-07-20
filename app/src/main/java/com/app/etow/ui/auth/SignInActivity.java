package com.app.etow.ui.auth;

/*
 *  Copyright Ⓒ 2018. All rights reserved
 *  Author DangTin. Create on 2018/06/16
 */

import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.app.etow.R;
import com.app.etow.constant.GlobalFuntion;
import com.app.etow.ui.auth.term_and_condition.TermAndConditionActivity;
import com.app.etow.ui.base.BaseMVPDialogActivity;
import com.app.etow.ui.main.MainActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends BaseMVPDialogActivity implements SignInMVPView {

    @Inject
    SignInPresenter presenter;

    @BindView(R.id.checkbox_terms_conditions)
    CheckBox chbTermsConditions;

    @BindView(R.id.tv_sign_in)
    TextView tvSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getActivityComponent().inject(this);
        viewUnbind = ButterKnife.bind(this);
        presenter.initialView(this);

        setListener();
    }

    @Override
    protected boolean bindView() {
        return true;
    }

    @Override
    protected int addContextView() {
        return R.layout.activity_driver_sign_in;
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

    private void setListener() {
        chbTermsConditions.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked) {
                    tvSignIn.setBackgroundResource(R.drawable.bg_grey_corner);
                    tvSignIn.setTextColor(getResources().getColor(R.color.textColorSecondary));
                } else {
                    tvSignIn.setBackgroundResource(R.drawable.bg_black_corner);
                    tvSignIn.setTextColor(getResources().getColor(R.color.white));
                }
            }
        });
    }

    @OnClick(R.id.tv_sign_in)
    public void onClickSignIn() {
        if (chbTermsConditions.isChecked()) {
            GlobalFuntion.startActivity(this, MainActivity.class);
            finish();
        }
    }

    @OnClick(R.id.tv_term_and_condition)
    public void onClickTermAndCondition() {
        GlobalFuntion.startActivity(this, TermAndConditionActivity.class);
    }
}
